package com.btg_pactual.btg_pactual_backend.service;

import com.btg_pactual.btg_pactual_backend.consumer.dto.OrderRecord;
import com.btg_pactual.btg_pactual_backend.entity.OrderEntity;
import com.btg_pactual.btg_pactual_backend.entity.OrderItem;
import com.btg_pactual.btg_pactual_backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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

}
