package com.HabibDev.Payment.controller;

import com.HabibDev.Payment.model.PaymentRequest;
import com.HabibDev.Payment.model.PaymentResponse;
import com.HabibDev.Payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/hello")
    public String hello(){
        return "I am from Payment";
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok(paymentResponse);
    }
}
