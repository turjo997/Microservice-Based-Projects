package com.example.productapp.demoproductapp.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment-app")
public interface FeignClientConfig {
    @GetMapping("/getCount")
    Integer getCount();
}
