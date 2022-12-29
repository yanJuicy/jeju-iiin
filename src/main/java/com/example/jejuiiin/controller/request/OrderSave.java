package com.example.jejuiiin.controller.request;

import com.example.jejuiiin.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSave {
    private Member member;
    private Long productId;
    private String thumbnailImgUrl;
    private String name;
    private int sellingPrice;
    private int quantity;
    private int summation;

    @Builder
    public OrderSave(Member member, Long productId, String thumbnailImgUrl, String name, int sellingPrice, int quantity, int summation) {
        this.member = member;
        this.productId = productId;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.summation = summation;
    }
}
