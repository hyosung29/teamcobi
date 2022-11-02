package com.team.cobi.logistics.controller;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.service.InboundService;
import com.team.cobi.logistics.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inbound")
public class InboundController {

    private final InboundService inboundService;

    private final InventoryService inventoryService;

    @PostMapping("")
    public void createInbound(@RequestBody InboundCreateRequest request) {
        inboundService.createInbound(request);
    }

    @PutMapping("/updateStatus/{id}")
    public void updateStatus(@PathVariable String id) {
        inboundService.updatePurchaseStatus(id);
        inboundService.insertOrderAccess(id);
    }


    @GetMapping("")
    public ResponseEntity<Page<InboundListResponse>> getInboundList(SearchInboundList search, Pageable pageable) {
        return ResponseEntity.ok().body(inboundService.getInboundPage(search, pageable));
    }

    @GetMapping("/hold")
    public ResponseEntity<Page<InboundListResponse>> getInboundHoldList(SearchInboundList search, Pageable pageable) {
        return ResponseEntity.ok().body(inboundService.getInboundHoldPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InboundListResponse> getInboundDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(inboundService.getInboundDetail(id));
    }

    @PutMapping("/{id}")
    public void updateInbound(@PathVariable("id") String id, @RequestBody InboundUpdateRequest request) {
        inboundService.updateInbound(id, request);
        inventoryService.createInventory(request);
    }


    @DeleteMapping("/{id}")
    public void deleteInbound(@PathVariable("id") String id ) { inboundService.deleteWarehouse(id); }


}
