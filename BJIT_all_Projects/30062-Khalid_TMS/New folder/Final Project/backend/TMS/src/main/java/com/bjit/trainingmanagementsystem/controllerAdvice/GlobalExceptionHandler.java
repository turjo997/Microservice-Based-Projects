package com.bjit.trainingmanagementsystem.controllerAdvice;

import com.bjit.trainingmanagementsystem.exception.*;
import com.bjit.trainingmanagementsystem.models.error.ErrorResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponseObject> handleUserEmailExistsException(UserEmailExistsException ex) {
        ErrorResponseObject errorResponse = ErrorResponseObject.builder()
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorResponseObject errorResponse = ErrorResponseObject.builder()
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ErrorResponseObject errorResponse = ErrorResponseObject.builder()
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<Object> fileUploadFailed(FileUploadFailedException ex) {
        ErrorResponseObject errorResponse = ErrorResponseObject.builder()
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleNotFoundException(UnauthorizedException ex) {
        ErrorResponseObject errorResponse = ErrorResponseObject.builder()
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}