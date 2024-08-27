package org.sergio.reactive_product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class ReactiveProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProductServiceApplication.class, args);
	}

}
