package com.onlinebookshop.apigateway.exception;

public class LoginExpiredException extends RuntimeException {
    public LoginExpiredException(String message) {
        super(message);
    }
}