package com.example.paymentapp.demopaymentapp.config;

import com.example.paymentapp.demopaymentapp.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-app")
public interface FeignUserConfig {
    @GetMapping("/get-user-by-id/{id}")
    UserResponse getUserById(@PathVariable Long id);

    @PutMapping("/update-user-balance/{id}/{balance}")
    void updateUserBalance(@PathVariable Integer balance, @PathVariable Long id);
}
