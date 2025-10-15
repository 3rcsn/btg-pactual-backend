package com.btg_pactual.btg_pactual_backend.service;

import com.btg_pactual.btg_pactual_backend.consumer.dto.OrderRecord;
import com.btg_pactual.btg_pactual_backend.controller.dto.OrderResponse;
import com.btg_pactual.btg_pactual_backend.entity.OrderEntity;
import com.btg_pactual.btg_pactual_backend.entity.OrderItem;
import com.btg_pactual.btg_pactual_backend.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save (OrderRecord orderRecord) {
        var orderEntity = new OrderEntity();

        orderEntity.setOrderId(orderRecord.orderId());
        orderEntity.setCustomerId(orderRecord.customerId());
        orderEntity.setItems(getItemList(orderRecord));
        orderEntity.setTotal(getTotal(orderRecord));

        orderRepository.save(orderEntity);
    }

    private static List<OrderItem> getItemList(OrderRecord orderRecord) {
        return orderRecord.items().stream()
                .map(i -> new OrderItem(i.product(), i.quantity(), i.price())).toList();
    }

    private BigDecimal getTotal(OrderRecord orderRecord) {
        return orderRecord.items()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
       var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

       return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {
        var aggregations = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "orders", Document.class);

        return new BigDecimal(Objects.requireNonNull(response.getUniqueMappedResult()).getOrDefault("total", BigDecimal.ZERO).toString());
    }
}
