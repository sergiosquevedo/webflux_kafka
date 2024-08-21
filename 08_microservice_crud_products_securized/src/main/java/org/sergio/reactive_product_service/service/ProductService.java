package org.sergio.reactive_product_service.service;

import org.sergio.reactive_product_service.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getCatalog();
    Flux<Product> getProductByCategory(String category);
    Mono<Product> getProductByCode(int code);
    Mono<Product> saveProduct(Product product);
    Mono<Void> deleteProduct(int code);
    Mono<Product> updateProductPrice(int code, double price);
}
