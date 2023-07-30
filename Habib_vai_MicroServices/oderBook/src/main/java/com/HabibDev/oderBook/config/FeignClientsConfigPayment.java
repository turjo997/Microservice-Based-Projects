package com.HabibDev.oderBook.config;

import com.HabibDev.oderBook.model.PaymentRequest;
import com.HabibDev.oderBook.model.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="payment-app")
public interface FeignClientsConfigPayment {

    @PostMapping("/payments/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest);
}
