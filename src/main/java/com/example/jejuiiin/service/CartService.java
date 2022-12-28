package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.CartItemServiceRequest;
import com.example.jejuiiin.controller.response.CartItemResponse;
import com.example.jejuiiin.controller.response.MyCartResponse;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.CartMapper;
import com.example.jejuiiin.repository.CartRepository;
import com.example.jejuiiin.repository.ProductRepository;

import com.example.jejuiiin.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public CartItemResponse createCartItem(CartItemServiceRequest request) {
        Product product = findProduct(request.getProductId());
        Member loginMember = request.getMember();
        /* 장바구니에 이미 상품이 있으면 수량 +n */
        Optional<CartItem> savedCartItem = cartRepository.findByProductIdAndMember(product.getProductId(), loginMember);
        if (savedCartItem.isPresent()) {
            savedCartItem.get().plusQuantity(request.getQuantity());
            return new CartItemResponse(savedCartItem.get().getCartItemId());
        }

        CartItem cartItem = CartMapper.toCartItem(loginMember, product, request.getQuantity());

        CartItem newCartItem = cartRepository.save(cartItem);
        return new CartItemResponse(newCartItem.getCartItemId());
    }

    @Transactional
    public MyCartResponse updateCartItem(CartItemServiceRequest request) {
        Product product = findProduct(request.getProductId());
        Member loginMember = request.getMember();
        CartItem savedCartItem = findCartItem(product.getProductId(), loginMember);

        savedCartItem.updateQuantity(request.getQuantity());

        return CartMapper.toMyCartResponse(savedCartItem);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_PRODUCT_MSG.getMsg()));
    }

    private CartItem findCartItem(Long productId, Member member) {
        return cartRepository.findByProductIdAndMember(productId, member)
                .orElseThrow(() -> new NoSuchElementException(NO_EXISTS_CART_ITEM_MSG.getMsg()));
    }
    public List<MyCartResponse> showMyCart(UserDetailsImpl userDetails) {
        List<CartItem> cartItemList = cartRepository.findAllByMember(userDetails.getMember());

        List<MyCartResponse> list = new ArrayList<>();

        for(CartItem cartItem : cartItemList){
            MyCartResponse response = MyCartResponse.builder()
                    .productId(cartItem.getProductId())
                    .thumbnailImgUrl(cartItem.getThumbnailImgUrl())
                    .name(cartItem.getName())
                    .sellingPrice(cartItem.getSellingPrice())
                    .quantity(cartItem.getQuantity())
                    .summation(cartItem.getSummation())
                    .build();

            list.add(response);
        }
        return list;
    }
}
