package com.example.jejuiiin.controller.request;

import lombok.Getter;

@Getter
public class FindCategoryProductRequest {
    
    private final int size;
    private final int page;
    private final String category;
    
    public FindCategoryProductRequest(String category, int page) {
        this.size = 20;
        this.category = category;
        this.page = page;
    }
}
