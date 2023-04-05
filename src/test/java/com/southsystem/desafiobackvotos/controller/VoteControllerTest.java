package com.southsystem.desafiobackvotos.controller;

import com.southsystem.challengebackvote.application.controller.VoteController;
import com.southsystem.challengebackvote.domain.model.internal.Vote;
import com.southsystem.challengebackvote.domain.model.internal.dto.VoteControlDto;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.model.internal.request.VoteRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.VoteResponse;
import com.southsystem.challengebackvote.domain.service.VoteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VoteControllerTest {

    @InjectMocks
    private VoteController voteController;

    @Mock
    private VoteService voteService;

    private static final String SECTION_ID = "642c2478e4c90845bca545c7";
    private static final String CPF = "97515893044";

    private static final Boolean SUCCESS = true;
    private static final Boolean FAILURE = false;

    private static Vote voteDataFake;
    private static VoteControlDto voteControlDtoDataFake;
    private static VoteResponse voteResponseDataFake;
    private static VoteRequest voteRequestDataFake;

    @BeforeAll
    public static void setup(){
        voteDataFake = Vote.builder().answer(Answer.SIM).sectionId(SECTION_ID).build();
        voteControlDtoDataFake = VoteControlDto.builder().cpf(CPF).sectionId(SECTION_ID).build();
        voteResponseDataFake = VoteResponse.builder().success(SUCCESS).build();
        voteRequestDataFake = VoteRequest.builder().cpf(CPF).sectionId(SECTION_ID).build();
    }

    @Test
    public void shouldCreateVote() throws URISyntaxException {
        Mockito.when(voteService.saveVote(voteRequestDataFake)).thenReturn(voteDataFake);
        ResponseEntity<?> response =  voteController.saveVote(voteRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(true, ((VoteResponse) response.getBody()).getSuccess());
        assertEquals(null, ((VoteResponse) response.getBody()).getFailure());
    }
}
