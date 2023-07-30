package com.example.OrderService.service;

import com.example.OrderService.entity.OrderEntity;
import com.example.OrderService.model.OrderRequestModel;

public interface OrderService {

    OrderEntity registerOrder(OrderRequestModel orderRequestModel);
    Boolean isAvailable(OrderRequestModel orderRequestModel);

    void updateInventory(OrderRequestModel orderRequestModel);
}
