package com.team.cobi.sales.repository;

import com.team.cobi.sales.entity.Client;
import com.team.cobi.sales.entity.SalesEstimate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesEstimateRepository extends JpaRepository<SalesEstimate, String> {
    Page<SalesEstimate> findByDeleteFlagFalse(Pageable pageable);

}