package com.team.cobi.purchase.service;

import com.team.cobi.purchase.dto.*;
import com.team.cobi.purchase.entity.PurchaseEstimate;
import com.team.cobi.purchase.repository.PurchaseEstimateQueryRepository;
import com.team.cobi.purchase.repository.PurchaseEstimateRepository;
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
public class PurchaseEstimateService {

    private  final PurchaseEstimateRepository purchaseEstimateRepository;

    private  final PurchaseEstimateQueryRepository purchaseEstimateQueryRepository;

    @Transactional
    public void createPurchaseEstimate(PurchaseEstimateCreateRequest request) {
        purchaseEstimateRepository.save(new PurchaseEstimate(request));
    }

    @Transactional(readOnly = true)
    public Page<PurchaseEstimateListResponse> getPurchaseEstimatePage(SearchPurchaseEstimateList search, Pageable pageable) {
        return purchaseEstimateQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public PurchaseEstimateDetailResponse getPurchaseEstimateDetail(String id) {
        return purchaseEstimateQueryRepository.getPurchaseEstimateDetail(id);
    }

    @Transactional
    public void updatePurchaseEstimate(String id, PurchaseEstimateUpdateRequest request) {
        PurchaseEstimate purchaseEstimate = getPurchaseEstimate(id);
        purchaseEstimate.update(request);
        purchaseEstimateRepository.save(purchaseEstimate);
    }

    @Transactional
    public void deletePurchaseEstimate(String id) {
        PurchaseEstimate purchaseEstimate = getPurchaseEstimate(id);
        purchaseEstimate.delete();
        purchaseEstimateRepository.save(purchaseEstimate);
    }

    @Transactional(readOnly = true)
    public PurchaseEstimate getPurchaseEstimate(String id) {
        PurchaseEstimate purchaseEstimate = purchaseEstimateRepository.findById(id).orElse(null);
        if(purchaseEstimate == null) throw  new NullException();
        return purchaseEstimate;
    }

}
