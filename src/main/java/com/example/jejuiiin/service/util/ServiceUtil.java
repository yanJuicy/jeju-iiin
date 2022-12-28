package com.example.jejuiiin.service.util;

import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.MemberRepository;
import com.example.jejuiiin.repository.ProductRepository;

import java.util.NoSuchElementException;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.*;

public class ServiceUtil {
    private static ProductRepository productRepository;
    private static CartRepository cartRepository;
    private static MemberRepository memberRepository;

    public static Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));
    }

    public static CartItem findCartItem(Long productId, Member member) {
        return cartRepository.findByProductIdAndMember(productId, member)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_CART_ITEM_MSG.getMsg()));
    }

    public static Member findMember(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXIST_MEMBER_MSG.getMsg()));
    }

    public static Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXIST_MEMBER_MSG.getMsg()));
    }
}
