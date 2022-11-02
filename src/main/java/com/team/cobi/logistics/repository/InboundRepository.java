package com.team.cobi.logistics.repository;

import com.team.cobi.logistics.entity.Inbound;
import com.team.cobi.logistics.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InboundRepository extends JpaRepository<Inbound, String> {

    Page<Inbound> findByDeleteFlagFalse(Pageable pageable);



}
