package com.btg_pactual.btg_pactual_backend.controller;

import com.btg_pactual.btg_pactual_backend.entity.OrderEntity;
import com.btg_pactual.btg_pactual_backend.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducer producer;

    public OrderController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage (@RequestBody OrderEntity order) {
        producer.send("orders", String.valueOf(order));
        return ResponseEntity.ok("Message sent.");
    }

}