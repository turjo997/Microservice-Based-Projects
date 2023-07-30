package com.example.productapp.demoproductapp;

import com.example.productapp.demoproductapp.config.MigrationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoProductappApplication implements CommandLineRunner {

	@Autowired
	private MigrationConfiguration migrationConfiguration;
	public static void main(String[] args) {
		SpringApplication.run(DemoProductappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("migration configuration:"+ migrationConfiguration.getMigration());
	}
}
