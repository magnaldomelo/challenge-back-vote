package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import com.southsystem.challengebackvote.domain.model.internal.VoteResult;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.VoteResultService;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteResultServiceImpl implements VoteResultService {

    @Autowired
    private VoteService voteService;

    @Autowired
    private SectionService sectionService;

    @Override
    public MessagingResult getMessagingResult(String sectionId) {
        var section = this.sectionService.getSectionById(sectionId);

        if(!section.getCloused()){
            throw new BusinessException("Section still open");
        }

        var votes = this.voteService.findBySectionId(sectionId);

        var voteResult = VoteResult.builder()
                .yes(votes.stream().filter(vote -> vote.getAnswer().equals(Answer.SIM)).count())
                .no(votes.stream().filter(vote -> vote.getAnswer().equals(Answer.SIM)).count())
                .build();

        var messagingResult = MessagingResult.builder()
                .voteResult(voteResult)
                .agenda(section.getAgenda())
                .build();

        return messagingResult;
    }
}
