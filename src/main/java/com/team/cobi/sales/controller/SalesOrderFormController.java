package com.team.cobi.sales.controller;

import com.team.cobi.purchase.dto.*;
import com.team.cobi.purchase.service.OrderFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales-orderform")

public class SalesOrderFormController {
    private final OrderFormService orderFormService;

    @PostMapping("/{id}")
    public void SalesEstimateAccess(@PathVariable String id, @RequestBody SalesEstimateRequest request) {
        orderFormService.salesEstimateAccess(id, request);
    }

    @GetMapping("")
    public ResponseEntity<Page<OrderFormListResponse>> getSalesList(SearchPurchaseOrderFormList search, Pageable pageable) {
        return ResponseEntity.ok().body(orderFormService.getSalesPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderFormDetailResponse> getSalesDetail(@PathVariable String id) {
        return ResponseEntity.ok(orderFormService.getSalesDetail(id));
    }

    @PutMapping("/{id}")
    public void updateOrderForm(@PathVariable("id") String id, @RequestBody OrderFormUpdateRequest request) {
        orderFormService.updateSales(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderForm(@PathVariable("id") String id) {
        orderFormService.deleteSales(id);
    }

    @PutMapping("/updateStatus/{id}")
    public void updateStatus(@PathVariable String id, @RequestBody SalesEstimateRequest request) {
        orderFormService.updateStatus2(id);
        orderFormService.insertOrder2Access(id, request);
    }
}
