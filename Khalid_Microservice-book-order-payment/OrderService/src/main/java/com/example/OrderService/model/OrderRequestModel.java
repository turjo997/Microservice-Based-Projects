package com.example.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestModel {

    private Long userId;
    private Long bookId;
    private Long quantity;
    private String orderStatus;

}
