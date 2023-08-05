package com.example.orderservice.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.model.OrderItemModel;
import com.example.orderservice.model.OrderRequestModel;
import com.example.orderservice.model.OrderResponseModel;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public ResponseEntity<Object> createOrder(OrderRequestModel orderRequest) {
        List<OrderItem> orderItems = new ArrayList<>();

        // Iterate over the items in the OrderRequestModel and convert them to OrderItem entities
        for (OrderItemModel itemModel : orderRequest.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .productName(itemModel.getProductName())
                    .quantity(itemModel.getQuantity())
                    .build();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .customerName(orderRequest.getCustomerName())
                .items(orderItems)
                .build();
        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrder(Long id, OrderRequestModel updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            // Update the existing order with the new values
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            // Update other attributes as needed

            return orderRepository.save(existingOrder);
        } else {
            // Handle case when the order is not found
            throw new IllegalArgumentException("Order not found");
        }
    }

}
