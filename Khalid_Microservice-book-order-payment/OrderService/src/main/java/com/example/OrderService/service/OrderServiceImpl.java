package com.example.OrderService.service;

import com.example.OrderService.entity.OrderEntity;
import com.example.OrderService.feignConfig.FeignClientBookInventory;
import com.example.OrderService.model.OrderRequestModel;
import com.example.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final FeignClientBookInventory feignClientBookInventory;

    @Override
    public OrderEntity registerOrder(OrderRequestModel orderRequestModel) {

        OrderEntity orderEntity = OrderEntity.builder()
                .userId(orderRequestModel.getUserId())
                .bookId(orderRequestModel.getBookId())
                .quantity(orderRequestModel.getQuantity())
                .build();

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        return savedOrderEntity;
    }

    @Override
    public Boolean isAvailable(OrderRequestModel orderRequestModel) {

        Long bookId = orderRequestModel.getBookId();
        Long quantity = orderRequestModel.getQuantity();
        Boolean availableInStock = feignClientBookInventory.inventoryStatus(bookId, quantity);

        return availableInStock;
    }

    @Override
    public void updateInventory(OrderRequestModel orderRequestModel) {
        feignClientBookInventory.updateInventory(orderRequestModel.getBookId(), orderRequestModel.getQuantity());
    }
}
