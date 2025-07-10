package com.btg_pactual.btg_pactual_backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "orders")
public class OrderEntity {

    @MongoId
    private Long orderId;

    private Long customerId;

    private BigDecimal total;

    private List<OrderItem> items;

}
