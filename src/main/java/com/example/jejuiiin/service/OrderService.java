package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.OrderItem;
import com.example.jejuiiin.controller.request.OrderSave;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Order;
import com.example.jejuiiin.mapper.OrderMapper;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.OrderRepository;
import com.example.jejuiiin.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;


    /* 상품 주문하기 */
    @Transactional
    public void order(OrderItem orderItem, UserDetails userDetails) {
        String loginId = userDetails.getUsername();
        Member member = ServiceUtil.findMember(loginId);

        OrderSave orderSave = OrderMapper.toOrderSave(orderItem, member);
        Order order = OrderMapper.toOrder(orderSave);

        orderRepository.save(order);
    }

    /* 장바구니에 있는 상품 주문하기 */
    @Transactional
    public void orderCartItem(List<OrderItem> productList, UserDetails userDetails) {
        String loginId = userDetails.getUsername();
        Member member = ServiceUtil.findMember(loginId);

        for(OrderItem orderItem : productList){
            order(orderItem, userDetails);

            /* 구매한 상품 장바구니에서 삭제하기 */
            CartItem cartItem = ServiceUtil.findCartItem(orderItem.getProductId(), member);
            cartRepository.deleteById(cartItem.getCartItemId());
        }
    }
}
