package com.example.jejuiiin.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCartItemRequest {
    private Long productId;
    private int quantity;
}
