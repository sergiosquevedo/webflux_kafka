package org.sergio.products_orders.producers;

import org.dozer.DozerBeanMapper;
import org.sergio.products_orders.model.ProductOrder;
import org.sergio.products_orders.producers.model.ProductOrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
    public void registerOrder(ProductOrder productOrder) {
        var kafkaBrokerResponse = kafkaTemplate.send("test-topic", dozerMapper.map(productOrder, ProductOrderMessage.class));

        kafkaBrokerResponse.whenCompleteAsync((response, error) -> {
            if (error != null)
                logger.error(error.getMessage(), error);

            logger.info("Order " + response.getProducerRecord().value().getName() + " registered in the Topic "
                    + response.getProducerRecord().topic());
        });
    }
}
