package com.team.cobi.logistics.service;

import com.team.cobi.logistics.dto.*;
import com.team.cobi.logistics.entity.Section;
import com.team.cobi.logistics.repository.SectionQueryRepository;
import com.team.cobi.logistics.repository.SectionRepository;
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
public class SectionService {
    private final SectionRepository sectionRepository;

    private final SectionQueryRepository sectionQueryRepository;

    @Transactional
    public void createSection(SectionCreateRequest request) {
        sectionRepository.save(new Section(request));
    }

    @Transactional(readOnly = true)
    public Page<SectionListResponse> getSectionPage(SearchSectionList search, Pageable pageable) {
        return sectionQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Section getSectionDetail(String sectionId) {
        return getSection(sectionId);
    }

    @Transactional
    public void updateSection(String sectionId, SectionUpdateRequest request) {
        Section section = getSection(sectionId);
        section.update(request);
        sectionRepository.save(section);
    }

    @Transactional
    public void deleteSection(String sectionId) {
        Section section = getSection(sectionId);
        section.delete();
       sectionRepository.save(section);
    }

    @Transactional(readOnly = true)
    public Section getSection(String sectionId) {
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if(section == null) throw  new NullException();
        return section;
    }

    @Transactional
    public List<SectionNameResponse> getSectionNames() {
        List<Section> sectionList = sectionRepository.findByDeleteFlagFalse();
        return sectionList.stream().map(SectionNameResponse::new).collect(Collectors.toList());
    }

}
