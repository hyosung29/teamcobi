package com.team.cobi.logistics.repository;

import com.team.cobi.logistics.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {

    Page<Section> findByDeleteFlagFalse(Pageable pageable);

    List<Section> findByDeleteFlagFalse();

}