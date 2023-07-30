package com.example.demobook.demobook.controller;

import com.example.demobook.demobook.model.BookModel;
import com.example.demobook.demobook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping("/create-book")
    public void createBook(@RequestBody BookModel bookModel){
        bookService.createABook(bookModel);
    }

    @GetMapping("/book/all")
    public List<BookModel> getAllBook(){
        return bookService.getAllBook();
    }
    @GetMapping("/test")
    public String getTest(){
        return "HI";
    }
}
