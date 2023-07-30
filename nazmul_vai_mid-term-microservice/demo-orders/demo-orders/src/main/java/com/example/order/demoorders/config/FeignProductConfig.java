package com.example.order.demoorders.config;

import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="product-app")
public interface FeignProductConfig {
    @GetMapping("/get-product-stock/{productName}")
    Integer getProductStock(@PathVariable String productName);


}
