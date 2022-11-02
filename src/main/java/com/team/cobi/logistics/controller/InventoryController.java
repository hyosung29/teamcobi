package com.team.cobi.logistics.controller;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Inventory;
import com.team.cobi.logistics.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("")
    public void createInventory(@RequestBody InboundUpdateRequest request) {
        inventoryService.createInventory(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<InventoryListResponse>> getInventoryList(SearchInventoryList search, Pageable pageable) {
        return ResponseEntity.ok().body(inventoryService.getInventoryPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(inventoryService.getInventoryDetail(id));
    }

    @PutMapping("/{id}")
    public void updateInventory(@PathVariable("id") String id, @RequestBody InboundUpdateRequest request) {
        inventoryService.updateInventory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable("id") String id) {
        inventoryService.deleteInventory(id);
    }

    @GetMapping("/names")
    public List<InventoryCodeResponse> getInventoryNames() {
        return inventoryService.getInventoryNames();
    }
}
