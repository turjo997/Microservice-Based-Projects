package com.HabibDev.Payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class PaymentEntity {
    @Id
    @GeneratedValue
    private Integer paymentId;
    private Integer customerId;
    private Integer amount;
    //private LocalDateTime paymentDate;

    // Add more properties as needed
}
