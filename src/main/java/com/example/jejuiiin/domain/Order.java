package com.example.jejuiiin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String thumbnailImgUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int sellingPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int summation;

    @Builder
    public Order(Member member, Long productId, String thumbnailImgUrl, String name, int sellingPrice, int quantity, int summation) {
        this.member = member;
        this.productId = productId;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.summation = summation;
    }
}
