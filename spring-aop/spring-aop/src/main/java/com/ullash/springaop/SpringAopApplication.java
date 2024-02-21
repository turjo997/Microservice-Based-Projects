package com.ullash.springaop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringAopApplication {

	public static void main(String[] args) throws UserNotFoundException {

		ApplicationContext context = SpringApplication.run(SpringAopApplication.class, args);

		AopClass aopClass = context.getBean(AopClass.class);
		aopClass.hello();
		aopClass.sum(10,20);
		aopClass.printSomething("I am a good boy");

		AnotherClass anotherClass = context.getBean(AnotherClass.class);
		anotherClass.anotherMethod(12);


		CustomUserService customUserService = context.getBean(CustomUserService.class);
		System.out.println(customUserService.getUser("Ullash"));


		User user = context.getBean(User.class);
		user.getUser("Ami");


	}

}
