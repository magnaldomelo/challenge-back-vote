package com.southsystem.challengebackvote.domain.job;

import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.service.MessagingService;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteResultJob {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private MessagingService messagingService;

    @Scheduled(fixedDelay = 60000)
    public void closeSectionAndSendVoteResult() {
        var sectionsNotExpiredAndNotClosed = getSectionExpiredAndNotClosed();

        sectionsNotExpiredAndNotClosed.forEach(
                section -> {
                    var messagingResult = this.messagingService.getMessagingResult(section.getId());

                    if(Objects.nonNull(messagingResult)){
                        this.messagingService.send(messagingResult);
                        section.setCloused(true);
                        sectionRepository.save(section);
                    }
                }
        );
    }

    private List<Section> getSectionExpiredAndNotClosed(){
        var instant = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));

        var listSection = this.sectionRepository.findAll()
                .stream().filter(section -> instant.isAfter(section.getExpiration()) &&
                        !section.getCloused()).collect(Collectors.toList());

        return listSection;
    }
}
