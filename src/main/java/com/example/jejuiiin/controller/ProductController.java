package com.example.jejuiiin.controller;

import static com.example.jejuiiin.mapper.ProductMapperMapStruct.PRODUCT_MAPPER;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jejuiiin.controller.request.FindCategoryProductRequest;
import com.example.jejuiiin.controller.response.CategoryProductPageResponse;
import com.example.jejuiiin.controller.response.CategoryProductResponseData;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* 새 상품 나열하기 */
    @GetMapping("/newitems")
    public Response<List<ProductResponse>> getNewProducts(){
        List<ProductResponse> response = productService.getNewProducts();

        return new Response<>(200,"신규 상품 조회 완료 ",response);

//        return productService.getNewProducts();
    }

    @GetMapping("/{productId}")
    public Response getProduct(@PathVariable Long productId) {
        ProductDetailResponse productdetail = productService.getProduct(productId);
        Response<ProductDetailResponse> response = new Response<>(200, "제품 상세조회 성공", productdetail);
        return response;

    }

    @GetMapping
    public CategoryProductPageResponse<?, ?> categoryProducts(@RequestParam(defaultValue = "") String category,
		@RequestParam(value = "subcategory", defaultValue = "") String subCategory,
        @RequestParam(defaultValue = "1") String page) {
        Page<Product> pageResult =
                productService.getCategoryProducts(new FindCategoryProductRequest(category, subCategory, Integer.parseInt(page)));
        Function<Product, CategoryProductResponseData> fn = PRODUCT_MAPPER::productToCategoryProductResponse;
        List<String> subCategoryList = productService.getSubCategoryList(category, subCategory);
        return CategoryProductPageResponse.success(200, "카테고리별 상품 조회 성공", pageResult, fn, subCategoryList);
    }

}
