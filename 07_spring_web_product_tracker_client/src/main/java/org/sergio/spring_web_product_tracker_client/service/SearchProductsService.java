package org.sergio.spring_web_product_tracker_client.service;

import org.sergio.spring_web_product_tracker_client.model.ShopItem;

import reactor.core.publisher.Flux;

public interface SearchProductsService {
    Flux<ShopItem> searchProductByMaxPrice(double priceMax);
}
