package org.sergio.reactive_product_service.controller;

import org.sergio.reactive_product_service.model.Product;
import org.sergio.reactive_product_service.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public Flux<Product> getProducts() {
        return productService.getCatalog();
    }

    @GetMapping("/product/category/{category}")
    public Flux<Product> getProductByCategory(@PathVariable String category) {
        return productService.getProductByCategory(category);
    }

    @GetMapping("/product/{code}")
    public Mono<ResponseEntity<Product>> getProductByCode(@PathVariable int code) {
        return productService.getProductByCode(code)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(value = "/product", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<Product>> saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .onErrorReturn(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @DeleteMapping(value = "/product/{code}")
    public Mono<Void> deleteProduct(@PathVariable int code) {
        return productService.deleteProduct(code);
    }

    @PutMapping("/product/{code}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable int code, @RequestParam("price") double price) {
        return productService.updateProductPrice(code, price)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
