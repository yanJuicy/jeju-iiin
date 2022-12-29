package com.example.jejuiiin.mapper;

import com.example.jejuiiin.controller.request.*;
import com.example.jejuiiin.controller.response.OrderHistoryResponse;
import com.example.jejuiiin.domain.Member;
import com.example.jejuiiin.domain.Order;
import com.example.jejuiiin.domain.Product;


public class OrderMapper {

    public static Order toOrder(OrderSave orderSave){
        return Order.builder()
                .member(orderSave.getMember())
                .productId(orderSave.getProductId())
                .thumbnailImgUrl(orderSave.getThumbnailImgUrl())
                .name(orderSave.getName())
                .sellingPrice(orderSave.getSellingPrice())
                .quantity(orderSave.getQuantity())
                .summation(orderSave.getSummation())
                .build();
    }

    public static OrderSave toOrderSave(OrderItem orderItem, Member member, Product product){
        Long productId = orderItem.getProductId();
        int quantity = orderItem.getQuantity();
        return OrderSave.builder()
                .member(member)
                .productId(productId)
                .thumbnailImgUrl(product.getThumbnailImgUrl())
                .name(product.getName())
                .sellingPrice(product.getPrice())
                .quantity(quantity)
                .summation(product.getPrice()*quantity)
                .build();
    }

    public static OrderHistoryResponse toOrderHistoryResponse(Order order){
        String orderDate = order.getCreatedAt().toLocalDate().toString();

        return OrderHistoryResponse.builder()
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .thumbnailImgUrl(order.getThumbnailImgUrl())
                .name(order.getName())
                .sellingPrice(order.getSellingPrice())
                .quantity(order.getQuantity())
                .summation(order.getSummation())
                .orderDate(orderDate)
                .build();
    }
}
