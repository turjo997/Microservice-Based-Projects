package com.example.paymentapp.demopaymentapp.entity;

import com.example.paymentapp.demopaymentapp.model.CartItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentStatus;
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartEntity> cartItems;
    private Integer totalPrice;
    private Long userId;
}
