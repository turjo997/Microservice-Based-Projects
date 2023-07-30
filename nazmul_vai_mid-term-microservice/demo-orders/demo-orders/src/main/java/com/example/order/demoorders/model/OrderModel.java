package com.example.order.demoorders.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class OrderModel {
    private String address;
    private String orderStatus;
    private List<CartItems> cartItems;
    private Long userId;
}
