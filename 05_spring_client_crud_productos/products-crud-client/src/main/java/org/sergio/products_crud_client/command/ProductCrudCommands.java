package org.sergio.products_crud_client.command;

import org.sergio.products_crud_client.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class ProductCrudCommands implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(ProductCrudCommands.class);

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0)
            throw new IllegalArgumentException(
                    "Is mandatory to use one of these modes: [create], [catalog], [delete code], [update code price]");

        //TODO Use WebFlux's WebClient requires to use the Spring Reactor Web dependency. 
        // I don't like this approach, this app is a command line one and should not be required 
        // to start a Netty server in order to consume an externa Http service, reactive or not.
        // Maybe I can use other RestClient like Feign in order to avoid use Spring Reactor web but
        // is not the course's objetive.
        var productsWebClient = WebClient.create("http://localhost:8080");

        switch (args[0]) {
            case "catalog" -> getProductCatalog(productsWebClient);
            case "create" -> createNewProduct(productsWebClient);
            default -> throw new IllegalArgumentException(
                    "Is mandatory to use one of these modes: [create], [catalog], [delete code], [update code price]");
        }

    }

    private void createNewProduct(WebClient productsWebClient) {
        logger.info("Creating produt's catalog");

        var newProduct = new Product(108, "Molinillo", "Cocina", 100, 23);

        var createdProduct = productsWebClient
                .post()
                .uri("/product")
                .body(Mono.just(newProduct), Product.class)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class);

        createdProduct
           .subscribe(p -> logger.info(p.toString()), e -> logger.error(e.getMessage()));
    }

    private void getProductCatalog(WebClient productsWebClient) {
        logger.info("Getting product's");

        var response = productsWebClient
                .get()
                .uri("/product")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class);

        response.subscribe(p -> logger.info(p.toString()));
    }

}
