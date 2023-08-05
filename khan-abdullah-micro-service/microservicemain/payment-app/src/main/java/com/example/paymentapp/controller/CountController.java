package com.example.paymentapp.controller;

import com.example.paymentapp.PaymentAppApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {
    @GetMapping("/getCount")
    public Integer getCount() {

        return PaymentAppApplication.COUNTER++;
    }

}
