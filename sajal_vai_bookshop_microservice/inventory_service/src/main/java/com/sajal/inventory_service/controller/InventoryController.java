package com.sajal.inventory_service.controller;

import com.sajal.inventory_service.dto.BookInfo;
import com.sajal.inventory_service.entity.BookEntity;
import com.sajal.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Create book
    @PostMapping("/book")
    ResponseEntity<BookEntity> createBook(@RequestBody BookEntity book){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createBook(book));
    }

    // get All book
    @GetMapping("/books")
    ResponseEntity<List<BookEntity>> getBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getBooks());
    }
    // get book
    @GetMapping("/book/{id}")
    ResponseEntity<BookEntity> getBook(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getBookById(id));
    }

    @GetMapping("/book/count/{id}")
    Integer getBookCount(@PathVariable Long id){
        return inventoryService.getBookCountById(id);
    }

    @GetMapping("/book/price/{id}")
    Integer getBookPrice(@PathVariable Long id){
        return inventoryService.getBookById(id).getPrice();
    }


    //update book
    @PutMapping("/book")
    ResponseEntity<BookEntity> updateBook(@RequestBody BookEntity book){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.updateBook(book));
    }

    @PutMapping("/book/update")
    Boolean updateCountByBookId(@RequestParam Long bookId,@RequestParam Integer count ){
        return inventoryService.updateBookCountById(bookId,count);
    }

    // Delete book
    @DeleteMapping("/book/{id}")
    ResponseEntity<Void> deleteBook(@PathVariable Long id){
        inventoryService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/books/info")
    List<BookInfo> getBookInfo(@RequestBody List<Long> ids){
        return inventoryService.getBooksInfo(ids);
    }

}
