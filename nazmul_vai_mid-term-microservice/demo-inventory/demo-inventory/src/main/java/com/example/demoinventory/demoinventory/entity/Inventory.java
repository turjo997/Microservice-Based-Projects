package com.example.demoinventory.demoinventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "inventories")
public class Inventory {
    @Id
    private Integer bookId;
    private Integer price;
    private Integer quantity;
}
