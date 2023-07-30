package com.example.productapp.demoproductapp.service.impl;

import com.example.productapp.demoproductapp.config.FeignOrderConfig;
import com.example.productapp.demoproductapp.entity.Product;
import com.example.productapp.demoproductapp.model.OrderModel;
import com.example.productapp.demoproductapp.model.ProductModel;
import com.example.productapp.demoproductapp.repository.ProductRepository;
import com.example.productapp.demoproductapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FeignOrderConfig feignOrderConfig;
    @Override
    public List<ProductModel> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductModel> requiredProducts = new ArrayList<>();
        for(Product product:products){
            ProductModel requiredProduct = ProductModel.builder()
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQuantity(product.getProductQuantity())
                    .productDetails(product.getProductDetails()).build();
            requiredProducts.add(requiredProduct);
        }
        return requiredProducts;
    }

    @Override
    public String addProduct(ProductModel productModel) {
        Product product = Product.builder()
                        .productName(productModel.getProductName())
                        .productPrice(productModel.getProductPrice())
                        .productQuantity(productModel.getProductQuantity())
                        .productDetails(productModel.getProductDetails())
                        .build();
        productRepository.save(product);
        return "";
    }

    @Override
    public OrderModel createOrder(OrderModel orderModel){
        return feignOrderConfig.createOrder(orderModel);
        //return "Your total payable amount is "+finalAmount;
    }

    @Override
    public Integer getProductStock(String productName) {
        Product product = productRepository.findByProductName(productName);
        Integer quantity = product.getProductQuantity();
        return quantity;
    }
    @Override
    public Integer getProductPrice(String productName) {
        Product product = productRepository.findByProductName(productName);
        Integer price = product.getProductPrice();
        return price;
    }
//    List<CartItems> cartItems = orderModel.getCartItems();
//    Integer total = 0;
//        for(CartItems cartItem:cartItems){
//        Product product = productRepository.findByProductName(cartItem.getProductName());
//        String amount = product.getProductPrice();
//        Integer intAmount = Integer.parseInt(amount);
//        String quantity = cartItem.getProductQuantity();
//        Integer quanInt = Integer.parseInt(quantity);
//        Integer productTotalAmount = intAmount * quanInt;
//        total += productTotalAmount;
//
//        String finalAmount = String.valueOf(total);
//    }

    @Override
    public void updateStock(String productName, Integer stock){
        Product product = productRepository.findByProductName(productName);
        Integer repoStock = product.getProductQuantity();

        Integer newStock = repoStock - stock;
        product.setProductQuantity(newStock);
        productRepository.save(product);
    }
}
