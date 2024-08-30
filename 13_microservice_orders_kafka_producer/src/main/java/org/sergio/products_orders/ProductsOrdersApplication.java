package org.sergio.products_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ProductsOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsOrdersApplication.class, args);
	}

}
