package com.btg_pactual.btg_pactual_backend.producer;

import com.btg_pactual.btg_pactual_backend.consumer.dto.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer (KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, OrderRecord order) {
        kafkaTemplate.send(topic, order);
        System.out.println("Sent: " + order.toString());
    }


}
