package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.OrderItem;
import com.example.jejuiiin.controller.request.OrderRequest;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Response order(@RequestBody OrderRequest orderRequest, @AuthenticationPrincipal UserDetails userDetails){
        List<OrderItem> productList = orderRequest.getProductList();

        /* 장바구니의 상품을 구매했을 경우 */
        if(orderRequest.isInCart()){
            orderService.orderCartItem(productList, userDetails);
        }

        /* 상세 페이지에서 상품을 구매했을 경우 */
        else {
            OrderItem orderItem = productList.get(0);
            orderService.order( orderItem, userDetails);
        }

        return new Response(200, "주문이 완료되었습니다.", null);
    }
}
