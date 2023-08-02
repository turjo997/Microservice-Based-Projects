package com.onlinebookshop.apigateway.controller;

import com.onlinebookshop.apigateway.exception.LoginExpiredException;
import com.onlinebookshop.apigateway.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginExpiredException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailExistsException(LoginExpiredException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(408)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);
    }

}
