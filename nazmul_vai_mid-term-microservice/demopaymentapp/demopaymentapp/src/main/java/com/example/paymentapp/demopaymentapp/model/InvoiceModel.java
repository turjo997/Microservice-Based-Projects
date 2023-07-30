package com.example.paymentapp.demopaymentapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceModel {
    private String paymentStatus;
    private String address;
    private List<CartItems> cartItems;
    private Integer totalPrice;
    private Long userId;
}
