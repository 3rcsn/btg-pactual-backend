package com.btg_pactual.btg_pactual_backend.consumer.dto;

import java.math.BigDecimal;

public record OrderItemRecord (String product, Integer quantity, BigDecimal price) {
}
