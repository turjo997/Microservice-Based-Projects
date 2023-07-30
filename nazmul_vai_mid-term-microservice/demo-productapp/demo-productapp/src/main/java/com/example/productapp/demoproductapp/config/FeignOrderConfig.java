package com.example.productapp.demoproductapp.config;

import com.example.productapp.demoproductapp.model.OrderModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-app")
public interface FeignOrderConfig {
    @PostMapping("/order-creation")
    OrderModel createOrder(OrderModel orderModel);
}
