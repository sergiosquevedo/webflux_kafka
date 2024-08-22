package org.sergio.products_tracker.shoprestclient.feignrestclient;

import org.sergio.products_tracker.shoprestclient.model.Product;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import reactor.core.publisher.Flux;

public interface FeignReactiveRestClient {
    @RequestLine("GET /product")
    @Headers(value = "Authentication: Basic {token}")
    Flux<Product> getShopCatalog(@Param("token") String token);
}
