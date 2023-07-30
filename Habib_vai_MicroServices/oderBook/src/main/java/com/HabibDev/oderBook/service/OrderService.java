package com.HabibDev.oderBook.service;


import com.HabibDev.oderBook.entity.OrderEntity;
import com.HabibDev.oderBook.model.OrderModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<Object> createOrder(OrderModel orderModel);
    ResponseEntity<List<OrderEntity>> getAllOrders();
    ResponseEntity<Object> getOrderById(Integer id);
    ResponseEntity<Object> updateOrder(Integer id, OrderModel orderModel);
    ResponseEntity<Object> deleteOrder(Integer id);
}
