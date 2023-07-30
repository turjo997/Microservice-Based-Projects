package com.sajal.user_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long userId;
    Long bookId;
    Integer quantity;
}
