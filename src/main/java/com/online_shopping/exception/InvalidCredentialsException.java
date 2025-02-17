package com.online_shopping.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Incorrect credentials, please try again.");
    }
}
