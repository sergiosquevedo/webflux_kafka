package org.sergio.reactive_product_service.persistence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductsRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
    Flux<ProductEntity> findByCategory(String category);

    Mono<Void> deleteByName(String name);

    @Query("delete from products where price > ?")
    Mono<Void> deleteByMaxPrice(double maxPrice);
}
