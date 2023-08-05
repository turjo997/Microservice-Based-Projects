package com.bookshop.onlineBookShopApplication.controller;

import com.bookshop.onlineBookShopApplication.entity.BookEntity;
import com.bookshop.onlineBookShopApplication.model.BookRequestModel;
import com.bookshop.onlineBookShopApplication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService ;
    @PostMapping("/add")
    public ResponseEntity<Object> createBooks(@RequestBody BookRequestModel bookRequestModel){
        return bookService.addBooks(bookRequestModel);
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAllBooks() {
        //List<BookEntity> books = (List<BookEntity>) bookService.getAllBooks();
        return bookService.getAllBooks();
    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<BookEntity> deleteBookById(@PathVariable Integer bookId) {
        // bookService.deleteBookById(bookId);
        return bookService.deleteBookById(bookId);
        //ResponseEntity.noContent().build();
    }


    @PutMapping("/update/{bookId}")
    public ResponseEntity<Object> updateBook(@PathVariable Integer bookId, @RequestBody BookRequestModel bookRequestModel) {
        return bookService.updateBooks(bookId, bookRequestModel);
    }


    @GetMapping("/author/{authorName}")
    public ResponseEntity<BookEntity> getBookByAuthorName(@PathVariable String authorName) {
        return bookService.getBookByAuthorName(authorName);
    }


    @GetMapping("/authorAndBook/{authorName}/{bookName}")
    public ResponseEntity<Object> bookDetails(@PathVariable String authorName,@PathVariable String bookName) {
        return bookService.getBookByAuthorAndBookName(authorName,bookName);
    }

}
