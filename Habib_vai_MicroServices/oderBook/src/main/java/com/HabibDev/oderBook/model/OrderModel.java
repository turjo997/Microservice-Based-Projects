package com.HabibDev.oderBook.model;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private Integer bookId;
    private Integer customerId;
    private String customerName;
    private String address;
    private LocalDateTime orderDate;
    private Integer quantity;
    private Integer totalPrice;

    // Add more properties as needed
}
