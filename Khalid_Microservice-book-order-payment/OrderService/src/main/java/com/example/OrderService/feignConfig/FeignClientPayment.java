package com.example.OrderService.feignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service")
public interface FeignClientPayment {
    @PostMapping("payment/pay-bill")
    public Boolean paymentDone(@RequestParam("bookId") Long bookId, @RequestParam("quantity") Long quantity, @RequestParam("UserId") Long userId);
}
