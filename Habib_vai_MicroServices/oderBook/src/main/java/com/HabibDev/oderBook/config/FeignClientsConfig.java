package com.HabibDev.oderBook.config;

import com.HabibDev.oderBook.model.BookModel;
import com.HabibDev.oderBook.model.ShopRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-app")
public interface FeignClientsConfig {

    @GetMapping("/books/id/{bookId}")
    ResponseEntity<BookModel> getBookById(@PathVariable("bookId") Integer bookId);

    @PostMapping("/shops/add-amount")
    public void addAmount(@RequestBody ShopRequest shopRequest);

    @GetMapping("books/reduceQuantity/{bookQuantity}/{bookId}")
    public Boolean reduceQuantity(@PathVariable("bookQuantity") Integer bookQuantity,@PathVariable("bookId") Integer bookId) ;

    @GetMapping("books/increaseQuantity/{bookQuantity}/{bookId}")
    public void increaseQuantity(@PathVariable("bookQuantity") Integer bookQuantity,@PathVariable("bookId") Integer bookId) ;

}
