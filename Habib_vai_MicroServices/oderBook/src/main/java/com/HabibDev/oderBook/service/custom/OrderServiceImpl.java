package com.HabibDev.oderBook.service.custom;


import com.HabibDev.oderBook.entity.OrderEntity;
import com.HabibDev.oderBook.model.OrderModel;
import com.HabibDev.oderBook.repository.OrderRepository;
import com.HabibDev.oderBook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<Object> createOrder(OrderModel orderModel) {
        // Convert OrderModel to OrderEntity and save it to the database

        //first will to book -app for fetching data according id
        //will come back with a response obj
        //will go to payment and check
        //feignClientsConfig.dopayment();
        //will come again with a response obj and save database.


        OrderEntity orderEntity = OrderEntity.builder()
                .bookId(orderModel.getBookId())
                .customerId(orderModel.getCustomerId())
                .customerName(orderModel.getCustomerName())
                .address(orderModel.getAddress())
                .orderDate(orderModel.getOrderDate())
                .quantity(orderModel.getQuantity())
                .totalPrice(orderModel.getTotalPrice())
                .build();
        System.out.println("coming");
        orderRepository.save(orderEntity);

        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getOrderById(Integer id) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            OrderEntity order = orderOptional.get();
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> updateOrder(Integer id, OrderModel orderModel) {
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            // Update the existing order with the new values from the OrderModel
            OrderEntity orderEntity = orderOptional.get();
            orderEntity.setBookId(orderModel.getBookId());
            orderEntity.setCustomerName(orderModel.getCustomerName());
            orderEntity.setAddress(orderModel.getAddress());
            orderEntity.setOrderDate(orderModel.getOrderDate());
            orderEntity.setQuantity(orderModel.getQuantity());
            orderEntity.setTotalPrice(orderModel.getTotalPrice());

            orderRepository.save(orderEntity);

            return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
