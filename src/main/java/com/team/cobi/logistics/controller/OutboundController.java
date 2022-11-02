package com.team.cobi.logistics.controller;

import com.team.cobi.logistics.dto.OutboundCreateRequest;
import com.team.cobi.logistics.dto.OutboundListResponse;
import com.team.cobi.logistics.dto.OutboundUpdateRequest;
import com.team.cobi.logistics.dto.SearchInboundList;
import com.team.cobi.logistics.entity.Outbound;
import com.team.cobi.logistics.service.InventoryService;
import com.team.cobi.logistics.service.OutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/outbound")

public class OutboundController {

    private final OutboundService outboundService;

    private final InventoryService inventoryService;

    @PostMapping("")
    public void createOutbound(@RequestBody OutboundCreateRequest request) {
        outboundService.createOutbound(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<OutboundListResponse>> getOutboundList(SearchInboundList search, Pageable pageable) {
        return ResponseEntity.ok().body(outboundService.getOutboundPage(search, pageable));
    }

    @GetMapping("/hold")
    public ResponseEntity<Page<OutboundListResponse>> getOutboundHoldList(SearchInboundList search, Pageable pageable) {
        return ResponseEntity.ok().body(outboundService.getOutboundHoldPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutboundListResponse> getOutboundDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(outboundService.getOutboundDetail(id));
    }

    @PutMapping("/{id}")
    public void updateOutbound(@PathVariable("id") String id, @RequestBody OutboundUpdateRequest request) {
        outboundService.updateOutbound(id); // 출고완료 상태창 수정
        inventoryService.inventoryDown(request);
    }

    @DeleteMapping("id")
    public void deleteOutbound(@PathVariable("id") String id) { outboundService.deleteOutbound(id); }

    @PutMapping("/updateStatus/{id}")
    public void updatedStatus(@PathVariable String id) {
        outboundService.updateSalesStatus(id);
        outboundService.insertSalesAccess(id);
    }

}
