package org.sergio.products_orders.producers;

import org.sergio.products_orders.error.BrokerRegistrationException;
import org.sergio.products_orders.model.ProductOrder;

public interface ProductOrderPublisher {
    void registerOrder(ProductOrder productOrder) throws BrokerRegistrationException;
}
