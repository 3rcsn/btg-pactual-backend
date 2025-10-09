package com.btg_pactual.btg_pactual_backend.controller;

import com.btg_pactual.btg_pactual_backend.producer.KafkaProducer;
import org.apache.kafka.common.protocol.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final KafkaProducer producer;

    public MessageController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String sendMessage(@RequestParam String message) {
        producer.send("orders", message);
        return "Message sent.";
    }
}