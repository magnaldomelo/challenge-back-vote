package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.request.SectionRequest;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.exception.EntityNotFoundException;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private AgendaService agendaService;

    @Value("${generalconfig.default-minutes-to-finish:1}")
    private Integer defaultMinutesToFinish;

    @Override
    public Section saveSection(SectionRequest sectionRequest) {
        var agenda = agendaService.getAgendaById(sectionRequest.getAgendaId());
        var minutesToFinish = Objects.isNull(sectionRequest.getMinutesToFinish()) ?
                defaultMinutesToFinish : sectionRequest.getMinutesToFinish();

        var sectionSearch = sectionRepository
                .findByAgendaAndClosed(new ObjectId(agenda.getId()), false);

        if(!sectionSearch.isEmpty()){
            throw new BusinessException("Section already exists for Agenda '" + agenda.getName());
        }

        var section = Section.builder()
                .agenda(agenda)
                .minutesToFinish(minutesToFinish)
                .cloused(false)
                .expiration(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))).plusSeconds(minutesToFinish * 60))
                .build();

        return this.sectionRepository.insert(section);
    }

    @Override
    public List<Section> getAll() {
        return this.sectionRepository.findAll().stream().collect(Collectors.toList());
    }

    /*@Cacheable(
            value = "section",
            unless = "#result == null",
            key = "'challenge-back-vote.SectionService.getSectionById' + '-' + #id")*/
    @Override
    public Section getSectionById(String id) {
        var section = this.sectionRepository.findById(id);

        if(section.isEmpty()){
            throw new EntityNotFoundException("Section for Id " + id + " not Found.");
        }

        return section.get();
    }

    @Override
    public Section update(String id, SectionRequest sectionRequest) {
        var section = getSectionById(id);
        var agenda = this.agendaService.getAgendaById(sectionRequest.getAgendaId());
        var minutesToFinish = Objects.isNull(sectionRequest.getMinutesToFinish()) ?
                defaultMinutesToFinish : sectionRequest.getMinutesToFinish();

        section.setMinutesToFinish(minutesToFinish);
        section.setExpiration(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))).plusSeconds(minutesToFinish * 60));

        if(Objects.nonNull(agenda)){
            section.setAgenda(agenda);
        }

        if(Objects.nonNull(sectionRequest.getCloused())){
            section.setCloused(sectionRequest.getCloused());
        }

        return this.sectionRepository.save(section);
    }

    @Override
    public void deleteById(String id) {
        var section = getSectionById(id);
        this.sectionRepository.deleteById(section.getId());
    }
}
