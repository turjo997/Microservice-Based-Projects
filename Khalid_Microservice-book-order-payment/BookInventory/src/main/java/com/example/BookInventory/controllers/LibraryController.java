package com.example.BookInventory.controllers;


import com.example.BookInventory.entity.BookEntity;
import com.example.BookInventory.exception.BookCreationFailedException;
import com.example.BookInventory.exception.BookNotFoundException;
import com.example.BookInventory.model.BookRequestModel;
import com.example.BookInventory.repository.BookRepository;
import com.example.BookInventory.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;
    private final BookRepository bookRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> registerBook(@RequestBody BookRequestModel bookRequestModel) {
        BookEntity bookEntity = libraryService.registerBook(bookRequestModel);

        if (bookEntity != null) {
            return new ResponseEntity<>(bookEntity, HttpStatus.OK);
        } else {
            throw new BookCreationFailedException("Book Creation Failed");
        }
    }

    @GetMapping("/all")
    public List<BookEntity> findAllData() {
        return bookRepository.findAll();
    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<Object> getByBookId(@PathVariable Long bookId) {

        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity != null) {
            return new ResponseEntity<>(bookEntity, HttpStatus.FOUND);
        } else {
            throw new BookNotFoundException("Book not found in Database");
        }

    }

    @GetMapping("/author/{authorName}")
    public List<BookEntity> getByAuthorName(@PathVariable String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Object> deleteByBookName(@PathVariable Long bookId) {
        BookEntity bookEntity = bookRepository.findByBookId(bookId);
        bookRepository.deleteById(bookId);

        if (bookEntity == null) {
            throw new BookNotFoundException("Delete Failed! No such Book Exits");
        } else {
            bookRepository.deleteById(bookId);
            return new ResponseEntity<>(bookEntity, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<Object> updateBookEntity(@PathVariable Long bookId, @RequestBody BookRequestModel bookRequestModel) {

        BookEntity bookEntity = bookRepository.findByBookId(bookId);

        if (bookEntity != null) {
            return libraryService.updateBookEntity(bookId, bookRequestModel);
        } else {
            throw new BookNotFoundException("Update Failed!");
        }
    }

    @GetMapping("search-both/{authorName}/{bookName}")
    public BookEntity getByAuthorAndBookName(@PathVariable String authorName, @PathVariable String bookName) {
        return bookRepository.findByAuthorNameAndBookName(authorName, bookName);
    }

    @GetMapping("stock-status/{bookId}/quantity/{quantity}")
    public Boolean isAvailable(@PathVariable Long bookId, @PathVariable Long quantity) {
        return false;
    }

    @GetMapping("/inventory-status")
    public Boolean inventoryStatus(@RequestParam("bookId") Long bookId, @RequestParam("quantity") Long quantity) {

        BookEntity bookEntity = bookRepository.findByBookId(bookId);
        Long inStock = bookEntity.getInStock();

        System.out.println("In stock -----------> "+bookEntity.getInStock());

        if (quantity > inStock) return false;
        else return true;
    }

    @PutMapping("/update-inventory")
    public void updateInventory(@RequestParam("bookId") Long bookId, @RequestParam("quantity") Long quantity) {

        BookEntity bookFromDB = bookRepository.findById(bookId).get();
        bookFromDB.setInStock(bookFromDB.getInStock()-quantity);
        BookEntity bookEntity = bookRepository.save(bookFromDB);

    }

    @GetMapping("/book-price")
    public Long getPrice(@RequestParam("bookId") Long bookId) {
        BookEntity bookEntity = bookRepository.findByBookId(bookId);
        return bookEntity.getPrice();
    }



}
