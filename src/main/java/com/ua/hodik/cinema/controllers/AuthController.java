package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.UserDto;
import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.services.UserService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final Validator userValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserService userService, @Qualifier(value="userValidator") Validator userValidator, ModelMapper modelMapper) {
        this.userService = userService;

        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("userDto") UserDto userDto) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult) {
        User user = convertToUser(userDto);

        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }
        userService.registration(user);
        return "redirect:/auth/login";
    }
    private User convertToUser(UserDto userDto){
       return modelMapper.map(userDto, User.class);
    }
}
