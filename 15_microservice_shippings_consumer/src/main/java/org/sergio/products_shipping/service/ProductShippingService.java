package org.sergio.products_shipping.service;

import org.sergio.products_shipping.model.Shipping;

import reactor.core.publisher.Flux;

public interface ProductShippingService {
    Flux<Shipping> pendientes();
}
