package com.team.cobi.notice.service;

import com.team.cobi.notice.dto.NoticeCreateRequest;
import com.team.cobi.notice.dto.NoticeListResponse;
import com.team.cobi.notice.dto.NoticeUpdateRequest;
import com.team.cobi.notice.dto.SearchNoticeList;
import com.team.cobi.notice.entity.Notice;
import com.team.cobi.notice.repository.NoticeQueryRepository;
import com.team.cobi.notice.repository.NoticeRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    @Transactional // 모두 성공하거나, 모두 실패하거나 처리해주기
    public void createNotice(NoticeCreateRequest request) {
        noticeRepository.save(new Notice(request)); // insert 후에는 save 를 해줘야 db에 값이 저장된다.
    }

//    @Transactional(readOnly = true)
//    public List<Notice> getNoticeList() {
//        return noticeRepository.findByDeleteFlagFalse();
//    }

//    @Transactional(readOnly = true)
//    public Page<Notice> getNoticePage(Pageable pageable) {
//        // Paging v1
//        return noticeRepository.findByDeleteFlagFalse(pageable);
//    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponse> getNoticePage(SearchNoticeList search, Pageable pageable) {
        // Paging v2
        return noticeQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Notice getNoticeDetail(String id) {
        return getNotice(id);
    }

    @Transactional
    public void updateNotice(String id, NoticeUpdateRequest request) {
        Notice notice = getNotice(id);
        notice.update(request);
        noticeRepository.save(notice);
    }

    @Transactional
    public void deleteNotice(String id) {

        Notice notice = getNotice(id); // Notice(Entity) = DB, getNotice(id) 동일한 id값 을 가져와라
        notice.delete();
        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    public Notice getNotice(String id) {
        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice == null) throw new NullException();
        return notice;
    }
}
