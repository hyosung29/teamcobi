package com.team.cobi.sales.service;

import com.team.cobi.sales.dto.*;
import com.team.cobi.sales.entity.SalesEstimate;
import com.team.cobi.sales.repository.SalesEstimateQueryRepository;
import com.team.cobi.sales.repository.SalesEstimateRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesEstimateService {

    private final SalesEstimateRepository salesEstimateRepository;

    private  final SalesEstimateQueryRepository salesEstimateQueryRepository;

    @Transactional
    public void createSalesEstimate(SalesEstimateCreateRequest request) {
        salesEstimateRepository.save(new SalesEstimate(request));
    }

    @Transactional(readOnly = true)
    public Page<SalesEstimateListResponse> getSalesEstimatePage(SearchSalesEstimateList search, Pageable pageable) {
        return salesEstimateQueryRepository.searchPage(search, pageable);
    }


    @Transactional(readOnly = true)
    public SalesEstimateDetailResponse getSalesEstimateDetail(String id) {
        return salesEstimateQueryRepository.getSalesEstimateDetail(id);
    }

    @Transactional
    public void updateSalesEstimate(String id, SalesEstimateUpdateRequest request) {
        SalesEstimate salesEstimate = getSalesEstimate(id);
        salesEstimate.update(request);
        salesEstimateRepository.save(salesEstimate);
    }

    @Transactional
    public void deleteSalesEstimate(String id) {
        SalesEstimate salesEstimate = getSalesEstimate(id);
        salesEstimate.delete();
        salesEstimateRepository.save(salesEstimate);
    }

    @Transactional(readOnly = true)
    public SalesEstimate getSalesEstimate(String id) {
        SalesEstimate salesEstimate = salesEstimateRepository.findById(id).orElse(null);
        if(salesEstimate == null) throw  new NullException();
        return salesEstimate;
    }

}
