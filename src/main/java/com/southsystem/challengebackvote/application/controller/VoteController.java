package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.internal.request.VoteRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.VoteResponse;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequestMapping("/api/v1/vote")
@AllArgsConstructor
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> saveVote(@RequestBody @Valid VoteRequest voteRequest) throws URISyntaxException {
        var vote = this.voteService.saveVote(voteRequest);

        if(Objects.isNull(vote)){
            throw new BusinessException("Attempt to vote failed for CPF " + voteRequest.getCpf());
        }

        return created(new URI(voteRequest.getSectionId())).body(VoteResponse.builder().success(true).build());
    }
}
