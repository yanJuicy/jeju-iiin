package com.example.jejuiiin.controller;

import com.example.jejuiiin.controller.request.FindCategoryProductRequest;
import com.example.jejuiiin.controller.response.CategoryProductResponse;
import com.example.jejuiiin.controller.response.PageResponse;
import com.example.jejuiiin.controller.response.ProductResponse;
import com.example.jejuiiin.controller.response.Response;
import com.example.jejuiiin.domain.Product;
import com.example.jejuiiin.mapper.ProductDetailResponse;
import com.example.jejuiiin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Response getProduct(@PathVariable Long productId) {
        ProductDetailResponse productdetail = productService.getProduct(productId);
        Response<ProductDetailResponse> response = new Response<>(200, "제품 상세조회 성공", productdetail);
        return response;

    }

    @GetMapping(params = {"category", "page"})
    public PageResponse<?, ?> categoryProducts(@RequestParam String category, @RequestParam String page) {
        Page<Product> pageResult =
                productService.getCategoryProducts(new FindCategoryProductRequest(category, Integer.parseInt(page)));
        Function<Product, CategoryProductResponse> fn = PRODUCT_MAPPER::productToCategoryProductResponse;
        return PageResponse.success(FIND_SUCCESS_CATEGORY_PRODUCT_MSG, pageResult, fn);
    }

}
