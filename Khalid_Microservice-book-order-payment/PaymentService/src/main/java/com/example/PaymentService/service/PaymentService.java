package com.example.PaymentService.service;

public interface PaymentService {
    Boolean validBalance(Long bookId, Long quantity, Long userId);
    Long getBill(Long bookId, Long quantity);
    Long getUserBalance(Long userId);
    void updateBalance(Long bookId, Long quantity, Long userId);
}
