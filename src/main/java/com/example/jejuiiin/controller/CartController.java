package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.CartItemRequest;
import com.example.jejuiiin.controller.request.CartItemServiceRequest;
import com.example.jejuiiin.controller.request.DeleteCartItemRequest;
import com.example.jejuiiin.controller.response.CartItemResponse;
import com.example.jejuiiin.controller.response.MyCartResponse;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.mapper.CartMapper;
import com.example.jejuiiin.security.UserDetailsImpl;
import com.example.jejuiiin.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public Response<?> newCartItem(@RequestBody CartItemRequest newCartItem, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var data = cartService.createCartItem(CartMapper.toCartItemServiceRequest(newCartItem, userDetails));
        return Response.success(201, "장바구니 등록 성공", data);
    }

    @PatchMapping
    public Response<?> replaceCartItem(@RequestBody CartItemRequest replaceCartItem, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        var data = cartService.updateCartItem(CartMapper.toCartItemServiceRequest(replaceCartItem, userDetails));
        return Response.success(200, "장바구니 변경 성공", data);
    }

    @GetMapping
    public Response<?> getMyCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MyCartResponse> data = cartService.showMyCart(userDetails);
        return Response.success(200, "장바구니 조회 성공", data);
    }

    @DeleteMapping("/items")
    public Response<?> deleteCartItem(@RequestBody DeleteCartItemRequest deleteCartItemRequest, @AuthenticationPrincipal UserDetailsImpl userDetails){
      cartService.deleteByIdList(deleteCartItemRequest);
      List<MyCartResponse> data = cartService.showMyCart(userDetails);
       return Response.success(200,"선택상품 삭제 완료",data);
    }
}
