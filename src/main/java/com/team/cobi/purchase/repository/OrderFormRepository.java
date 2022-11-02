package com.team.cobi.purchase.repository;

import com.team.cobi.purchase.entity.OrderForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, String> {
    Page<OrderForm> findByDeleteFlagFalse(Pageable pageable);

    int countByPurchaseNumberLike(String purchaseNumber);

    int countBySalesNumberLike(String salesNumber);
}