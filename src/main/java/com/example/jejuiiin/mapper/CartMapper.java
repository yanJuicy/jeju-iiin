package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.request.CartItemRequest;
import com.example.jejuiiin.controller.request.CartItemServiceRequest;
import com.example.jejuiiin.controller.response.MyCartResponse;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.security.UserDetailsImpl;

public class CartMapper {

    public static CartItem toCartItem(Member member, Product product, int quantity) {
        return CartItem.builder()
                .member(member)
                .productId(product.getProductId())
                .thumbnail_img_url(product.getThumbnailImgUrl())
                .name(product.getName())
                .sellingPrice(product.getPrice())
                .quantity(quantity)
                .summation(quantity * product.getPrice())
                .build();
    }

    public static CartItemServiceRequest toCartItemServiceRequest(CartItemRequest cartItemRequest,
                                                                  UserDetailsImpl userDetails) {
        return new CartItemServiceRequest(cartItemRequest.getProductId(), cartItemRequest.getQuantity(),
                userDetails.getMember());
    }

    public static MyCartResponse toMyCartResponse(CartItem cartItem) {
        return MyCartResponse.builder()
                .productId(cartItem.getProductId())
                .thumbnailImgUrl(cartItem.getThumbnailImgUrl())
                .name(cartItem.getName())
                .sellingPrice(cartItem.getSellingPrice())
                .quantity(cartItem.getQuantity())
                .summation(cartItem.getSummation())
                .build();
    }

}
