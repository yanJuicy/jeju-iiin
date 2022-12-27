package com.example.jejuiiin.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCartResponse {
    private Long productId;
    private String thumbnailImgUrl;
    private String name;
    private int sellingPrice;
    private int quantity;
    private int summation;

    public MyCartResponse(Long productId, String thumbnailImgUrl, String name, int sellingPrice, int quantity, int summation) {
        this.productId = productId;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.summation = summation;
    }



}
