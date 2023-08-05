package com.example.paymentapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentAppApplication implements CommandLineRunner {
	public static Integer COUNTER = 0;

	public static void main(String[] args) {
		SpringApplication.run(PaymentAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
