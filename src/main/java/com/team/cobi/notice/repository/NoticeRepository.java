package com.team.cobi.notice.repository;

import com.team.cobi.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, String> {
//    List<Notice> findByDeleteFlagFalse();

    Page<Notice> findByDeleteFlagFalse(Pageable pageable);
}
