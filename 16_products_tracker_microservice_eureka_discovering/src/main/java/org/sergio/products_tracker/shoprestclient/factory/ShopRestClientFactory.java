package org.sergio.products_tracker.shoprestclient.factory;

import org.sergio.products_tracker.shoprestclient.ShopRestClient;

public interface ShopRestClientFactory {
    public ShopRestClient getShopRestClient(String shop);
}
