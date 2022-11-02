package com.team.cobi.purchase.controller;

import com.team.cobi.sales.dto.*;
import com.team.cobi.sales.entity.Client;
import com.team.cobi.sales.service.SalesClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/purchase-client")
public class PurchaseClientController {

    private final SalesClientService clientService;

    @PostMapping("")
    public void createPurchaseClient(@RequestBody SalesClientCreateRequest request) {
        clientService.createPurchaseClient(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<SalesClientListResponse>> getPurchaseClientList(SearchSalesClientList search, Pageable pageable){
        return ResponseEntity.ok().body(clientService.getPurchaseClientPage(search, pageable));
    }

    @GetMapping("/{id}")    //변수 쓸때는 중괄호로 감싸긴
    public ResponseEntity<Client> getPurchaseClientDetail(@PathVariable String id){
        return ResponseEntity.ok().body(clientService.getPurchaseClientDetail(id));
    }

    @PutMapping("/{id}")
    public void updatePurchaseClient(@PathVariable("id") String id, @RequestBody SalesClientUpdateRequest request){
        clientService.updateSalesClient(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSalesClient(@PathVariable("id") String id){
        clientService.deleteSalesClient(id);
    }


    // 주문서 등록에서 클라이언트 리스트 출력
    @GetMapping("/codes/{id}")
    public SalesClientCodeResponse getPurchaseClientCodes(@PathVariable("id") String id) {
        return clientService.getPurchaseClientCodes(id);
    }

    @GetMapping("/code")
    public List<SalesClientCodeResponse> getPurchaseClientCode() {
        return clientService.getSalesClientCode();
    }

}