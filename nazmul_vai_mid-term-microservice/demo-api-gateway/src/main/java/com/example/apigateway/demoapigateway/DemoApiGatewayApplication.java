package com.example.apigateway.demoapigateway;

//import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class DemoApiGatewayApplication {

	public static void main(String[] args) {
			SpringApplication.run(DemoApiGatewayApplication.class, args);
		}
//	@Bean
//	public Sampler defaultSampler() {
//		return Sampler.ALWAYS_SAMPLE;
//	}
}
