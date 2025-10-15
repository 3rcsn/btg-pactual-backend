package com.btg_pactual.btg_pactual_backend.controller;

import com.btg_pactual.btg_pactual_backend.consumer.dto.OrderRecord;
import com.btg_pactual.btg_pactual_backend.controller.dto.ApiResponse;
import com.btg_pactual.btg_pactual_backend.controller.dto.PaginationResponse;
import com.btg_pactual.btg_pactual_backend.producer.KafkaProducer;
import com.btg_pactual.btg_pactual_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProducer producer;

    private final OrderService orderService;

    public OrderController(KafkaProducer producer, OrderService orderService) {
        this.producer = producer;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> sendMessage (@RequestBody OrderRecord order) {
        producer.send("orders", order);
        return ResponseEntity.ok("Message sent.");
    }

    @GetMapping("{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId (@PathVariable("customerId") Long customerId,
                                                    @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));

        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }


}