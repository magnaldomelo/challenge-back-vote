package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;

public interface VoteResultService {
    MessagingResult getMessagingResult(String sectionId);
}
