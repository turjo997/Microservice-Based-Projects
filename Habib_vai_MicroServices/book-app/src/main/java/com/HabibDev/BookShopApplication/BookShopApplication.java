package com.HabibDev.BookShopApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShopApplication.class, args);
	}

}
