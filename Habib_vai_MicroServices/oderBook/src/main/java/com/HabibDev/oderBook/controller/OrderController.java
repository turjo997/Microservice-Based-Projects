package com.HabibDev.oderBook.controller;


import com.HabibDev.oderBook.config.FeignClientsConfig;
import com.HabibDev.oderBook.config.FeignClientsConfigPayment;
import com.HabibDev.oderBook.entity.OrderEntity;
import com.HabibDev.oderBook.model.*;
import com.HabibDev.oderBook.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")

public class OrderController {

    private final OrderService orderService;
    @Autowired
    FeignClientsConfig feignClientsConfig;
    @Autowired
    FeignClientsConfigPayment feignClientsConfigPayment;


    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderModel orderModel) {
        Integer bookId = orderModel.getBookId();
        ResponseEntity<BookModel> bookResponse;
        BookModel book;

        try {
            bookResponse = feignClientsConfig.getBookById(bookId);
            book = bookResponse.getBody();
        } catch (Exception e) {
            // Return a bad request response if the book is not found
            return ResponseEntity.badRequest().body("Book not found for this bookID");
        }

        // Check if the requested quantity is available
        Boolean isAvailableBook = feignClientsConfig.reduceQuantity(orderModel.getQuantity(), bookId);
        if (!isAvailableBook) {
            // Return a not found response if the requested quantity exceeds availability
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You ordered more books than available");
        }

        Integer price = book.getPrice() * orderModel.getQuantity();
        orderModel.setTotalPrice(price);

        //sending customerID and price to the payment service
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCustomerId(orderModel.getCustomerId());
        paymentRequest.setAmount(price);

        ResponseEntity<PaymentResponse> paymentResponse = feignClientsConfigPayment.processPayment(paymentRequest);
        PaymentResponse paymentResponseBody = paymentResponse.getBody();

        if (paymentResponseBody != null) {
            String paymentMessage = paymentResponseBody.getMessage();

            if (paymentMessage.equals("Payment successful")) {
                ShopRequest shopRequest = new ShopRequest();
                shopRequest.setBookId(bookId);
                shopRequest.setAmount(price);
                feignClientsConfig.addAmount(shopRequest);

                // Create the order using the order service
                return orderService.createOrder(orderModel);
            } else if (paymentMessage.equals("Customer not found")) {
                // Return a bad request response and roll back book quantity if the customer is not found
                feignClientsConfig.increaseQuantity(orderModel.getQuantity(), bookId);
                return ResponseEntity.badRequest().body("Customer not found");
            } else {
                // Return a bad request response and roll back book quantity if there is insufficient balance
                feignClientsConfig.increaseQuantity(orderModel.getQuantity(), bookId);
                return ResponseEntity.badRequest().body("Insufficient balance");
            }
        }

        // Return a bad request response if the payment response is null
        return ResponseEntity.badRequest().body("Failed to make an order!");
    }

   // @PostMapping("/create")
   /* public ResponseEntity<Object> createOrder(@RequestBody OrderModel orderModel) {
        Integer bookId = orderModel.getBookId();
        ResponseEntity<BookModel> bookResponse;
        BookModel book;

        try {
            bookResponse = feignClientsConfig.getBookById(bookId);
            book = bookResponse.getBody();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Book not found for this bookID");
        }

        Boolean isAvailableBook  = feignClientsConfig.reduceQuantity(orderModel.getQuantity(),bookId);
        if(!isAvailableBook){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You order more books than avaiability");
        }
        Integer price = book.getPrice() * orderModel.getQuantity();
        orderModel.setTotalPrice(price);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCustomerId(orderModel.getCustomerId());
        paymentRequest.setAmount(price);

        ResponseEntity<PaymentResponse> paymentResponse = feignClientsConfigPayment.processPayment(paymentRequest);
        PaymentResponse paymentResponseBody = paymentResponse.getBody();

        if (paymentResponseBody != null) {
            String paymentMessage = paymentResponseBody.getMessage();

            if (paymentMessage.equals("Payment successful")) {
                ShopRequest shopRequest = new ShopRequest();
                shopRequest.setBookId(bookId);
                shopRequest.setAmount(price);
                feignClientsConfig.addAmount(shopRequest);
                return orderService.createOrder(orderModel);
            } else if (paymentMessage.equals("Customer not found")) {
                return ResponseEntity.badRequest().body("Customer not found");
            } else {
                return ResponseEntity.badRequest().body("Insufficient balance");
            }
        }
        return ResponseEntity.badRequest().body("Failed to make a order!");
    }

*/

    @GetMapping("/all")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Integer id, @RequestBody OrderModel orderModel) {
        return orderService.updateOrder(id, orderModel);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }
}

