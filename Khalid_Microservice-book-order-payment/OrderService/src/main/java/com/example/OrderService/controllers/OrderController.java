package com.example.OrderService.controllers;

import com.example.OrderService.entity.OrderEntity;;
import com.example.OrderService.feignConfig.FeignClientPayment;
import com.example.OrderService.model.OrderRequestModel;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final FeignClientPayment feignClientPayment;
    private final OrderRepository orderRepository;

    @PostMapping("/place-order")
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequestModel orderRequestModel) {

        OrderEntity orderEntity = orderService.registerOrder(orderRequestModel);

        Boolean availableInStock = orderService.isAvailable(orderRequestModel);
        Boolean paymentDone = feignClientPayment.paymentDone(orderRequestModel.getBookId(), orderRequestModel.getQuantity(), orderRequestModel.getUserId());

        if(availableInStock && paymentDone) {
            orderService.updateInventory(orderRequestModel);
            orderEntity.setOrderStatus("Complete");
        } else {
            if (!availableInStock) orderEntity.setOrderStatus("Stock out");
            else if (! paymentDone) orderEntity.setOrderStatus("Insufficient Balance");
        }

        orderRepository.save(orderEntity);

        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }


    @GetMapping("/stock-status")
    public String stockStatus() {
        //return "YAY! inventory status is : "+feignClientBookInventory.inventoryStatus(200);
        return "poop";
    }
}
