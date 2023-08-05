package com.bookshop.onlineBookShopApplication.service;

import com.bookshop.onlineBookShopApplication.entity.BookEntity;
import com.bookshop.onlineBookShopApplication.model.BookRequestModel;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<Object> addBooks(BookRequestModel bookRequestModel);

    ResponseEntity<Object> getAllBooks();

    ResponseEntity<BookEntity> getBookById(Integer bookId);
    ResponseEntity<BookEntity> getBookByAuthorName(String authorName);


    ResponseEntity<BookEntity> deleteBookById(Integer bookId);

    ResponseEntity<Object> updateBooks(Integer id , BookRequestModel bookRequestModel);

    ResponseEntity<Object> getBookByAuthorAndBookName(String authorName, String bookName);
}
