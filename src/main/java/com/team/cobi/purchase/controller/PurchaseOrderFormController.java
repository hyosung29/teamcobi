package com.team.cobi.purchase.controller;

import com.team.cobi.purchase.dto.*;
import com.team.cobi.purchase.dto.OrderFormCreateRequest;
import com.team.cobi.purchase.dto.OrderFormListResponse;
import com.team.cobi.purchase.dto.OrderFormUpdateRequest;
import com.team.cobi.purchase.dto.SearchPurchaseOrderFormList;
import com.team.cobi.purchase.service.OrderFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // json 형식 = key : value
@RequiredArgsConstructor
@RequestMapping("/api/purchase-orderform")
// api 적는 이유 : 스웨거 구분
// vue의 async랑 url 맞추기
// 스웨거 : 화면(web) 이 없을 때 백엔드 처리를 확인해볼 수 있는 기능

// 리소스에 대한 조회, 등록, 수정, 삭제는 GET, POST, PUT, DELETE 메소드를 사용

public class PurchaseOrderFormController {

    private final OrderFormService orderFormService;

    @PostMapping("/{id}")
    public void PurchaseEstimateAccess(@PathVariable String id, @RequestBody PurchaseEstimateRequest request) {
        orderFormService.purchaseEstimateAccess(id, request);
    }

    //구매 주문서 목록
    @GetMapping("")
    // ResponseEntity : 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스
    public ResponseEntity<Page<OrderFormListResponse>> getPurchaseList(SearchPurchaseOrderFormList search, Pageable pageable) {
        // HTTP 상태가 200인(정상적인)애들을 body에 담아(화면에 표시) / 그런데 그 때 findByDeleteFlagFalse인 애들만 표시
        return ResponseEntity.ok().body(orderFormService.getPurchasePage(search, pageable));
    }

    // 구매 주문서 상세화면
    @GetMapping("/{id}")
    // @Controller에서 파라미터를 받는 방법은 2가지가 있는데
        // 1. /API_NAME?key1=val1   :: 아마 기존에 get방식으로 받던거
        // 2. /API_NAME/{value1}    :: 얘가 PathVariable
    public ResponseEntity<OrderFormDetailResponse> getPurchaseDetail(@PathVariable String id) {
        return ResponseEntity.ok(orderFormService.getPurchaseDetail(id));
    }

    @PutMapping("/{id}")
    public void updateOrderForm(@PathVariable("id") String id, @RequestBody OrderFormUpdateRequest request) {
        orderFormService.updatePurchase(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderForm(@PathVariable("id") String id){
        orderFormService.deletePurchase(id);
    }

    @PutMapping("/updateStatus/{id}")
    public void updateStatus(@PathVariable String id, @RequestBody PurchaseEstimateRequest request) {
        orderFormService.updateStatus(id);
        orderFormService.insertOrderAccess(id, request);
    }

}
