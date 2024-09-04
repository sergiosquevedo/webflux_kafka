package org.sergio.products_shipping.repository;

import java.util.UUID;

import org.sergio.products_shipping.repository.model.ShippingEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Flux;

public interface ProductShippingRepository extends ReactiveCrudRepository<ShippingEntity, UUID>{
    @Query("select * from shipping where status='pending'")
    Flux<ShippingEntity> getByPendingStatus();
}
