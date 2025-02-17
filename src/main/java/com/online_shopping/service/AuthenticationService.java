package com.online_shopping.service;

import com.online_shopping.entity.User;
import com.online_shopping.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    public String login(String username, String password) {
//        User user = userService.findUserByUsername(username);
//        if (user != null && user.getPassword().equals(password)) { // In practice, hash passwords
//            return jwtTokenProvider.createToken(username);
//        } else {
//            throw new AuthenticationException("Invalid credentials");
//        }
//    }
}

