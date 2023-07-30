package com.example.BookInventory.service;


import com.example.BookInventory.entity.BookEntity;
import com.example.BookInventory.model.BookRequestModel;
import org.springframework.http.ResponseEntity;

public interface LibraryService {
    BookEntity registerBook(BookRequestModel bookRequestModel);

    ResponseEntity<Object> updateBookEntity(Long bookId, BookRequestModel bookRequestModel);
}
