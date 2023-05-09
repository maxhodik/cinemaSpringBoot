package com.ua.hodik.cinema.security;

import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(username);
        return new UserDetailsImpl(optionalUser.orElseThrow(()-> new UsernameNotFoundException("User not found")));

    }
}
