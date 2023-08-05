package com.bookshop.onlineBookShopApplication.service.impl;

import com.bookshop.onlineBookShopApplication.entity.BookEntity;
import com.bookshop.onlineBookShopApplication.exception.CustomException;
import com.bookshop.onlineBookShopApplication.model.BookRequestModel;
import com.bookshop.onlineBookShopApplication.model.BookResponseModel;
import com.bookshop.onlineBookShopApplication.repository.BookRepository;
import com.bookshop.onlineBookShopApplication.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<Object> addBooks(BookRequestModel bookRequestModel) {
        BookEntity bookEntity = BookEntity.builder()
                .bookName(bookRequestModel.getBookName())
                .authorName(bookRequestModel.getAuthorName())
                .price(bookRequestModel.getPrice())
                .quantity(bookRequestModel.getQuantity())
                .build();

        bookRepository.save(bookEntity);

        return new ResponseEntity<>(bookEntity, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getAllBooks() {
        //return (ResponseEntity<Object>) bookRepository.findAll();

        List<BookEntity> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new CustomException("Currently there are not books stored");
            //return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }


    @Override
    public ResponseEntity<BookEntity> getBookById(Integer bookId) {
        Optional<BookEntity> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            return ResponseEntity.ok(optionalBook.get());
        } else {
            throw new CustomException("Book is not present with that ID");
            //return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<BookEntity> getBookByAuthorName(String authorName) {
        BookEntity book = bookRepository.findByAuthorName(authorName);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            // return ResponseEntity.notFound().build();
            throw new CustomException("Found no book with that author");
        }
    }

    @Override
    public ResponseEntity<BookEntity> deleteBookById(Integer bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            ResponseEntity bookDeletedSuccessfully = new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
            return bookDeletedSuccessfully;
        } else {
            throw new CustomException("Book not found");
            //new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }



    @Override
    public ResponseEntity<Object> updateBooks(Integer bookId , BookRequestModel bookRequestModel) {
        Optional<BookEntity> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            BookEntity bookEntity = optionalBook.get();
            // Update the book entity with the new values from the request model
            bookEntity.setBookName(bookRequestModel.getBookName());
            bookEntity.setAuthorName(bookRequestModel.getAuthorName());
            bookEntity.setPrice(bookRequestModel.getPrice());
            bookEntity.setQuantity(bookRequestModel.getQuantity());

            // Save the updated book entity
            BookEntity updatedBook = bookRepository.save(bookEntity);

            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> getBookByAuthorAndBookName(String authorName, String bookName) {
        BookEntity bookEntity = bookRepository.findByAuthorNameAndBookName(authorName, bookName);
        if (bookEntity == null) {
            throw new CustomException("Doesn't match authorName and book Name");
        }
        return new ResponseEntity<>(bookEntity, HttpStatus.OK);
        // return new ResponseEntity<>(new RuntimeException("Doesn't match authorName and book Name"), HttpStatus.BAD_GATEWAY);
    }

}



