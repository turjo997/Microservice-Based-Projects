package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.model.OrderRequestModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    ResponseEntity<Object> createOrder(OrderRequestModel orderRequest);
    void deleteOrder(Long id);

    Order updateOrder(Long id, OrderRequestModel updatedOrder);
}