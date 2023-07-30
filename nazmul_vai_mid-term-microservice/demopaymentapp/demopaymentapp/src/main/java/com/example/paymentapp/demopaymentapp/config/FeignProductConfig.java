package com.example.paymentapp.demopaymentapp.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-app")
public interface FeignProductConfig {
    @GetMapping("/get-product-stock/{productName}")
    Integer getProductStock(@PathVariable String productName);
    @GetMapping("/get-product-price/{productName}")
    Integer getProductPrice(@PathVariable String productName);
    @PutMapping("/update-stock/{productName}/{stock}")
    void updateProductStock(@PathVariable String productName, @PathVariable Integer stock);

}
