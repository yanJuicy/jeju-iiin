package com.example.jejuiiin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String thumbnail_img_url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int summation;

    @Builder
    public CartItem(Member member, Long productId, String thumbnail_img_url, String name,
                    int sellingPrice, int quantity, int summation) {
        this.member = member;
        this.productId = productId;
        this.thumbnail_img_url = thumbnail_img_url;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.summation = summation;
    }
}