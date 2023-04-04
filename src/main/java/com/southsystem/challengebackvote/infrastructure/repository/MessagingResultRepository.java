package com.southsystem.challengebackvote.infrastructure.repository;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagingResultRepository extends MongoRepository<MessagingResult, String> {
}
