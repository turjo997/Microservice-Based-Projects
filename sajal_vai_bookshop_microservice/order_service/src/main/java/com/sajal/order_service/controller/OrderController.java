package com.sajal.order_service.controller;

import com.sajal.order_service.config.UserClientConfig;
import com.sajal.order_service.dto.ConfirmOrderDto;
import com.sajal.order_service.entity.OrderEntity;
import com.sajal.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/place-order")
    ResponseEntity<Object> placeOrder(@RequestBody OrderEntity order){
        return ResponseEntity.ok(orderService.placeOrder(order));
    }
    @PostMapping("/confirm-order")
    ResponseEntity<Object> confirmOrder(@RequestBody ConfirmOrderDto confirmOrder){
        return ResponseEntity.ok(orderService.confirmOrder(confirmOrder));
    }
}
