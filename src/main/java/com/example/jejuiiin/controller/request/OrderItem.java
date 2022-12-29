package com.example.jejuiiin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItem {
    private Long productId;
    private int quantity;

    @Builder
    public OrderItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
