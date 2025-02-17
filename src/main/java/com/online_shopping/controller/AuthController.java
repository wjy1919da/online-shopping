package com.online_shopping.controller;


import com.online_shopping.dto.LoginRequest;
import com.online_shopping.dto.RegisterRequest;
import com.online_shopping.entity.User;
import com.online_shopping.security.JwtTokenProvider;
import com.online_shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public User registerUser(@Valid @RequestBody RegisterRequest user) {
        System.out.println("registerUser");

        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String username  = loginRequest.getUsername() ;
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUsername()));
    }
}

