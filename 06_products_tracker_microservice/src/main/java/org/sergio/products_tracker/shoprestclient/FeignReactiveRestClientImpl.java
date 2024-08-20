package org.sergio.products_tracker.shoprestclient;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.feignrestclient.FeignReactiveRestClient;
import org.springframework.stereotype.Component;
import feign.jackson.JacksonDecoder;
import feign.reactive.ReactorDecoder;
import feign.reactive.ReactorFeign;
import reactor.core.publisher.Flux;

@Component
public class FeignReactiveRestClientImpl implements ShopRestClient {

    @Override
    public Flux<ShopItem> getShopCatalog() {
        var productReactiveRestClient = ReactorFeign.builder()
                .decoder(new ReactorDecoder(new JacksonDecoder()))
                .target(FeignReactiveRestClient.class, "http://localhost:8080");

        return productReactiveRestClient.getShopCatalog()
                .map(item -> new ShopItem(item.getName(), item.getCategory(), item.getPrice(), "shop1"));
    }
}
