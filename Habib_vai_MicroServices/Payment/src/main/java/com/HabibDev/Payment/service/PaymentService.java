package com.HabibDev.Payment.service;
import com.HabibDev.Payment.model.PaymentRequest;
import com.HabibDev.Payment.model.PaymentResponse;
public interface PaymentService {
        PaymentResponse processPayment(PaymentRequest paymentRequest);
}


