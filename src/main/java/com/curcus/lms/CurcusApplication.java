package com.curcus.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.curcus.lms")
public class CurcusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurcusApplication.class, args);
	}

}
