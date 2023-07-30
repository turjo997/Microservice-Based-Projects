package com.HabibDev.Payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentAppApplication.class, args);
	}

}
