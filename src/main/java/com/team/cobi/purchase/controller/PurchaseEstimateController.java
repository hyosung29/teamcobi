package com.team.cobi.purchase.controller;


import com.team.cobi.purchase.dto.*;
import com.team.cobi.purchase.entity.PurchaseEstimate;
import com.team.cobi.purchase.service.PurchaseEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase-estimate")
public class PurchaseEstimateController {
    private final PurchaseEstimateService purchaseEstimateService;

    @PostMapping
    public void createPurchaseEstimate(@RequestBody PurchaseEstimateCreateRequest request) {
        purchaseEstimateService.createPurchaseEstimate(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<PurchaseEstimateListResponse>> getPurchaseEstimateList(SearchPurchaseEstimateList search, Pageable pageable) {
        return ResponseEntity.ok().body(purchaseEstimateService.getPurchaseEstimatePage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseEstimateDetailResponse> getPurchaseEstimateDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(purchaseEstimateService.getPurchaseEstimateDetail(id));
    }

    @PutMapping("/{id}")
    public void updatePurchaseEstimate(@PathVariable("id") String id, @RequestBody PurchaseEstimateUpdateRequest request) {
        purchaseEstimateService.updatePurchaseEstimate(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseEstimate(@PathVariable("id") String id) {
        purchaseEstimateService.deletePurchaseEstimate(id);
    }
}
