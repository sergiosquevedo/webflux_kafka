package org.sergio.reactive_product_service.async;

import org.sergio.reactive_product_service.async.model.ProductOrderMessage;
import org.sergio.reactive_product_service.persistence.ProductEntity;
import org.sergio.reactive_product_service.persistence.ProductsRepository;
import org.sergio.reactive_product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class ProductStockUpdaterConsumer {
    Logger logger = LoggerFactory.getLogger(ProductStockUpdaterConsumer.class);

    final ProductService productService;
    final ProductsRepository productsRepository;

    public ProductStockUpdaterConsumer(ProductService productService, ProductsRepository productsRepository) {
        this.productService = productService;
        this.productsRepository = productsRepository;
    }

    /*
     * This consumer will attach to the products' topic listening when an order is
     * done.
     * 
     * When an order is finished, this method will update the current product's
     * stock in the repository
     */
    @KafkaListener(topics = "${org.sergio.products.topic}", groupId = "${org.sergio.products.group.stock}")
    public void productStockManagement(ProductOrderMessage productOrderMessage) {
        productService.getProductByCode(productOrderMessage.getCode())
                .doOnNext(product -> product.setStock(product.getStock() - productOrderMessage.getUnits()))
                .doOnNext(product -> logger.info("Updating stock for the product code: " + product.getCode()
                        + ". Available units: " + product.getStock()))
                .flatMap(product -> productsRepository
                        .save(new ProductEntity(false, product.getCode(), product.getName(),
                                product.getCategory(), product.getPrice(), product.getStock())))
                .doOnError(error -> logger.error(error.getMessage(), error))
                .subscribe();
    }
}
