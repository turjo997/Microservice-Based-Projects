package com.sajal.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Long userId;
    private Long bookId;
    private Double totalAmount;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
