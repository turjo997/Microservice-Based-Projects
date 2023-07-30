package com.HabibDev.oderBook.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    private Integer orderId;
    private Integer customerId;
    private Integer bookId;  // Foreign key referencing the BookEntity
    private String customerName;
    private String address;
    private LocalDateTime orderDate;
    private Integer quantity;
    private Integer totalPrice;

    // Add more properties as needed
}
