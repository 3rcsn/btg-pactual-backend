package com.btg_pactual.btg_pactual_backend.repository;

import com.btg_pactual.btg_pactual_backend.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
