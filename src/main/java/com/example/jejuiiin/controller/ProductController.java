package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.FindCategoryProductRequest;
import com.example.jejuiiin.controller.response.CategoryProductResponse;
import com.example.jejuiiin.controller.response.PageResponse;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static com.example.jejuiiin.controller.response.message.SuccessMessage.FIND_SUCCESS_CATEGORY_PRODUCT_MSG;
import static com.example.jejuiiin.mapper.ProductMapperMapStruct.PRODUCT_MAPPER;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* 새 상품 나열하기 */
    @GetMapping("/newitems")
    public List<ProductResponse> getNewProducts(){
        return productService.getNewProducts();
    }

    @GetMapping("/{productId}")
    public ProductDetailResponse getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public PageResponse<?, ?> categoryProducts(@RequestParam(defaultValue = "") String category,
		@RequestParam(value = "subcategory", defaultValue = "") String subCategory,
        @RequestParam(defaultValue = "1") String page) {
        Page<Product> pageResult =
                productService.getCategoryProducts(new FindCategoryProductRequest(category, subCategory, Integer.parseInt(page)));
        Function<Product, CategoryProductResponse> fn = PRODUCT_MAPPER::productToCategoryProductResponse;
        return PageResponse.success(FIND_SUCCESS_CATEGORY_PRODUCT_MSG, pageResult, fn);
    }

}
