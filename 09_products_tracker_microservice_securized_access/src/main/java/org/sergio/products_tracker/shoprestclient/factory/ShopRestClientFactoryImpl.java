package org.sergio.products_tracker.shoprestclient.factory;

import java.util.HashMap;
import java.util.Map;

import org.sergio.products_tracker.shoprestclient.FeignReactiveRestClientImpl;
import org.sergio.products_tracker.shoprestclient.ShopRestClient;
import org.sergio.products_tracker.shoprestclient.WebClientRestClientImpl;
import org.springframework.stereotype.Component;

@Component
public class ShopRestClientFactoryImpl implements ShopRestClientFactory {

    private final Map<String, String> shopRouter = Map.of("shop1", "http://localhost:8080",
            "shop2", "http://localhost:8090");

    private Map<String, ShopRestClient> INSTANCE = new HashMap<>();

    @Override
    public ShopRestClient getShopRestClient(String shop) {
        if (INSTANCE.get(shop) == null) {
            switch (shop) {
                case "shop1" -> INSTANCE.put(shop, new FeignReactiveRestClientImpl(shop, shopRouter.get(shop)));
                case "shop2" -> INSTANCE.put(shop, new WebClientRestClientImpl(shop, shopRouter.get(shop)));
                default ->
                    throw new IllegalArgumentException("This shop is not configured yet");
            }

        }
        return INSTANCE.get(shop);
    }

}
