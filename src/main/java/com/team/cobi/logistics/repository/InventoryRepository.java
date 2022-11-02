package com.team.cobi.logistics.repository;

import com.team.cobi.logistics.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Page<Inventory> findByDeleteFlagFalse(Pageable pageable);

    Optional<Inventory> findByWarehouseIdAndSectionIdAndProductId(String warehouseId, String sectionId, String productId);

    Inventory findByProductId(String productId);


    //List<Inventory> findByDeleteFlagFalseAndQuantityGreaterThan(int i);

}