package com.example.PaymentService.feignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface FeignClientUserService {
    @GetMapping("user/user-balance")
    public Long getBalance(@RequestParam("userId") Long userId);

    @PutMapping("user/update-balance")
    public void updateBalance(@RequestParam("userId") Long userId, @RequestParam("currentBalance") Long currentBalance);
}
