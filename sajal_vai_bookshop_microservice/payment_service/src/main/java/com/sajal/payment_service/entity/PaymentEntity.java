package com.sajal.payment_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private Long orderId;
    private Double finalAmount;
    private Boolean isSuccess;
}
