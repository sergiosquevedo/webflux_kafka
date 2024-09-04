package org.sergio.products_shipping.service;

import org.sergio.products_shipping.model.Shipping;
import org.sergio.products_shipping.repository.ProductShippingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ProductShippingServiceImpl implements ProductShippingService {

    final Logger logger = LoggerFactory.getLogger(ProductShippingServiceImpl.class);

    final ProductShippingRepository ProductShippingRepository;

    public ProductShippingServiceImpl(ProductShippingRepository productShippingRepository) {
        this.ProductShippingRepository = productShippingRepository;
    }

    @Override
    public Flux<Shipping> pendientes() {
        return ProductShippingRepository.getByPendingStatus()
                .map(entity -> new Shipping(entity.getIdShipping().toString(), entity.getProduct(), entity.getDateShipping(),
                        entity.getAddress(), entity.getStatus()));
    }

}
