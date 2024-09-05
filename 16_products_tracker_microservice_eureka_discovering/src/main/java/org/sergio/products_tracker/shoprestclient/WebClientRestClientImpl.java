package org.sergio.products_tracker.shoprestclient;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.model.Product;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class WebClientRestClientImpl implements ShopRestClient {
    private final String shopName;
    private final String url;
    private final WebClient.Builder webClientBuilder;

    public WebClientRestClientImpl(String shopName, String url, WebClient.Builder webClientBuilder) {
        this.shopName = shopName;
        this.url = url;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Flux<ShopItem> getShopCatalog() {
        WebClient webClient = webClientBuilder.build();
        return webClient
                .get()
                .uri(url + "/product")
                .retrieve()
                .bodyToFlux(Product.class)
                .map(item -> new ShopItem(item.getName(), item.getCategory(), item.getPrice(), shopName));
    }
}