package com.sajal.order_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-service")
public interface UserClientConfig {
    @GetMapping("/user/exist/{id}")
    Boolean userExist(@PathVariable("id") Long id);

    @PostMapping("/user/add-book")
    Boolean addBookToUser(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId, @RequestParam("quantity") Integer quantity);
}
