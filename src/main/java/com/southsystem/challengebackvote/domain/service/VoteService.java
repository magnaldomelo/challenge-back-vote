package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.Vote;
import com.southsystem.challengebackvote.domain.model.internal.request.VoteRequest;

import java.util.List;

public interface VoteService {
    Vote saveVote(VoteRequest voteRequest);
    List<Vote> findBySectionId(String sectionId);
}
