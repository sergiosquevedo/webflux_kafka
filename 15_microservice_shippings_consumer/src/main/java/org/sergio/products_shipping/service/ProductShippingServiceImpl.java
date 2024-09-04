package org.sergio.products_shipping.service;

import javax.management.RuntimeErrorException;

import org.sergio.products_shipping.model.Shipping;
import org.sergio.products_shipping.repository.ProductShippingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductShippingServiceImpl implements ProductShippingService {

    final Logger logger = LoggerFactory.getLogger(ProductShippingServiceImpl.class);

    final ProductShippingRepository productShippingRepository;

    public ProductShippingServiceImpl(ProductShippingRepository productShippingRepository) {
        this.productShippingRepository = productShippingRepository;
    }

    @Override
    public Flux<Shipping> pendientes() {
        return productShippingRepository.getByPendingStatus()
                .map(entity -> new Shipping(entity.getIdShipping().toString(), entity.getProduct(), entity.getDateShipping(),
                        entity.getAddress(), entity.getStatus()));
    }
}
