package org.sergio.products_orders.service;

import org.sergio.products_orders.model.ProductOrder;

import reactor.core.publisher.Mono;

public interface ProductOrderService {
    Mono<ProductOrder> createNewProductOrder(ProductOrder productOrder);
}
