package com.sajal.order_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment-service")
public interface PaymentClientConfig {
    @PostMapping("/payment")
    Boolean payment(@RequestParam("billedAmount") Double billedAmount , @RequestParam("creditCard") Double creditCard );
}
