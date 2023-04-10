package com.southsystem.challengebackvote.infrastructure.repository;

import com.southsystem.challengebackvote.domain.model.internal.Section;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends MongoRepository<Section, String> {

    @Query("{'agenda._id': ?0, 'cloused': ?1}")
    List<Section> findByAgendaAndClosed(ObjectId objectId, Boolean cloused);

    @Query("{'agenda._id': ?0}")
    List<Section> findByAgendaId(ObjectId objectId);
}
