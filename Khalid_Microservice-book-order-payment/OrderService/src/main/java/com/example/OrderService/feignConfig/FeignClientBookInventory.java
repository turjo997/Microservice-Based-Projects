package com.example.OrderService.feignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "book-inventory")
public interface FeignClientBookInventory {
    @GetMapping("books/inventory-status")
    public Boolean inventoryStatus(@RequestParam("bookId") Long bookId, @RequestParam("quantity") Long quantity);

    @PutMapping("books/update-inventory")
    public void updateInventory(@RequestParam("bookId") Long bookId, @RequestParam("quantity") Long quantity);
}
