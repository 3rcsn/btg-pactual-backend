package com.btg_pactual.btg_pactual_backend.consumer.dto;

import java.util.List;

public record OrderRecord (Long orderId, Long customerId, List<OrderItemRecord> items) {
}
