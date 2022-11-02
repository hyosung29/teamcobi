package com.team.cobi.logistics.controller;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Section;
import com.team.cobi.logistics.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/section")
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("")
    public void createSetion(@RequestBody SectionCreateRequest request) {
        sectionService.createSection(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<SectionListResponse>> getSectionList(SearchSectionList search, Pageable pageable) {
        return ResponseEntity.ok().body(sectionService.getSectionPage(search, pageable));
    };

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionDetail(@PathVariable String sectionId) {
        return ResponseEntity.ok().body(sectionService.getSectionDetail(sectionId));
    }

    @PutMapping("/{id}")
    public void updateSection(@PathVariable("id") String sectionId, @RequestBody SectionUpdateRequest request) {
        sectionService.updateSection(sectionId, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable("id") String sectionId) {
        sectionService.deleteSection(sectionId);
    }

    @GetMapping("/names")
    public List<SectionNameResponse> getSectionNames() {
        return sectionService.getSectionNames();
    }
}
