package org.sergio.products_tracker.shoprestclient.feignrestclient;

import org.sergio.products_tracker.shoprestclient.model.Product;

import feign.RequestLine;
import reactor.core.publisher.Flux;

public interface FeignReactiveRestClient {
    @RequestLine("GET /product")
    Flux<Product> getShopCatalog();
}
