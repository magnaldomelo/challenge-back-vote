package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import com.southsystem.challengebackvote.infrastructure.exception.BadRequestException;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.exception.EntityNotFoundException;
import com.southsystem.challengebackvote.infrastructure.repository.AgendaRepository;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Agenda saveAgenda(AgendaRequest agendaRequest) {
        var agenda = this.agendaRepository.findByName(agendaRequest.getName());

        if(!agenda.isEmpty()){
            throw new BusinessException("Reported record '" + agendaRequest.getName() + "' already exists.");
        }

        return this.agendaRepository.insert(modelMapper.map(agendaRequest, Agenda.class));
    }

    @Override
    public List<Agenda> getAll() {
        return this.agendaRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Agenda getAgendaById(String id) {
        var agenda = this.agendaRepository.findById(id);

        if(agenda.isEmpty()){
            throw new EntityNotFoundException("Agenda for Id " + id + " not Found.");
        }

        return agenda.get();
    }

    @Override
    public Agenda update(String id, AgendaRequest agendaRequest) {
        var agenda = this.getAgendaById(id);
        agenda.setName(agendaRequest.getName());
        var agendaUpdated = this.agendaRepository.save(agenda);

        if(Objects.nonNull(agendaUpdated)){
            var sectionsByAgendaId = this.sectionRepository.findByAgendaId(new ObjectId(id));

            sectionsByAgendaId.forEach(section -> {
                section.getAgenda().setName(agendaUpdated.getName());
                this.sectionRepository.save(section);
            });
        }

        return agendaUpdated;
    }

    @Override
    public void deleteById(String id) {
        var agenda = this.getAgendaById(id);
        this.agendaRepository.deleteById(agenda.getId());
    }
}
