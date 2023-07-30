package com.example.BookInventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BookInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInventoryApplication.class, args);
	}

}
