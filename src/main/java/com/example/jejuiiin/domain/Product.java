package com.example.jejuiiin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String caption;

    @Column(nullable = false)
    private String thumbnailImgUrl;

    @Column(nullable = false)
    private String detailImgUrl;

    @Builder
    public Product(String name, int price, String caption, String thumbnailImgUrl, String detailImgUrl) {
        this.name = name;
        this.price = price;
        this.caption = caption;
        this.thumbnailImgUrl = thumbnailImgUrl;
        this.detailImgUrl = detailImgUrl;
    }
}
