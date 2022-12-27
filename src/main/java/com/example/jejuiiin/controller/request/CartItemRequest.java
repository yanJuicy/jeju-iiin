package com.example.jejuiiin.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CartItemRequest {

    @NotNull
    private Long productId;

    @Min(value = 1)
    private int quantity;
}
