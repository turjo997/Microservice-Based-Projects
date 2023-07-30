package com.example.productapp.demoproductapp.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderModel {
    private String orderStatus;
    private String address;
    private List<CartItems> cartItems;
    private Long userId;
}
