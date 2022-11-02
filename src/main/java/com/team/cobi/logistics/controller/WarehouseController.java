package com.team.cobi.logistics.controller;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Warehouse;
import com.team.cobi.logistics.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("")
    public void createWarehouse(@RequestBody WarehouseCreateRequest request) {
        warehouseService.createWarehouse(request);
    }


    @GetMapping("")
    public ResponseEntity<Page<WarehouseListResponse>> getWarehouseList(SearchWarehouseList search, Pageable pageable) {
        return ResponseEntity.ok().body(warehouseService.getWarehousePage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(warehouseService.getWarehouseDetail(id));
    }

    @PutMapping("/{id}")
    public void updateWarehouse(@PathVariable("id") String id, @RequestBody WarehouseUpdateRequest request) {
        warehouseService.updateWarehouse(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable("id") String id ) { warehouseService.deleteWarehouse(id); }


    @GetMapping("/names")
    public List<WarehouseCodeResponse> getWarehouseName() {
        return warehouseService.getWarehouseName();
    }

    @GetMapping("/names/{id}")
    public ResponseEntity<WarehouseCodeResponse> getWarehouseNameOne(@PathVariable String id) {
        return ResponseEntity.ok().body(warehouseService.checkWarehouse(id));
    }


}
