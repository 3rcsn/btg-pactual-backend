package com.btg_pactual.btg_pactual_backend.consumer;

import com.btg_pactual.btg_pactual_backend.consumer.dto.OrderRecord;
import com.btg_pactual.btg_pactual_backend.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final OrderService orderService;

    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "orders", groupId = "btg-pactual")
    public String consumeMessage (OrderRecord order) {

        System.out.println("Received: " + order.toString());

        orderService.save(order);

        return order.toString();

    }

}
