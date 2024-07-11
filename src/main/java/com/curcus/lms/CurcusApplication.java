package com.curcus.lms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "com.curcus.lms")
public class CurcusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurcusApplication.class, args);
	}
	@Bean
    public CommandLineRunner testConnection(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                jdbcTemplate.execute("SELECT 1");
                System.out.println("Connection successful!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
