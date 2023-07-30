package com.example.order.demoorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoOrdersApplication.class, args);
	}

}
