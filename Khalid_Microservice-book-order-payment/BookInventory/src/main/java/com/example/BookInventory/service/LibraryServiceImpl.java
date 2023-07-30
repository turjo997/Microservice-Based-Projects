package com.example.BookInventory.service;

import com.example.BookInventory.entity.BookEntity;
import com.example.BookInventory.model.BookRequestModel;
import com.example.BookInventory.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{

    private final BookRepository bookRepository;

    @Override
    public BookEntity registerBook(BookRequestModel bookRequestModel) {

        BookEntity bookEntity = BookEntity.builder()
                .bookName(bookRequestModel.getBookName())
                .authorName(bookRequestModel.getAuthorName())
                .price(bookRequestModel.getPrice())
                .inStock(bookRequestModel.getInStock())
                .build();
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return bookEntity;
    }

    @Override
    public ResponseEntity<Object> updateBookEntity(Long bookId, BookRequestModel bookRequestModel) {

        BookEntity bookFromDB = bookRepository.findById(bookId).get();
        bookFromDB.setBookName(bookRequestModel.getBookName());
        bookFromDB.setAuthorName(bookRequestModel.getAuthorName());
        BookEntity bookEntity = bookRepository.save(bookFromDB);

        return new ResponseEntity<>(bookEntity, HttpStatus.OK);
    }


}
