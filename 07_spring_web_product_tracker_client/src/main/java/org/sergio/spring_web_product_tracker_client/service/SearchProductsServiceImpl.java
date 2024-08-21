package org.sergio.spring_web_product_tracker_client.service;

import org.sergio.spring_web_product_tracker_client.model.ShopItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class SearchProductsServiceImpl implements SearchProductsService {
    @Value("${products.tracker.service.url}")
    private String productsTrackerUrl;

    @Override
    public Flux<ShopItem> searchProductByMaxPrice(double priceMax) {
        WebClient webClient = WebClient.create(productsTrackerUrl);
        return webClient
                .get()
                .uri("/catalog?price="+priceMax)
                .retrieve()
                .bodyToFlux(ShopItem.class);
    }
}
