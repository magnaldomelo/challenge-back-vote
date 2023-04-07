package com.southsystem.desafiobackvotos.service;

import com.southsystem.challengebackvote.domain.model.internal.*;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.domain.service.impl.MessagingServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MessagingServiceTest {

    @InjectMocks
    private MessagingServiceImpl messagingService;

    @Mock
    private SectionService sectionService;

    @Mock
    private VoteService voteService;

    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";
    private static final String AGENDA_NAME = "Teste1";
    private static final String SECTION_ID = "642d87e1138a287f9833ecc6";
    private static final Integer MINUTES_TO_FINISH = 5;
    private static final Instant EXPIRATION = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
    private static final Boolean CLOUSED_FALSE = false;
    private static Agenda agendaDataFake;
    private static Section sectionDataFake;
    private static Vote voteDataFake;

    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().id(AGENDA_ID).name(AGENDA_NAME).build();
        sectionDataFake = Section.builder().id(SECTION_ID).agenda(agendaDataFake).cloused(CLOUSED_FALSE)
                .minutesToFinish(MINUTES_TO_FINISH)
                .agenda(agendaDataFake).expiration(EXPIRATION).build();
        voteDataFake = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
    }

    @Test
    public void shouldgetMessagingResultSucess(){
        List<Vote> votes = getVoteResultDataFake();
        var voteResult = VoteResult.builder().yes(6L).no(4L).build();
        var messagingResult = MessagingResult.builder().voteResult(voteResult)
                .agenda(sectionDataFake.getAgenda()).build();

        Mockito.when(this.sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteService.findBySectionId(SECTION_ID)).thenReturn(votes);

        var resultAtenda = messagingService.getMessagingResult(SECTION_ID);

        assertEquals(messagingResult, resultAtenda);
    }

    private List<Vote> getVoteResultDataFake(){
        var vote1 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote2 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote3 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote4 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote5 = Vote.builder().answer(Answer.NAO).sectionId(SECTION_ID).build();
        var vote6 = Vote.builder().answer(Answer.NAO).sectionId(SECTION_ID).build();
        var vote7 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote8 = Vote.builder().answer(Answer.NAO).sectionId(SECTION_ID).build();
        var vote9 = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        var vote10 = Vote.builder().answer(Answer.NAO).sectionId(SECTION_ID).build();

        return new ArrayList<>(List.of(vote1, vote2, vote3, vote4, vote5, vote6, vote7,
                vote8, vote9, vote10));
    }
}
