package com.sajal.payment_service.service;

import com.sajal.payment_service.entity.PaymentEntity;
import com.sajal.payment_service.repository.PaymentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentEntityRepository paymentRepository;

}
