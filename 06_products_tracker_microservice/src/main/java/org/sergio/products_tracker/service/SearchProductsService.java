package org.sergio.products_tracker.service;

import org.sergio.products_tracker.model.ShopItem;

import reactor.core.publisher.Flux;

public interface SearchProductsService {
    Flux<ShopItem> searchItemByMaxPrice(double priceMax);
}
