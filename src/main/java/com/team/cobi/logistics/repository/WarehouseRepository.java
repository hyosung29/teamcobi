package com.team.cobi.logistics.repository;

import com.team.cobi.logistics.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String> {

    Page<Warehouse> findByDeleteFlagFalse(Pageable pageable);

    List<Warehouse> findByDeleteFlagFalseAndStatus(String status);

    Optional<Warehouse> findByDeleteFlagFalseAndId(String warehouseId);

}
