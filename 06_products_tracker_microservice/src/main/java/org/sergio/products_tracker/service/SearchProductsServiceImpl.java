package org.sergio.products_tracker.service;

import org.sergio.products_tracker.model.ShopItem;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class SearchProductsServiceImpl implements SearchProductsService {
    String shop1Url = "http://localhost:8080";
    String shop2Url = "http://localhost:8090";

    @Override
    public Flux<ShopItem> searchItemByMaxPrice(double priceMax) {
        Flux<ShopItem> flux1 = catalog(shop1Url, "Shop1");
        Flux<ShopItem> flux2 = catalog(shop2Url, "Shop2");
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
