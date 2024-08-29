package org.sergio.products_orders.service;

import org.sergio.products_orders.model.ProductOrder;
import org.sergio.products_orders.producers.ProductOrderPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    Logger logger = LoggerFactory.getLogger(ProductOrderServiceImpl.class);
    
    final ProductOrderPublisher productOrderPublisher;

    public ProductOrderServiceImpl(ProductOrderPublisher productOrderPublisher){
        this.productOrderPublisher = productOrderPublisher;
    }

    @Override
    public void createNewProductOrder(ProductOrder productOrder) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewProductOrder'");
    }
    
}
