package org.sergio.reactive_product_service.service;

import org.sergio.reactive_product_service.exception.ProductAlreadyExistException;
import org.sergio.reactive_product_service.model.Product;
import org.sergio.reactive_product_service.persistence.ProductEntity;
import org.sergio.reactive_product_service.persistence.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductsRepository productsRepository;

    public ProductServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public Flux<Product> getCatalog() {
        return productsRepository.findAll()
                .map(this::mapProductEntityIntoProduct);
    }

    @Override
    public Flux<Product> getProductByCategory(String category) {
        return productsRepository.findByCategory(category)
                .map(this::mapProductEntityIntoProduct);
    }

    @Override
    public Mono<Product> getProductByCode(int code) {
        return productsRepository.findById(code)
                .map(this::mapProductEntityIntoProduct);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return isProductExist(product.getCode())
                .filter(productExist -> !productExist)
                .switchIfEmpty(Mono.error(ProductAlreadyExistException::new))
                .map(p -> product)
                .map(p -> new ProductEntity(p.getCode(), p.getName(), p.getCategory(), p.getPrice(), p.getStock()))
                .doOnNext(p -> logger.info("Saving new product: " + p.toString()))
                .flatMap(productsRepository::save)
                .doOnNext(p -> logger.info("Product saved properly"))
                .map(p -> product);
    }

    private Mono<Boolean> isProductExist(int code) {
        return productsRepository.findById(code)
                .map(product -> true)
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<Void> deleteProduct(int code) {
        return productsRepository.findById(code)
                .doOnNext(p -> logger.info("Deleting product with code " + p.getCode()))
                .flatMap(p -> productsRepository.deleteById(code))
                .then();

    }

    @Override
    public Mono<Product> updateProductPrice(int code, double price) {
        return productsRepository.findById(code)
                .doOnNext(p -> logger.info("Updating product with code" + code))
                .doOnNext(p -> p.setPrice(price))
                .flatMap(productsRepository::save)
                .map(this::mapProductEntityIntoProduct);
    }

    private Product mapProductEntityIntoProduct(ProductEntity p) {
        return new Product(p.getCode(), p.getName(), p.getCategory(), p.getPrice(), p.getStock());
    }
}
