package com.team.cobi.product.service;

import com.team.cobi.product.dto.*;
import com.team.cobi.product.entity.Product;
import com.team.cobi.product.repository.ProductQueryRepository;
import com.team.cobi.product.repository.ProductRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    @Transactional //모두 성공하거나, 모두 실패하거나
    public void createProduct(ProductCreateRequest request) {
        productRepository.save(new Product(request)); //insert 후에는 save 해서 db 저장
    }

    @Transactional(readOnly = true) //읽기 전용
    public Page<ProductListResponse> getProductPage(SearchProductList search, Pageable pageable){
        return productQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Product getProductDetail(String id) {
        return getProduct(id);
    }

    @Transactional(readOnly = false)
    public Product getProduct(String id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) throw new NullException();
        return product;
    }

    @Transactional
    public void updateProduct(String id, ProductUpdateRequest request) {
        Product product = getProduct(id); //db에 매개변수로 받은 id와 같은게 있어? 있으면
        product.update(request);  //바구니 변수에 담아온 request 값을 db에 업데이트해
        productRepository.save(product);    //dao.저장
    }

    public void deleteProduct(String id) {
        Product product = getProduct(id);
        product.delete();
        productRepository.save(product);
    }

    // 주문서 등록에서 클라이언트 리스트 출력을 위한 서비스 (진아)
    @Transactional
    public List <ProductCodeResponse> getProductCodes() {
        List<Product> productList = productRepository.findByDeleteFlagFalse();
        return productList.stream().map(ProductCodeResponse::new).collect(Collectors.toList());
    }
}
