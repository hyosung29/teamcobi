package com.team.cobi.notice.controller;

import com.team.cobi.notice.dto.NoticeCreateRequest;
import com.team.cobi.notice.dto.NoticeListResponse;
import com.team.cobi.notice.dto.NoticeUpdateRequest;
import com.team.cobi.notice.dto.SearchNoticeList;
import com.team.cobi.notice.entity.Notice;
import com.team.cobi.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // json 형식 = key : value
@RequiredArgsConstructor
@RequestMapping("/api/notice") //localhost:/api/notice
// api 적는 이유 : 스웨거 구분
// 스웨거 : 화면(web) 이 없을 때 백엔드 처리를 확인해볼 수 있는 기능
public class NoticeController {

    // Autowired 를 쓰지 않는 이유는 순환되서 코드가 꼬일 수 있음
    private final NoticeService noticeService;


    @PostMapping("")
    public void createNotice(@RequestBody NoticeCreateRequest request) {
        noticeService.createNotice(request);
    }


    @GetMapping("")
    public ResponseEntity<Page<NoticeListResponse>> getNoticeList(SearchNoticeList search, Pageable pageable) {
        return ResponseEntity.ok().body(noticeService.getNoticePage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(noticeService.getNoticeDetail(id));
    }

    @PutMapping("/{id}")
    public void updateNotice(@PathVariable("id") String id, @RequestBody NoticeUpdateRequest request) {
        noticeService.updateNotice(id, request);
    }

    @DeleteMapping("/{id}")
    // ur
    public void deleteNotice(@PathVariable("id") String id) {
        noticeService.deleteNotice(id);
    }

}
