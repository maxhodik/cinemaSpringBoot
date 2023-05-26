package com.ua.hodik.cinema.util.validation;


import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return this.getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        user=userService.findByName(user.getName()).orElse(null);
        if (user != null) {
            errors.rejectValue("name", "", "User with this name already exists");
        }
    }
}
