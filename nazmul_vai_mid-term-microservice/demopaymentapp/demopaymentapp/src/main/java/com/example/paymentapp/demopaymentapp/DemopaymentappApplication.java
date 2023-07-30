package com.example.paymentapp.demopaymentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemopaymentappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemopaymentappApplication.class, args);
	}

}
