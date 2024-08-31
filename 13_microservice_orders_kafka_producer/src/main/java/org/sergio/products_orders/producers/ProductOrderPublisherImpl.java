package org.sergio.products_orders.producers;

import org.dozer.DozerBeanMapper;
import org.sergio.products_orders.error.BrokerRegistrationException;
import org.sergio.products_orders.model.ProductOrder;
import org.sergio.products_orders.producers.model.ProductOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ProductOrderPublisherImpl implements ProductOrderPublisher {
    Logger logger = LoggerFactory.getLogger(ProductOrderPublisherImpl.class);

    final DozerBeanMapper dozerMapper;

    final KafkaTemplate<String, ProductOrderMessage> kafkaTemplate;

    public ProductOrderPublisherImpl(KafkaTemplate<String, ProductOrderMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.dozerMapper = new DozerBeanMapper();
    }

    @Override
    public void registerOrder(ProductOrder productOrder) throws BrokerRegistrationException {
        var kafkaBrokerResponse = kafkaTemplate.send("org.sergio.products.topic",
                dozerMapper.map(productOrder, ProductOrderMessage.class));

        kafkaBrokerResponse.whenCompleteAsync((response, error) -> {
            if (error != null) {
                logger.error(error.getMessage(), error);
                throw new BrokerRegistrationException("Error registering order with code: " + productOrder.getCode());
            }
            logger.info("Order " + response.getProducerRecord().value().getName() + " registered in the Topic "
                    + response.getProducerRecord().topic());
        });
    }
}
