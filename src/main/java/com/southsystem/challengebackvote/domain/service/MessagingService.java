package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import com.southsystem.challengebackvote.domain.model.internal.dto.MessagingResultDto;

public interface MessagingService {
    MessagingResult save(MessagingResultDto messagingResultDto);
    void send(MessagingResult messagingResult);
    MessagingResult getMessagingResult(String sectionId);
    MessagingResult getMessagingResult(String sectionId, Boolean closed);
}
