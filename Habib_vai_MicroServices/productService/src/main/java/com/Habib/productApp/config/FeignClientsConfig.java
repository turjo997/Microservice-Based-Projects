package com.Habib.productApp.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="payment-app")
public interface FeignClientsConfig {
    @GetMapping("/getCount")
    Integer getCount();
}
