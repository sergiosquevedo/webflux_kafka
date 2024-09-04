package org.sergio.products_shipping.async;

import java.time.LocalDateTime;
import java.util.UUID;

import org.sergio.products_shipping.async.model.ProductOrderMessage;
import org.sergio.products_shipping.repository.ProductShippingRepository;
import org.sergio.products_shipping.repository.model.ShippingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductShippingConsumer {

    Logger logger = LoggerFactory.getLogger(ProductShippingConsumer.class);

    final ProductShippingRepository productShippingRepository;

    public ProductShippingConsumer(ProductShippingRepository productShippingRepository) {
        this.productShippingRepository = productShippingRepository;
    }

    @KafkaListener(topics = "${org.sergio.products.topic}", groupId = "${org.sergio.products.group.shipping}")
    public void shippingManagement(ProductOrderMessage productOrderMessage) {
        productShippingRepository.save(
                new ShippingEntity(UUID.randomUUID(), productOrderMessage.getName(),
                        LocalDateTime.now(), productOrderMessage.getAddress(), "pending"))
                .subscribe(
                    savedEntity -> logger.info("Product shipping saved properly, new shipping id: " + savedEntity.getIdShipping().toString()),
                    error -> logger.error(error.getMessage(), error)
                );
    }
}
