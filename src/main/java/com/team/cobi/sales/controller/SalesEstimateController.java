package com.team.cobi.sales.controller;

import com.team.cobi.sales.dto.*;
import com.team.cobi.sales.entity.SalesEstimate;
import com.team.cobi.sales.service.SalesEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales-estimate")
public class SalesEstimateController {
    private final SalesEstimateService salesEstimateService;


    @PostMapping("")
    public void createSalesEstimate(@RequestBody SalesEstimateCreateRequest request) {
        salesEstimateService.createSalesEstimate(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<SalesEstimateListResponse>> getSalesEstimateList(SearchSalesEstimateList search, Pageable pageable) {
        return ResponseEntity.ok().body(salesEstimateService.getSalesEstimatePage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesEstimateDetailResponse> getSalesEstimateDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(salesEstimateService.getSalesEstimateDetail(id));
    }

    @PutMapping("/{id}")
    public void updateSalesEstimate(@PathVariable("id") String id, @RequestBody SalesEstimateUpdateRequest request) {
        salesEstimateService.updateSalesEstimate(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSalesEstimate(@PathVariable("id") String id) {
        salesEstimateService.deleteSalesEstimate(id);
    }


}
