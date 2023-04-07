package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.request.SectionRequest;

import java.util.List;

public interface SectionService {
    Section saveSection(SectionRequest sectionRequest);
    List<Section> getAll();
    Section getSectionById(String id);
    Section update(String id, SectionRequest sectionRequest);
    void deleteById(String id);
    void resetExpiration(String id);
}
