package com.southsystem.challengebackvote.infrastructure.repository;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String> {
    List<Agenda> findByName(String name);
}
