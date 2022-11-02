package com.team.cobi.sales.controller;

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
@RequestMapping("/api/client")
public class SalesClientController {

    private final SalesClientService clientService;

    @PostMapping("")
    public void createSalesClient(@RequestBody SalesClientCreateRequest request) {
        clientService.createSalesClient(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<SalesClientListResponse>> getSalesClientList(SearchSalesClientList search, Pageable pageable){
        return ResponseEntity.ok().body(clientService.getSalesClientPage(search, pageable));
    }

    @GetMapping("/{id}")    //변수 쓸때는 중괄호로 감싸긴
    public ResponseEntity<Client> getSalesClientDetail(@PathVariable String id){
        return ResponseEntity.ok().body(clientService.getSalesClientDetail(id));
    }

    @PutMapping("/{id}")
    public void updateSalesClient(@PathVariable("id") String id, @RequestBody SalesClientUpdateRequest request){
        clientService.updateSalesClient(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSalesClient(@PathVariable("id") String id){
        clientService.deleteSalesClient(id);
    }


    // 주문서 등록에서 클라이언트 리스트 출력
    @GetMapping("/codes/{id}")
    public SalesClientCodeResponse getSalesClientCodes(@PathVariable("id") String id) {
        return clientService.getSalesClientCodes(id);
    }

    @GetMapping("/code")
    public List<SalesClientCodeResponse> getPurchaseClientCode() {
        return clientService.getSalesClientCode();
    }

}
