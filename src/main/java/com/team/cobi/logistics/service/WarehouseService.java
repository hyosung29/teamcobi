package com.team.cobi.logistics.service;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Warehouse;
import com.team.cobi.logistics.repository.WarehouseQueryRepository;
import com.team.cobi.logistics.repository.WarehouseRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseQueryRepository warehouseQueryRepository;

    @Transactional
    public void createWarehouse(WarehouseCreateRequest request) {
        warehouseRepository.save(new Warehouse(request));
    }

    @Transactional
    public Page<WarehouseListResponse> getWarehousePage(SearchWarehouseList search, Pageable pageable) {
        return warehouseQueryRepository.searchPage(search, pageable);
    }

    @Transactional
    public void updateWarehouse(String id, WarehouseUpdateRequest request) {
        Warehouse warehouse = getWarehouse(id);
        warehouse.update(request);
        warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true)
    public Warehouse getWarehouse(String id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElse(null);
        if (warehouse == null) throw new NullException();
        return warehouse;
    }

    @Transactional(readOnly = true)
    public Warehouse getWarehouseDetail(String id) { return getWarehouse(id); }

    @Transactional
    public void deleteWarehouse(String id) {
        Warehouse warehouse = getWarehouse(id);  // Notice(Entity) = DB, getNotice(id) 동일한 id값 을 가져와라
        warehouse.delete();
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public List<WarehouseCodeResponse> getWarehouseName() {
        //return warehouseQueryRepository.getWarehouseName();
        List<Warehouse> warehouseList = warehouseRepository.findByDeleteFlagFalseAndStatus("사용중");
        return warehouseList.stream().map(WarehouseCodeResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public WarehouseCodeResponse checkWarehouse(String id) {
        return warehouseQueryRepository.getWarehouseName(id);
    }


}
