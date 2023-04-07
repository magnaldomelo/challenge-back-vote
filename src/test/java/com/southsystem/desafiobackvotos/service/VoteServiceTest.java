package com.southsystem.desafiobackvotos.service;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.Vote;
import com.southsystem.challengebackvote.domain.model.internal.dto.VoteControlDto;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.model.internal.request.VoteRequest;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.UsersService;
import com.southsystem.challengebackvote.domain.service.VoteControlService;
import com.southsystem.challengebackvote.domain.service.impl.VoteServiceImpl;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import com.southsystem.challengebackvote.infrastructure.repository.VoteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VoteServiceTest {

    @InjectMocks
    private VoteServiceImpl voteServiceImpl;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteControlService voteControlService;

    @Mock
    private SectionService sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private UsersService usersService;

    private static final String CPF = "27998034076";
    private static final String AGENDA_NAME = "Teste1";
    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";
    private static final String SECTION_ID = "642d87e1138a287f9833ecc6";
    private static final Boolean CLOUSED_FALSE = false;
    private static final String ANSWER_SIM = "SIM";
    private static final Integer MINUTES_TO_FINISH = 5;
    private static final Instant EXPIRATION = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
    private static Agenda agendaDataFake;
    private static Section sectionDataFake;
    private static Vote voteDataFake;
    private static VoteControlDto voteControlDto;
    private static VoteRequest voteRequestDataFake;

    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().id(AGENDA_ID).name(AGENDA_NAME).build();
        sectionDataFake = Section.builder().id(SECTION_ID).cloused(CLOUSED_FALSE).minutesToFinish(MINUTES_TO_FINISH)
                .agenda(agendaDataFake).expiration(EXPIRATION).build();
        voteControlDto = VoteControlDto.builder().cpf(CPF).sectionId(SECTION_ID).build();
        voteDataFake = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        voteRequestDataFake = VoteRequest.builder().sectionId(SECTION_ID).cpf(CPF).answer(ANSWER_SIM).build();
    }

    @Test
    public void shouldsaveVoteReturnSuccess(){
        Instant instantExpiration = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")))
                .plusSeconds(120);

        Mockito.when(voteControlService.checkExitsVoteBySection(voteControlDto)).thenReturn(false);
        Mockito.doNothing().when(usersService).validCpf(CPF);

        sectionDataFake.setExpiration(instantExpiration);
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));

        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteRepository.insert(voteDataFake)).thenReturn(voteDataFake);
        var vote = voteServiceImpl.saveVote(voteRequestDataFake);

        Mockito.verify(voteControlService).saveVoteControl(voteControlDto);
        assertEquals(Answer.valueOf(ANSWER_SIM), vote.getAnswer());
    }

    @Test
    public void shouldsaveVoteReturnBusinessExceptionWithAlreadyVoted(){
        Instant instantExpiration = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")))
                .plusSeconds(120);

        Mockito.when(voteControlService.checkExitsVoteBySection(voteControlDto)).thenReturn(true);
        Mockito.doNothing().when(usersService).validCpf(CPF);

        sectionDataFake.setExpiration(instantExpiration);
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));

        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteRepository.insert(voteDataFake)).thenReturn(voteDataFake);

        Throwable throwable = assertThrows (BusinessException.class, () -> {
            var vote = voteServiceImpl.saveVote(voteRequestDataFake);
        });

        assertEquals("Associated with CPF '" + CPF + "' already voted.", throwable.getMessage());
    }

    @Test
    public void shouldsaveVoteReturnBusinessExceptionExpirationDate(){
        Instant instantExpiration = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")))
                .plusSeconds(-120);

        Mockito.when(voteControlService.checkExitsVoteBySection(voteControlDto)).thenReturn(false);
        Mockito.doNothing().when(usersService).validCpf(CPF);

        sectionDataFake.setExpiration(instantExpiration);
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));

        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteRepository.insert(voteDataFake)).thenReturn(voteDataFake);

        Throwable throwable = assertThrows (BusinessException.class, () -> {
            var vote = voteServiceImpl.saveVote(voteRequestDataFake);
        });

        assertEquals("Voting already expired.", throwable.getMessage());
    }

    @Test
    public void shouldsaveVoteReturnBusinessExceptionEligibleToVote(){
        Instant instantExpiration = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")))
                .plusSeconds(120);

        Mockito.when(voteControlService.checkExitsVoteBySection(voteControlDto)).thenReturn(false);
        Mockito.doThrow(new BusinessException("CPF '" + CPF + "' is unable to vote."))
                .when(usersService).validCpf(CPF);

        sectionDataFake.setExpiration(instantExpiration);
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));

        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteRepository.insert(voteDataFake)).thenReturn(voteDataFake);

        Throwable throwable = assertThrows (BusinessException.class, () -> {
            var vote = voteServiceImpl.saveVote(voteRequestDataFake);
        });

        assertEquals("CPF '" + CPF + "' is unable to vote.", throwable.getMessage());
    }

    @Test
    public void shouldsaveVoteReturnBusinessExceptionInInsert(){
        Instant instantExpiration = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")))
                .plusSeconds(120);

        Mockito.when(voteControlService.checkExitsVoteBySection(voteControlDto)).thenReturn(false);
        Mockito.doNothing().when(usersService).validCpf(CPF);

        sectionDataFake.setExpiration(instantExpiration);
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));

        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(voteRepository.insert(voteDataFake)).thenReturn(null);

        Throwable throwable = assertThrows (BusinessException.class, () -> {
            var vote = voteServiceImpl.saveVote(voteRequestDataFake);
        });

        assertEquals("Error trying to save vote", throwable.getMessage());
    }
}
