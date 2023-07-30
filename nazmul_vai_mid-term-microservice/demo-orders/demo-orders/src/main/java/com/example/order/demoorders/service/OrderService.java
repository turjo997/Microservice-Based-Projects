package com.example.order.demoorders.service;

import com.example.order.demoorders.model.OrderModel;
import com.example.order.demoorders.model.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderModel orderModel);
    void updateOrderById(Long orderId, String orderStatus);
}
