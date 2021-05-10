package com.cst363.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("cst363.controllers")
@SpringBootApplication
public class Cst363Application {

	public static void main(String[] args) {
		SpringApplication.run(Cst363Application.class, args);
	}

}
