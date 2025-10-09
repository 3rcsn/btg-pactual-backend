package com.btg_pactual.btg_pactual_backend.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "orders", groupId = "btg-pactual")
    public void consumeMessage (String message) {
        System.out.println("Received: " + message);
    }

}
