package org.sergio.reactive_product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "persistence")
@SpringBootApplication
public class ReactiveProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProductServiceApplication.class, args);
	}

}
