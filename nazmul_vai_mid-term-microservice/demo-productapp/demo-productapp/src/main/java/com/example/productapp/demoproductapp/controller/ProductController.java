package com.example.productapp.demoproductapp.controller;

import com.example.productapp.demoproductapp.model.OrderModel;
import com.example.productapp.demoproductapp.model.ProductModel;
import com.example.productapp.demoproductapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @GetMapping("/get-products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/add-products")
    public String addProduct(@RequestBody ProductModel productModel){
        productService.addProduct(productModel);
        return "Product added";
    }

    @PostMapping("/create-order")
    public OrderModel createOrder(@RequestBody OrderModel orderModel){
        logger.info("HI");
        return productService.createOrder(orderModel);
    }

    @GetMapping("/get-product-stock/{productName}")
    public Integer getProductStock(@PathVariable String productName){
        return productService.getProductStock(productName);
    }

    @GetMapping("/get-product-price/{productName}")
    public Integer getProductPrice(@PathVariable String productName){
        return productService.getProductPrice(productName);
    }

    @PutMapping("/update-stock/{productName}/{stock}")
    public void updateProductStock(@PathVariable String productName, @PathVariable Integer stock){
        productService.updateStock(productName, stock);
    }
}
