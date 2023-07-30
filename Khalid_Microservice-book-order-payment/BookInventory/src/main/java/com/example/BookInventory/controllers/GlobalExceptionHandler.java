package com.example.BookInventory.controllers;

import com.example.BookInventory.exception.BookCreationFailedException;
import com.example.BookInventory.exception.BookNotFoundException;
import com.example.BookInventory.exception.DeleteFailedException;
import com.example.BookInventory.exception.UpdateFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BookNotFoundException.class, BookCreationFailedException.class, DeleteFailedException.class, UpdateFailedException.class})
    public ResponseEntity<Object> responseNotFoundException (Exception exception) {
        if (exception instanceof BookNotFoundException) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        else if (exception instanceof BookCreationFailedException) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        }
        else if (exception instanceof DeleteFailedException) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_MODIFIED);
        }

    }
}
