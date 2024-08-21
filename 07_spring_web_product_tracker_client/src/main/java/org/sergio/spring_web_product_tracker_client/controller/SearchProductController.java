package org.sergio.spring_web_product_tracker_client.controller;

import org.sergio.spring_web_product_tracker_client.service.SearchProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchProductController {
    private static final int BUFFER_SIZE = 1;

    final SearchProductsService searchProductsService;

    public SearchProductController(SearchProductsService searchProductsService) {
        this.searchProductsService = searchProductsService;
    }

    @GetMapping(value = "/searchProductByMaxPrice")
    public String searchProductByMaxPrice(@RequestParam("price") double maxPrice, Model model) {
        var contextVariable = new ReactiveDataDriverContextVariable(
                searchProductsService.searchProductByMaxPrice(maxPrice), BUFFER_SIZE);

        model.addAttribute("searchProductByMaxPriceResponse", contextVariable);

        return "searchProductByMaxPriceResponse";
    }
}
