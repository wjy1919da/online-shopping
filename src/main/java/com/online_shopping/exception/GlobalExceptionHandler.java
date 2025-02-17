package com.online_shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// GlobalExceptionHandler.java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials() {
        return ResponseEntity.status(401).body("Incorrect credentials");
    }

    @ExceptionHandler(NotEnoughInventoryException.class)
    public ResponseEntity<?> handleLowStock() {
        return ResponseEntity.badRequest().body("Insufficient stock");
    }
}
