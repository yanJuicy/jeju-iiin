package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.CreateCartItemRequest;
import com.example.jejuiiin.controller.response.MyCartResponse;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.security.UserDetailsImpl;
import com.example.jejuiiin.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public Response<?> newCartItem(@RequestBody CreateCartItemRequest newCartItem, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var data = cartService.createCartItem(newCartItem, userDetails.getMember());
        return Response.success(201, "장바구니 등록 성공", data);
    }
    @GetMapping
    public Response<?> getMyCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<MyCartResponse> data = cartService.showMyCart(userDetails);
        return Response.success(200, "장바구니 조회 성공", data);
    }


}
