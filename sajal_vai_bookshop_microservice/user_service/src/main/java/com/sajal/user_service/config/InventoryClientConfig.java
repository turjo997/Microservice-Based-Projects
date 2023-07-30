package com.sajal.user_service.config;

import com.sajal.user_service.dto.BookInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("inventory-service")
public interface InventoryClientConfig {
    @PostMapping("/books/info")
    List<BookInfo> getBookInfo(@RequestBody List<Long> ids);
}
