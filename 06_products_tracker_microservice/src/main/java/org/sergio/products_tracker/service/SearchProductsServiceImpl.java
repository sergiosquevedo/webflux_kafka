package org.sergio.products_tracker.service;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.ShopRestClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class SearchProductsServiceImpl implements SearchProductsService {
    static final String SHOP1_URL = "http://localhost:8080";
    static final String SHOP2_URL = "http://localhost:8090";

    final ShopRestClient shopRestClient;

    public SearchProductsServiceImpl(ShopRestClient shopRestClient){
        this.shopRestClient = shopRestClient;
    }

    @Override
    public Flux<ShopItem> searchItemByMaxPrice(double priceMax) {
        Flux<ShopItem> flux1 = this.shopRestClient.getShopCatalog();
        Flux<ShopItem> flux2 = catalog(SHOP2_URL, "Shop2");
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrice() <= priceMax);
    }

    private Flux<ShopItem> catalog(String url, String shop) {
        WebClient webClient = WebClient.create(url);
        return webClient
                .get()
                .uri("/product")
                .retrieve()
                .bodyToFlux(ShopItem.class)
                .map(e -> {
                    e.setShop(shop);
                    return e;
                });
    }
}
