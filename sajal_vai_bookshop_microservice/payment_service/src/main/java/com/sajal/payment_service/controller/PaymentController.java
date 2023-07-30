package com.sajal.payment_service.controller;

import com.sajal.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    Boolean payment(@RequestParam Double billedAmount ,@RequestParam Double creditCard ){
        return billedAmount <= creditCard;
    }


}
