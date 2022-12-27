package com.example.jejuiiin.controller.request;

import com.example.jejuiiin.domain.Member;
import lombok.Getter;

@Getter
public class CartItemServiceRequest {
    private Long productId;
    private int quantity;
    private Member member;

    public CartItemServiceRequest(Long productId, int quantity, Member member) {
        this.productId = productId;
        this.quantity = quantity;
        this.member = member;
    }
}
