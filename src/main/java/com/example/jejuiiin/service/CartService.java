package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.CreateCartItemRequest;
import com.example.jejuiiin.controller.response.CreateCartItemResponse;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.example.jejuiiin.controller.exception.ExceptionMessage.NO_EXISTS_PRODUCT_MSG;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CreateCartItemResponse createCartItem(CreateCartItemRequest request, Member loginMember) {
        Long productId = request.getProductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));

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
        return new CreateCartItemResponse(newCartItem.getCartItemId());
    }


}
