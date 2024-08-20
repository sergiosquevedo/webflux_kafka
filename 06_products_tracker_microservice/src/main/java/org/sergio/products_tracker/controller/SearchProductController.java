package org.sergio.products_tracker.controller;

import org.sergio.products_tracker.model.ShopItem;
import org.sergio.products_tracker.service.SearchProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class SearchProductController {
    final SearchProductsService searchProductsService;

    @Autowired
    public SearchProductController(SearchProductsService searchProductsService){
        this.searchProductsService = searchProductsService;
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<Flux<ShopItem>> elemetsPrice(@RequestParam("price") double priceMax){
        return new ResponseEntity<>(searchProductsService.searchItemByMaxPrice(priceMax), HttpStatus.OK);
    }
}
