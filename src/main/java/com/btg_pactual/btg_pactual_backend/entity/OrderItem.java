package com.btg_pactual.btg_pactual_backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    private String product;

    private Integer quantity;

    private BigDecimal price;

}
