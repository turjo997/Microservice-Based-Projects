package com.example.paymentapp.demopaymentapp.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-app")
public interface FeignOrderConfig {
    @PutMapping("/get-order-by-id/{orderId}/{orderStatus}")
    void updateOrderById(@PathVariable Long orderId, @PathVariable String orderStatus);
}
