package com.example.PaymentService.feignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "book-inventory")
public interface FeignClientBookInventory {
    @GetMapping("books/book-price")
    public Long getPrice(@RequestParam("bookId") Long bookId);
}
