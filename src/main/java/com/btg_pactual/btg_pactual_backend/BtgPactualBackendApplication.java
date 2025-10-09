package com.btg_pactual.btg_pactual_backend;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class BtgPactualBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtgPactualBackendApplication.class, args);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("orders")
				.partitions(10)
				.replicas(1)
				.build();
	}

}
