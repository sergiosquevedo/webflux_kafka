package org.sergio.products_tracker.service;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.factory.ShopRestClientFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SearchProductsServiceImpl implements SearchProductsService {
    final ShopRestClientFactory shopRestClientFactory;

    public SearchProductsServiceImpl(ShopRestClientFactory shopRestClientFactory){
        this.shopRestClientFactory = shopRestClientFactory;
    }

    @Override
    public Flux<ShopItem> searchItemByMaxPrice(double priceMax) {
        Flux<ShopItem> flux1 = this.shopRestClientFactory.getShopRestClient("shop1").getShopCatalog();
        Flux<ShopItem> flux2 = this.shopRestClientFactory.getShopRestClient("shop2").getShopCatalog();
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrice() <= priceMax);
    }
}
