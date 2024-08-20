package org.sergio.products_tracker.shoprestclient;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.model.Product;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class WebClientRestClientImpl implements ShopRestClient {
    private final String shopName;
    private final String url;

    public WebClientRestClientImpl(String shopName, String url) {
        this.shopName = shopName;
        this.url = url;
    }

    @Override
    public Flux<ShopItem> getShopCatalog() {
        WebClient webClient = WebClient.create(url);
        return webClient
                .get()
                .uri("/product")
                .retrieve()
                .bodyToFlux(Product.class)
                .map(item -> new ShopItem(item.getName(), item.getCategory(), item.getPrice(), shopName));
    }
}