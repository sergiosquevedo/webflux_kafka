package org.sergio.products_orders.controller;

import org.sergio.products_orders.error.BrokerRegistrationException;
import org.sergio.products_orders.model.ProductOrder;
import org.sergio.products_orders.service.ProductOrderService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProductOrderController {
    final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping(value = "/order", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ResponseEntity<ProductOrder>> createProductOrder(@RequestBody ProductOrder newProductOrder) {
        return productOrderService.createNewProductOrder(newProductOrder)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .onErrorMap(
                    BrokerRegistrationException.class,
                    e -> new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage()));
    }
}
