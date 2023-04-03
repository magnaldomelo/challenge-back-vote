package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;

import java.util.List;

public interface AgendaService {
    Agenda saveAgenda(AgendaRequest agendaRequest);

    List<Agenda> getAll();

    Agenda getAgendaById(String id);

    Agenda update(String id, AgendaRequest agendaRequest);

    void deleteById(String id);
}
