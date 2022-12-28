package com.example.jejuiiin.service.util;

import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.NO_EXISTS_CART_ITEM_MSG;
import static com.example.jejuiiin.controller.exception.ExceptionMessage.NO_EXISTS_PRODUCT_MSG;

@Service
@RequiredArgsConstructor
public class ServiceUtil {
    private static final ProductRepository productRepository;
    private static final CartRepository cartRepository;

    public static Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));
    }

    public static CartItem findCartItem(Long productId, Member member) {
        return cartRepository.findByProductIdAndMember(productId, member)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_CART_ITEM_MSG.getMsg()));
    }
}
