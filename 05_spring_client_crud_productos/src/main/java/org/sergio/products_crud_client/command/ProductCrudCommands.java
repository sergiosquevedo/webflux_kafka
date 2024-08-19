package org.sergio.products_crud_client.command;

import org.sergio.products_crud_client.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Component
public class ProductCrudCommands implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(ProductCrudCommands.class);

    final WebClient productsWebClient;

    public ProductCrudCommands() {
        this.productsWebClient = WebClient.create("http://localhost:8080");
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0)
            throw new IllegalArgumentException(
                    "Is mandatory to use one of these modes: [search {code}], [catalog], [create], [delete {code}], [update {code} {price}]");

        // TODO Use WebFlux's WebClient requires to use the Spring Reactor Web
        // dependency.
        // I don't like this approach, this app is a command line one and should not be
        // required
        // to start a Netty server in order to consume an externa Http service, reactive
        // or not.
        // Maybe I can use other RestClient like Feign in order to avoid use Spring
        // Reactor web but
        // is not the course's objetive.

        switch (args[0]) {
            case "catalog" -> getProductCatalog();
            case "create" -> createNewProduct();
            case "search" -> searchProduct(args);
            case "delete" -> deleteProduct(args);
            case "update" -> updateProduct(args);
            default -> throw new IllegalArgumentException(
                    "Is mandatory to use one of these modes: [create], [catalog], [delete code], [update code price]");
        }

    }

    private void updateProduct(String... args) {
        if (args.length == 1)
            throw new IllegalArgumentException(
                    "If you want to update a product, is mandatory to pass the product's code");
        if (args.length == 2)
            throw new IllegalArgumentException(
                    "If you want to update a product, is mandatory to pass the new product's price");

        var response = this.productsWebClient
                .put()
                .uri("/product/" + args[1] + "?price=" + args[2])
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode() == HttpStatus.NOT_FOUND ? Mono.error(ex) : Mono.empty());

        response.subscribe(p -> logger.info(p.toString()), e -> logger.error(e.getMessage()));
    }

    private void deleteProduct(String... args) {
        if (args.length == 1)
            throw new IllegalArgumentException(
                    "If you want to delete a product, is mandatory to pass the product's code");

        var response = this.productsWebClient
                .delete()
                .uri("/product/" + args[1])
                .retrieve()
                .toBodilessEntity()
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode() == HttpStatus.NOT_FOUND ? Mono.error(ex) : Mono.empty());

        response.subscribe(r -> logger.info(r.getStatusCode().toString()), e -> logger.error(e.getMessage()));
    }

    private void searchProduct(String... args) {
        if (args.length == 1)
            throw new IllegalArgumentException(
                    "If you want to search a product, is mandatory to pass the product's code");

        var response = this.productsWebClient
                .get()
                .uri("/product/" + args[1])
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getStatusCode() == HttpStatus.NOT_FOUND ? Mono.error(ex) : Mono.empty());

        response.subscribe(p -> logger.info(p.toString()), e -> logger.error(e.getMessage()));
    }

    private void createNewProduct() {
        logger.info("Creating produt's catalog");

        var newProduct = new Product(108, "Molinillo", "Cocina", 100, 23);

        var createdProduct = this.productsWebClient
                .post()
                .uri("/product")
                .body(Mono.just(newProduct), Product.class)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class);

        createdProduct
                .subscribe(p -> logger.info(p.toString()), e -> logger.error(e.getMessage()));
    }

    private void getProductCatalog() {
        logger.info("Getting product's");

        var response = this.productsWebClient
                .get()
                .uri("/product")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Product.class);

        response.subscribe(p -> logger.info(p.toString()));
    }

}
