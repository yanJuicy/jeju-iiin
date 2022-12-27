package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.CartItemRequest;
import com.example.jejuiiin.controller.request.CartItemServiceRequest;
import com.example.jejuiiin.controller.response.CartItemResponse;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.NO_EXISTS_CART_ITEM_MSG;
import static com.example.jejuiiin.controller.exception.ExceptionMessage.NO_EXISTS_PRODUCT_MSG;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItemResponse createCartItem(CartItemRequest request, Member loginMember) {
        Long productId = request.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));

        /* 장바구니에 이미 상품이 있으면 수량 +1 */
        Optional<CartItem> savedCartItem = cartRepository.findByProductIdAndMember(productId, loginMember);
        if (savedCartItem.isPresent()) {
            savedCartItem.get().plusQuantity(request.getQuantity());
            return new CartItemResponse(savedCartItem.get().getCartItemId());
        }

        // mapper 변환 필요
        CartItem cartItem = CartItem.builder()
                .member(loginMember)
                .productId(productId)
                .thumbnail_img_url(product.getThumbnailImgUrl())
                .name(product.getName())
                .sellingPrice(product.getPrice())
                .quantity(request.getQuantity())
                .summation(request.getQuantity() * product.getPrice())
                .build();

        CartItem newCartItem = cartRepository.save(cartItem);
        return new CartItemResponse(newCartItem.getCartItemId());
    }

    @Transactional
    public CartItemResponse updateCartItem(CartItemServiceRequest request) {
        Long productId = request.getProductId();
        productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));

        Member loginMember = request.getMember();
        CartItem savedCartItem = cartRepository.findByProductIdAndMember(productId, loginMember)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_CART_ITEM_MSG.getMsg()));

        savedCartItem.updateQuantity(request.getQuantity());

        return new CartItemResponse(savedCartItem.getCartItemId());
    }


}
