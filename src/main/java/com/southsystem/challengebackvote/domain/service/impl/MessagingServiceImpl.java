package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.MessagingResult;
import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.VoteResult;
import com.southsystem.challengebackvote.domain.model.internal.dto.MessagingResultDto;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.processor.MessageResultProcessor;
import com.southsystem.challengebackvote.domain.service.MessagingService;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.repository.MessagingResultRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private MessageResultProcessor messageResultProcessor;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessagingResultRepository messagingResultRepository;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private VoteService voteService;

    @Override
    public MessagingResult save(MessagingResultDto messagingResultDto) {
        return this.messagingResultRepository.insert(modelMapper.map(messagingResultDto, MessagingResult.class));
    }

    @Override
    public void send(MessagingResult messagingResult) {
        messageResultProcessor.send(messagingResult);
    }

    @Override
    public MessagingResult getMessagingResult(String sectionId) {
        var section = this.sectionService.getSectionById(sectionId);

        if(section.getCloused()){
            throw new BusinessException("Section still open");
        }

        return getResult(section);
    }

    @Override
    public MessagingResult getMessagingResult(String sectionId, Boolean closed) {
        if(closed){
            throw new BusinessException("Section still open");
        }

        var section = this.sectionService.getSectionById(sectionId);

        return getResult(section);
    }

    private MessagingResult getResult(Section section){
        var votes = this.voteService.findBySectionId(section.getId());

        var voteResult = VoteResult.builder()
                .yes(votes.stream().filter(vote -> vote.getAnswer().equals(Answer.SIM)).count())
                .no(votes.stream().filter(vote -> vote.getAnswer().equals(Answer.NAO)).count())
                .build();

        var messagingResult = MessagingResult.builder()
                .voteResult(voteResult)
                .agenda(section.getAgenda())
                .build();

        return messagingResult;
    }
}
