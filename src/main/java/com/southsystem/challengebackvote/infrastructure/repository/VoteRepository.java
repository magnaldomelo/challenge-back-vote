package com.southsystem.challengebackvote.infrastructure.repository;

import com.southsystem.challengebackvote.domain.model.internal.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findBySectionId(String sectionId);
}
