package com.example.paymentapp.demopaymentapp.service.impl;

import com.example.paymentapp.demopaymentapp.config.FeignOrderConfig;
import com.example.paymentapp.demopaymentapp.config.FeignProductConfig;
import com.example.paymentapp.demopaymentapp.config.FeignUserConfig;
import com.example.paymentapp.demopaymentapp.entity.CartEntity;
import com.example.paymentapp.demopaymentapp.entity.Invoice;
import com.example.paymentapp.demopaymentapp.model.*;
import com.example.paymentapp.demopaymentapp.repository.InvoiceRepository;
import com.example.paymentapp.demopaymentapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final FeignProductConfig feignProductConfig;
    private final FeignOrderConfig feignOrderConfig;
    private final InvoiceRepository invoiceRepository;
    private final FeignUserConfig userConfig;
    //private InvoiceModel invoiceModel;
    @Override
    public void validateOrder(OrderResponse orderResponse) {
        List<CartItems> cartItems = orderResponse.getCartItems();
        List<CartItems> responseCartItems = new ArrayList<>();
        List<CartEntity> finalCartEntity = new ArrayList<>();
        Long orderId = orderResponse.getId();
        String orderStatus = "COMPLETED";

        Integer totalPrice = 0;
        for(CartItems cartItem:cartItems){
            Integer pQuantity = feignProductConfig.getProductStock(cartItem.getProductName());
            Integer productPrice = feignProductConfig.getProductPrice(cartItem.getProductName());
            System.out.println(productPrice);
            Integer productPricePerQuantity = pQuantity*productPrice;
            Integer oQuantity = cartItem.getProductQuantity();
            if(oQuantity<pQuantity){
                responseCartItems.add(cartItem);
                CartEntity cartEntity = CartEntity.builder()
                        .productName(cartItem.getProductName())
                        .productQuantity(cartItem.getProductQuantity()).build();
                finalCartEntity.add(cartEntity);
                totalPrice += productPricePerQuantity;
                feignProductConfig.updateProductStock(cartItem.getProductName(), oQuantity);
            }
            else{
                cartItem.setProductQuantity(oQuantity);
                responseCartItems.add(cartItem);
            }
        }

        //String finalPrice = String.valueOf(totalPrice);
        if(totalPrice>0){
            Invoice invoice = Invoice.builder()
                    .address(orderResponse.getAddress())
                    .paymentStatus("PENDING")
                    .cartItems(finalCartEntity)
                    .totalPrice(totalPrice)
                    .userId(orderResponse.getUserId()).build();
            invoiceRepository.save(invoice);
        }
        feignOrderConfig.updateOrderById(orderId, orderStatus);


//        InvoiceModel invoiceModel = InvoiceModel.builder()
//                .orderStatus("Successful")
//                .address(orderResponse.getAddress())
//                .cartItems(responseCartItems)
//                .totalPrice(finalPrice).build();
//
//        return invoiceModel;
    }


    @Override
    public List<InvoiceModel> getInvoice(Long userId){
        List<Invoice> invoice = invoiceRepository.findByUserId(userId);
        List<InvoiceModel> invoiceModels = new ArrayList<>();
        for(Invoice inv:invoice){
            if(!inv.getCartItems().isEmpty()){
                //Invoice requiredInvoice = invoice.get();
                List<CartEntity> cartItem = inv.getCartItems();
                List<CartItems> finalCartItems = new ArrayList<>();
                for(CartEntity item:cartItem){
                    CartItems cartProduct = CartItems.builder()
                            .productName(item.getProductName())
                            .productQuantity(item.getProductQuantity()).build();
                    finalCartItems.add(cartProduct);
                }
                InvoiceModel invoiceModel = InvoiceModel.builder()
                        .paymentStatus(inv.getPaymentStatus())
                        .address(inv.getAddress())
                        .totalPrice(inv.getTotalPrice())
                        .cartItems(finalCartItems)
                        .userId(inv.getUserId()).build();
                invoiceModels.add(invoiceModel);
            }
        }
        return invoiceModels;

//        List<CartItems> emptyCart = new ArrayList<>();
//        CartItems cartItems = CartItems.builder()
//                .productQuantity(0)
//                .productName("No name").build();
//        InvoiceModel invoiceModel = InvoiceModel.builder()
//                .paymentStatus("CANCELLED")
//                .totalPrice(0)
//                .cartItems(emptyCart)
//                .build();
//        return invoiceModel;
    }

    @Override
    public String finalResult(PaymentModel paymentModel) {
        Long user_id = paymentModel.getUser_id();
        UserResponse response = userConfig.getUserById(user_id);
        Optional<Invoice> invoice = invoiceRepository.findById(paymentModel.getInvoice_id());

        if(invoice.isPresent()){
            Invoice newInvoice = invoice.get();
            Integer balance = response.getAccountBalance();
            Integer requiredAmount = paymentModel.getTotalPrice();
            if(balance<requiredAmount){
                return "Sorry cannot process the payment";
            }
            userConfig.updateUserBalance(requiredAmount, user_id);
            newInvoice.setPaymentStatus("COMPLETED");
            invoiceRepository.save(newInvoice);
        }

        return "PAYMENT PROCESSED";

    }
}
