package com.Habib.productApp;

import com.Habib.productApp.config.MigrationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductAppApplication implements CommandLineRunner {

	@Autowired
	private MigrationConfiguration migrationConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(ProductAppApplication.class, args);
	}

	public void run (String... args) throws Exception{
		System.out.println("migration Configuration "+ migrationConfiguration.getMigration());
	}

}
