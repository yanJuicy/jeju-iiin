package com.example.jejuiiin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProductCategory category;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProductSubCategory subCategory;

	@Builder
	public Product(String name, int price, String caption, String thumbnailImgUrl,
				   String detailImgUrl, ProductCategory category, ProductSubCategory subCategory) {
		this.name = name;
		this.price = price;
		this.caption = caption;
		this.thumbnailImgUrl = thumbnailImgUrl;
		this.detailImgUrl = detailImgUrl;
		this.category = category;
		this.subCategory = subCategory;
	}
}
