package com.example.jejuiiin.controller.response;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class CategoryProductPageResponse<T, EN> extends PageResponse<T, EN> {

	List<String> categoryList;
	List<String> subCategoryList;

	public CategoryProductPageResponse(int httpStatusCode, String msg, Page<EN> page, Function<EN, T> fn,
		List<String> subCategoryList) {
		super(httpStatusCode, msg, page, fn);
		this.categoryList = List.of("MAGAZINE", "SHOP");
		this.subCategoryList = subCategoryList;
	}

	public static <T, EN> CategoryProductPageResponse<T, EN> success(int httpStatusCode, String message, Page<EN> page,
		Function<EN, T> fn, List<String> subCategoryList) {
		return new CategoryProductPageResponse<>(httpStatusCode, message, page, fn, subCategoryList);
	}

}
