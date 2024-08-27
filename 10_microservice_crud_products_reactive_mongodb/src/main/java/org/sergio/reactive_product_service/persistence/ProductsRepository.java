package org.sergio.reactive_product_service.persistence;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsRepository extends ReactiveMongoRepository<ProductEntity, String> {
    Flux<ProductEntity> findByCategory(String category);

    Mono<Void> deleteByName(String name);

    @DeleteQuery("{ 'price':{$lt:?0} }")
    Mono<Void> deleteByPriceMax(double priceMax);
}
