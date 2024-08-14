package org.sergio.reactive_product_service.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.sergio.reactive_product_service.model.Product;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private static List<Product> products = new ArrayList<>(
            List.of(new Product(100, "Azucar", "Alimentación", 1.10, 20),
                    new Product(101, "Leche", "Alimentación", 1.20, 15),
                    new Product(102, "Jabón", "Limpieza", 0.89, 30),
                    new Product(103, "Mesa", "Hogar", 125, 4),
                    new Product(104, "Televisión", "Hogar", 650, 10),
                    new Product(105, "Huevos", "Alimentación", 2.20, 30),
                    new Product(106, "Fregona", "Limpieza", 3.40, 6),
                    new Product(107, "Detergente", "Limpieza", 8.7, 12)));

    @Override
    public Flux<Product> getCatalog() {
        return Flux.fromIterable(products)
                .delayElements(Duration.ofSeconds(2));
    }

    @Override
    public Flux<Product> getProductByCategory(String category) {
        return getCatalog().filter(p -> p.getCategory().equals(category));
    }

    @Override
    public Mono<Product> getProductByCode(int code) {
        return getCatalog().filter(p -> p.getCode() == code).next();
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return getProductByCode(product.getCode())
                .doOnSuccess(p -> {
                    if (p != null)
                        throw new RuntimeException("Product already exist");

                    products.add(product);
                })
                .thenReturn(product);
    }

    @Override
    public Mono<Void> deleteProduct(int code) {
        return getProductByCode(code)
                .doOnSuccess(p -> products.removeIf(r -> r.getCode() == code))
                .then();
    }

    @Override
    public Mono<Product> updateProductPrice(int code, double price) {
        return getProductByCode(code)
                .doOnNext(p -> {
                    var product = products.stream().filter(u -> u.getCode() == code).findFirst();
                    if (product.isEmpty())
                        throw new RuntimeException("Product not found");

                    product.get().setPrice(price);
                });
    }

}
