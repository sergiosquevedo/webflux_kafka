package org.sergio.products_tracker.shoprestclient;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.shoprestclient.local.FeignReactiveRestClient;

import feign.jackson.JacksonDecoder;
import feign.reactive.ReactorDecoder;
import feign.reactive.ReactorFeign;
import reactor.core.publisher.Flux;

public class FeignReactiveRestClientImpl implements ShopRestClient {

    private final String shopName;
    private final String url;

    public FeignReactiveRestClientImpl(String shopName, String url){
        this.shopName = shopName;
        this.url = url;
    }

    @Override
    public Flux<ShopItem> getShopCatalog() {
        var productReactiveRestClient = ReactorFeign.builder()
                .decoder(new ReactorDecoder(new JacksonDecoder()))
                .target(FeignReactiveRestClient.class, url);

        return productReactiveRestClient.getShopCatalog()
                .map(item -> new ShopItem(item.getName(), item.getCategory(), item.getPrice(), shopName));
    }
}
