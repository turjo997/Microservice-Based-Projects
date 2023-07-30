package com.example.demobook.demobook.service;

import com.example.demobook.demobook.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    void createABook(BookModel bookModel);
    List<BookModel> getAllBook();
}
