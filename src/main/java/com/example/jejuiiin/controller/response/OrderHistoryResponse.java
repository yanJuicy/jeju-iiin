package com.example.jejuiiin.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderHistoryResponse {
    private Long orderId;
    private Long productId;
    private String thumbnailImgUrl;
    private String name;
    private int sellingPrice;
    private int quantity;
    private int summation;
    private String orderDate;

    @Builder
    public OrderHistoryResponse(Long orderId, Long productId, String thumbnailImgUrl, String name, int sellingPrice, int quantity, int summation, String orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.summation = summation;
        this.orderDate = orderDate;
    }
}
