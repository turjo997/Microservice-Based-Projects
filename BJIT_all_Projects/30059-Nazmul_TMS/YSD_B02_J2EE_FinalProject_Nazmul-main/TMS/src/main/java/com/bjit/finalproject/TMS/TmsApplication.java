package com.bjit.finalproject.TMS;

import com.bjit.finalproject.TMS.service.RoleService;
import com.bjit.finalproject.TMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class TmsApplication {
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final UserService userService;

	@Bean
	CommandLineRunner run(RoleService roleService){
		return args -> {

			roleService.addRole("TRAINEE");
			roleService.addRole("TRAINER");
			roleService.addRole("ADMIN");

			userService.registerAdmin("admin@gmail.com");
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(TmsApplication.class, args);
	}

}
