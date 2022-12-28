package com.example.jejuiiin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "ORDERS")
@NoArgsConstructor
@Entity
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
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
