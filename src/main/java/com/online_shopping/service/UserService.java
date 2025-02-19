package com.online_shopping.service;


import com.online_shopping.dto.RegisterRequest;
import com.online_shopping.entity.User;
import com.online_shopping.exception.UserAlreadyExistsException;
import com.online_shopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public User findByUsername(String username){
        return userRepository.findByUsername(username) ;
    }

    // Other business logic methods can be added

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            //System.out.println("authentication.getPrincipal().toString() : "+authentication.getPrincipal().toString() );

            if (principal instanceof UserDetails) {

                System.out.println("principal instanceof UserDetails");
                UserDetails userDetails = (UserDetails) principal;
                System.out.println("userDetails username "+userDetails.getUsername());
                User user = userRepository.findByUsername(userDetails.getUsername());
                return user;  // Return the authenticated user
            } else {
                // Handle cases where the principal is not an instance of User
                throw new RuntimeException("User not found in the SecurityContext");
            }
        } else {
            throw new RuntimeException("No authentication found in SecurityContext");
        }
    }

}

