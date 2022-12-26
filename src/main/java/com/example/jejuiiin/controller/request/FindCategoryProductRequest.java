package com.example.jejuiiin.controller.request;

import lombok.Getter;

@Getter
public class FindCategoryProductRequest {

	private String category;
	private String subCategory;
	private int page;
	private int size;

	public FindCategoryProductRequest(String category, String subCategory, int page) {
		this.category = category;
		this.subCategory = subCategory;
		this.page = page;
		this.size = 20;
	}
}
