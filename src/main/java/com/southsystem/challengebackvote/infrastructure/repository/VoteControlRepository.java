package com.southsystem.challengebackvote.infrastructure.repository;

import com.southsystem.challengebackvote.domain.model.internal.VoteControl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteControlRepository extends MongoRepository<VoteControl, String> {
    VoteControl findByCpfAndSectionId(String cpf, String sectionId);
}
