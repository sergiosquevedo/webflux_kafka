package org.sergio.products_shipping.controller;

import java.time.Duration;

import org.sergio.products_shipping.model.Shipping;
import org.sergio.products_shipping.service.ProductShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;

@RestController
public class ProductShippingController {
    Logger logger = LoggerFactory.getLogger(ProductShippingController.class);
    final ProductShippingService productShippingService;

    public ProductShippingController(ProductShippingService productShippingService) {
        this.productShippingService = productShippingService;
    }

    @GetMapping(value = "/pending")
    public Flux<Shipping> pendingShipping() {
        return productShippingService.pendientes()
                .delayElements(Duration.ofSeconds(1))
                .onErrorMap(e -> {
                    logger.error(e.getMessage(), e);
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                });
    }
}