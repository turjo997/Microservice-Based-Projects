package com.example.productapp.demoproductapp.service;

import com.example.productapp.demoproductapp.model.OrderModel;
import com.example.productapp.demoproductapp.model.ProductModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductModel> getAllProducts();
    String addProduct(ProductModel productModel);
    OrderModel createOrder(OrderModel orderModel);
    Integer getProductStock(String productName);
    Integer getProductPrice(String productName);
    void updateStock(String productName, Integer stock);
}
