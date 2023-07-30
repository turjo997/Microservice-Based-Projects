package com.example.BookInventory.exception;

public class BookCreationFailedException extends RuntimeException {

    public BookCreationFailedException(String message) {
        super(message);
    }
}
