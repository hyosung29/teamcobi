package com.team.cobi.product.repository;

import com.team.cobi.product.entity.Product;
import com.team.cobi.sales.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByDeleteFlagFalse(Pageable pageable);

    // 거래처명 불러오기 위한 작업 - 진아
    List <Product> findByDeleteFlagFalse();

}