package com.team.cobi.purchase.repository;

import com.team.cobi.purchase.entity.PurchaseEstimate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseEstimateRepository extends JpaRepository<PurchaseEstimate, String> {
    Page<PurchaseEstimate> findByDeleteFlagFalse(Pageable pageable);
}