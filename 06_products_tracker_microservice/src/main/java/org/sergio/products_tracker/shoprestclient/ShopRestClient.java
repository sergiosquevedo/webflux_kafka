package org.sergio.products_tracker.shoprestclient;

import org.sergio.products_tracker.model.ShopItem;
import reactor.core.publisher.Flux;

public interface ShopRestClient {
    public Flux<ShopItem> getShopCatalog();
}
