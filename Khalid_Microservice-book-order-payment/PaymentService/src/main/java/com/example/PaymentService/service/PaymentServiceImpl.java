package com.example.PaymentService.service;

import com.example.PaymentService.feignConfig.FeignClientBookInventory;
import com.example.PaymentService.feignConfig.FeignClientUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final FeignClientUserService feignClientUserService;
    private  final FeignClientBookInventory feignClientBookInventory;
    @Override
    public Boolean validBalance(Long bookId, Long quantity, Long userId) {
        Long userBalance = getUserBalance(userId);
        Long totalBill = getBill(bookId, quantity);

        if (userBalance >= totalBill) return true;
        else return false;
    }
    @Override
    public Long getUserBalance(Long userId) {
        return feignClientUserService.getBalance(userId);
    }

    @Override
    public void updateBalance(Long bookId, Long quantity, Long userId) {
        Long totalBill = getBill(bookId, quantity);
        Long userBalance = getUserBalance(userId);
        Long currentBalance = userBalance - totalBill;
        feignClientUserService.updateBalance(userId, currentBalance);
    }

    @Override
    public Long getBill(Long bookId, Long quantity) {
        Long price = feignClientBookInventory.getPrice(bookId);
        return price * quantity;
    }

}
