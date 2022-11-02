package com.team.cobi.logistics.repository;

import com.team.cobi.logistics.entity.Outbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundRepository extends JpaRepository<Outbound, String> {

    Page<Outbound> findByDeleteFlagFalse(Pageable pageable);

}
