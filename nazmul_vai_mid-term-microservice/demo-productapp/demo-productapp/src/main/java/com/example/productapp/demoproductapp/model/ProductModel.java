package com.example.productapp.demoproductapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {
    private String productName;
    private Integer productPrice;
    private Integer productQuantity;
    private String productDetails;
}
