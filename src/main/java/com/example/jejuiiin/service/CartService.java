package com.example.jejuiiin.service;

import com.example.jejuiiin.controller.request.CartItemServiceRequest;
import com.example.jejuiiin.controller.request.DeleteCartItemRequest;
import com.example.jejuiiin.controller.response.CartItemResponse;
import com.example.jejuiiin.controller.response.MyCartResponse;
import com.example.jejuiiin.domain.CartItem;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.CartMapper;
import com.example.jejuiiin.repository.CartRepository;

import com.example.jejuiiin.security.UserDetailsImpl;
import com.example.jejuiiin.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ServiceUtil serviceUtil;


    @Transactional
    public CartItemResponse createCartItem(CartItemServiceRequest request) {
        Product product = serviceUtil.findProduct(request.getProductId());
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
        Product product = serviceUtil.findProduct(request.getProductId());
        Member loginMember = request.getMember();
        CartItem savedCartItem = serviceUtil.findCartItem(product.getProductId(), loginMember);

        savedCartItem.updateQuantity(request.getQuantity());

        return CartMapper.toMyCartResponse(savedCartItem);
    }

    public List<MyCartResponse> showMyCart(UserDetailsImpl userDetails) {
        List<CartItem> cartItemList = cartRepository.findAllByMember(userDetails.getMember());

        List<MyCartResponse> list = new ArrayList<>();

        for(CartItem cartItem : cartItemList){
            MyCartResponse response = MyCartResponse.builder()
                    .cartItemId(cartItem.getCartItemId())
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

    @Transactional
    public void deleteByIdList(DeleteCartItemRequest deleteCartItemRequest) {
        List<Long> deleteCartItem = deleteCartItemRequest.getCartItemIdList();

        for(int i = 0; i<deleteCartItem.size(); i++){

         cartRepository.deleteById(deleteCartItem.get(i));
        }
    }
}
