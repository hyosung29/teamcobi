package com.team.cobi.logistics.service;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Inventory;
import com.team.cobi.logistics.repository.InventoryQueryRepository;
import com.team.cobi.logistics.repository.InventoryRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    @Override
    public String toString() {
        return "InventoryService{" +
                "inventoryRepository=" + inventoryRepository +
                '}';
    }

    private final InventoryRepository inventoryRepository;

    private final InventoryQueryRepository inventoryQueryRepository;

    @Transactional
    public void createInventory(InboundUpdateRequest request) {
        String warehouseId = request.getWarehouseId();
        String sectionId = request.getSectionId();
        String productId = request.getProductId();
        int quantity = request.getProductQuantity();

        Inventory inventory = inventoryRepository.findByWarehouseIdAndSectionIdAndProductId(warehouseId, sectionId, productId)
                .orElse(new Inventory());
        inventory.update(quantity, productId, warehouseId, sectionId);
        inventoryRepository.save(inventory);

    }

    @Transactional
    public void outboundInventory(OutboundUpdateRequest request) {
        String warehouseId = request.getWarehouseId();
        String sectionId = request.getSectionId();
        String productId = request.getProductId();
        int quantity = request.getProductQuantity();

        Inventory inventory = inventoryRepository.findByWarehouseIdAndSectionIdAndProductId(warehouseId, sectionId, productId)
                .orElse(new Inventory());
        inventory.outboundInventory(quantity);
        inventoryRepository.save(inventory);

    }

    @Transactional(readOnly = true)
    public Page<InventoryListResponse> getInventoryPage(SearchInventoryList search, Pageable pageable) {
        return inventoryQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Inventory getInventoryDetail(String id) {
        return getInventory(id);
    }


    //쓰나안쓰나확인
    @Transactional
    public void updateInventory(String id, InboundUpdateRequest request) {
        Inventory inventory = getInventory(id);
        String warehouseId = request.getWarehouseId();
        String sectionId = request.getSectionId();
        String productId = request.getProductId();
        int quantity = request.getProductQuantity();
        inventory.update(quantity, productId, warehouseId, sectionId);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteInventory(String id) {
        Inventory inventory = getInventory(id);
        inventory.delete();
        inventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public Inventory getInventory(String id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory == null) throw new NullException();
        return inventory;
    }

    @Transactional
    public List<InventoryCodeResponse> getInventoryNames() {
        return inventoryQueryRepository.getNames();
    }

    @Transactional
    public void inventoryDown(OutboundUpdateRequest request) {
        String productId = request.getProductId();
        int quantity = request.getProductQuantity();
        log.info("어휴 " + request.getProductId());
        log.info("어휴2 " + request.getProductQuantity());
        Inventory inventory = inventoryRepository.findByProductId(productId);
        inventory.outboundInventory(quantity);
    }
}