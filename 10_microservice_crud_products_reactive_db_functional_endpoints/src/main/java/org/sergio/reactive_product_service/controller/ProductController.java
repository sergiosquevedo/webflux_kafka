package org.sergio.reactive_product_service.controller;

import org.sergio.reactive_product_service.exception.ProductAlreadyExistException;
import org.sergio.reactive_product_service.model.Product;
import org.sergio.reactive_product_service.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Bean
    public RouterFunction<ServerResponse> productsRouter() {
        return RouterFunctions.route(
                RequestPredicates.GET("/product"),
                request -> ServerResponse.ok().body(productService.getCatalog(), Product.class))
                .andRoute(
                        RequestPredicates.GET("/product/category/{category}"),
                        request -> ServerResponse
                                .ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(productService.getProductByCategory(request.pathVariable("category")),
                                        Product.class))
                .andRoute(
                        RequestPredicates.GET("/product/{code}"),
                        request ->
                        // Below, we are getting the path variable from the request. Keep in mind that
                        // if the code is not an integer, this route
                        // will return a Internal Server Exception (500). We are focusing in the
                        // Functional Routers, we are not going to sanitize the
                        // request provided by the service's consumer
                        productService.getProductByCode(Integer.valueOf(request.pathVariable("code"))) // UNSAFE!!
                                .flatMap(response -> ServerResponse
                                        .ok()
                                        .contentType(MediaType.TEXT_EVENT_STREAM)
                                        .bodyValue(response))
                                .switchIfEmpty(ServerResponse.notFound().build()))
                .andRoute(
                        RequestPredicates.POST("/product"),
                        request -> request.bodyToMono(Product.class)
                                .flatMap(product -> ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(MediaType.TEXT_EVENT_STREAM)
                                        .body(productService.saveProduct(product)
                                                .onErrorMap(
                                                        ProductAlreadyExistException.class,
                                                        e -> new ResponseStatusException(HttpStatus.CONFLICT)),
                                                Product.class)))
                .andRoute(
                        RequestPredicates.DELETE("/product/{code}"),
                        request -> ServerResponse.ok()
                                .build(productService.deleteProduct(Integer.valueOf(request.pathVariable("code")))))
                .andRoute(
                        RequestPredicates.PUT("/product/{code}"),
                        request -> productService.updateProductPrice(
                                Integer.valueOf(request.pathVariable("code")), // UNSAFE!!
                                Double.valueOf(request.queryParam("price").get())) // UNSAFE!!
                                .flatMap(response -> ServerResponse
                                        .ok()
                                        .contentType(MediaType.TEXT_EVENT_STREAM)
                                        .bodyValue(response))
                                .switchIfEmpty(ServerResponse.notFound().build()));
    }
}
