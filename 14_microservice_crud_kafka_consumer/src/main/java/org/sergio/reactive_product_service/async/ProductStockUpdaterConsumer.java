package org.sergio.reactive_product_service.async;

import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(topics = "${org.sergio.products.topic}", groupId = "${org.sergio.products.group.stock}")
public class ProductStockUpdaterConsumer {

}
