package com.example.microservicedemo.demomicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//this is the eureka server(have to change the name of the module)
public class DemoMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMicroserviceApplication.class, args);
	}

}
