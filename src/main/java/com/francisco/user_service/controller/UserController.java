package com.francisco.user_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.francisco.user_service.dto.LoginRequest;
import com.francisco.user_service.dto.UserRequest;
import com.francisco.user_service.dto.UserResponse;
import com.francisco.user_service.entity.User;
import com.francisco.user_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        User savedUser = userService.createUser(request);
        return new UserResponse(savedUser.getId(), savedUser.getEmail());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        User verifiedUser = userService.verifyUser(request);
        return new UserResponse(verifiedUser.getId(), verifiedUser.getEmail());
    }
    
    
    
   
}
