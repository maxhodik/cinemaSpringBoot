package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.model.Role;
import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void registration (User user){
        String password = user.getPassword();
        if(password.equals("Admin")){
            user.setRole(Role.ADMIN);
        }else user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public Optional<User> findByName(String name) {
      return userRepository.findByName(name);
    }
}
