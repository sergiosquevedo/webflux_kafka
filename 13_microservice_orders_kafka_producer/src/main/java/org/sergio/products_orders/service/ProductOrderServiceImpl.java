package org.sergio.products_orders.service;

import org.sergio.products_orders.error.BrokerRegistrationException;
import org.sergio.products_orders.model.ProductOrder;
import org.sergio.products_orders.producers.ProductOrderPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    Logger logger = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    final ProductOrderPublisher productOrderPublisher;

    public ProductOrderServiceImpl(ProductOrderPublisher productOrderPublisher) {
        this.productOrderPublisher = productOrderPublisher;
    }

    @Override
    public Mono<ProductOrder> createNewProductOrder(ProductOrder productOrder) {
        try {
            productOrderPublisher.registerOrder(productOrder);
            return Mono.just(productOrder);
        }catch(KafkaException | BrokerRegistrationException ex){
            logger.error(ex.getMessage(), ex);
            return Mono.error(ex);
        }  
    }
}
