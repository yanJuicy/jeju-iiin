package com.example.jejuiiin.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItem> productList;
    private boolean inCart;

    @Builder
    public OrderRequest(List<OrderItem> productList, boolean inCart) {
        this.productList = productList;
        this.inCart = inCart;
    }
}
