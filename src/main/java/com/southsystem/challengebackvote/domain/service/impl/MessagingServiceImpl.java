package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import com.southsystem.challengebackvote.domain.processor.VoteResultProcessor;
import com.southsystem.challengebackvote.domain.service.MessagingService;
import com.southsystem.challengebackvote.domain.service.VoteResultService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private VoteResultProcessor voteResultProcessor;

    @Autowired
    private VoteResultService voteResultService;

    @Override
    public void send(MessagingResult messagingResult) {
        voteResultProcessor.send(String.format("Agenda '%s' closed! Votes: [Yes= %d] ~ [No= %d]",
                messagingResult.getAgenda().getName(),
                messagingResult.getVoteResult().getYes(),
                messagingResult.getVoteResult().getNo()
        ));
    }
}
