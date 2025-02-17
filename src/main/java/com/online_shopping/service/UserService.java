package com.online_shopping.service;


import com.online_shopping.dto.RegisterRequest;
import com.online_shopping.entity.User;
import com.online_shopping.exception.UserAlreadyExistsException;
import com.online_shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a user
    public User registerUser(RegisterRequest registerRequest) {
        // Ensure the username or email is not already taken
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new UserAlreadyExistsException("Username already taken");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already taken");
        }

        // Map RegisterRequest to User entity
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Hash the password
        user.setRole(0); // Default role (can be modified if needed)

        // Save the user in the database
        return userRepository.save(user);
    }

    // Login user by username
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        System.out.println("loginUser : "+user.toString());
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    // Other business logic methods can be added
}

