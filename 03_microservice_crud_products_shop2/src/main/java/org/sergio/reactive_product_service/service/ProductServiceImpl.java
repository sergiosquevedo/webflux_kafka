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
            List.of(new Product(110, "Limón", "Alimentación", 0.4, 20),
                    new Product(121, "Canela", "Alimentación", 6.20, 15),
                    new Product(132, "Cepillo de dientes", "Limpieza", 2.89, 30),
                    new Product(143, "Lamparilla", "Hogar", 15, 4),
                    new Product(154, "Sofá Cama", "Hogar", 1500, 10),
                    new Product(165, "Lentejas", "Alimentación", 0.20, 30),
                    new Product(176, "Escoba", "Limpieza", 1.40, 6),
                    new Product(187, "Abrillantador", "Limpieza", 2.7, 12)));

    @Override
    public Flux<Product> getCatalog() {
        return Flux.fromIterable(products)
                .delayElements(Duration.ofSeconds(1));
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
                .doOnNext(p -> products.removeIf(r -> r.getCode() == code))
                .then();
    }

    @Override
    public Mono<Product> updateProductPrice(int code, double price) {
        return getProductByCode(code)
                .doOnNext(p -> getProduct(code, price));
    }

    private void getProduct(int code, double price) {
        var product = products.stream().filter(u -> u.getCode() == code).findFirst();
        product.get().setPrice(price);
    }

}
