package com.sajal.order_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "inventory-service")
public interface InventoryClientConfig {
    @GetMapping("/book/count/{id}")
    Integer getBookCount(@PathVariable("id") Long id);

    @GetMapping("/book/price/{id}")
    Integer getBookPrice(@PathVariable("id") Long id);

    @PutMapping("/book/update")
    Boolean updateCountByBookId(@RequestParam("bookId") Long bookId, @RequestParam("count") Integer count );
}
