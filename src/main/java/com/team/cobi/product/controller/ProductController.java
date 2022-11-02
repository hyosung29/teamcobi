package com.team.cobi.product.controller;

import com.team.cobi.product.dto.*;
import com.team.cobi.product.entity.Product;
import com.team.cobi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.createProduct(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductListResponse>> getProductList(SearchProductList search, Pageable pageable){
        return ResponseEntity.ok().body(productService.getProductPage(search, pageable));
    }

    @GetMapping("/{id}")    //변수 쓸때는 중괄호로 감싸긴
    public ResponseEntity<Product> getProductDetail(@PathVariable String id){
        return ResponseEntity.ok().body(productService.getProductDetail(id));
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") String id, @RequestBody ProductUpdateRequest request){
        productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
    }


    // 주문서 등록에서 클라이언트 리스트 출력
    @GetMapping("/codes")
    public List<ProductCodeResponse> getProductCodes() {
        return productService.getProductCodes();
    }
}
