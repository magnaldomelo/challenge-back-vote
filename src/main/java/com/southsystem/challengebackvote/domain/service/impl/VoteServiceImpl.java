package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.external.UsersResultEnum;
import com.southsystem.challengebackvote.domain.model.internal.Vote;
import com.southsystem.challengebackvote.domain.model.internal.dto.VoteControlDto;
import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import com.southsystem.challengebackvote.domain.model.internal.request.VoteRequest;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.domain.service.UsersService;
import com.southsystem.challengebackvote.domain.service.VoteControlService;
import com.southsystem.challengebackvote.domain.service.VoteService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteControlService voteControlService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private UsersService usersService;

    private final static String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    @Override
    public Vote saveVote(VoteRequest voteRequest) {
        var answer = answerFromVoteRequest(voteRequest.getAnswer());

        checkExitsVoteBySection(voteRequest.getCpf(), voteRequest.getSectionId());
        checkIsExpiredDate(voteRequest.getSectionId());
        eligibleToVote(voteRequest.getCpf());


        var vote = Vote.builder().sectionId(voteRequest.getSectionId()).answer(answer).build();
        var voteSaved = this.voteRepository.insert(vote);

        if(Objects.nonNull(voteSaved)){
            this.voteControlService.saveVoteControl(VoteControlDto.builder()
                    .cpf(voteRequest.getCpf()).sectionId(voteRequest.getSectionId()).build());
        }

        return voteSaved;
    }

    @Override
    public List<Vote> findBySectionId(String sectionId) {
        var votes = this.voteRepository.findBySectionId(sectionId);

        return votes;
    }

    private void checkExitsVoteBySection(String cpf, String sectionId){
        var voteControlDto = VoteControlDto.builder().cpf(cpf)
                .sectionId(sectionId).build();
        var checkExitsVoteBySection = this.voteControlService.checkExitsVoteBySection(voteControlDto);

        if (checkExitsVoteBySection){
            throw new BusinessException("Associated with CPF '" + cpf + "' already voted.");
        }
    }

    private void checkIsExpiredDate(String sectionId){
        var section = this.sectionService.getSectionById(sectionId);
        var instant = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));

        if(section.getExpiration().isBefore(instant)){
            throw new BusinessException("Voting already expired.");
        }
    }

    private void eligibleToVote(String cpf){
        try{
            var usersResultEnum = usersService.validCpf(cpf);

            if(ABLE_TO_VOTE.equals(UsersResultEnum.valueOf(usersResultEnum.getValue().toString()))){
                throw new BusinessException("CPF '" + cpf + "' is unable to vote.");
            }
        }catch (Exception e){
            throw new BusinessException("CPF '" + cpf + "' is unable to vote.");
        }
    }

    private Answer answerFromVoteRequest(String answer){
        try{
            return Answer.valueOf(answer.toUpperCase());
        }catch (Exception e){
            throw new BusinessException("Invalid voting option");
        }
    }


    //TODO implementar controle de voto; implementar salvar voto; implementar scheduler; implementar fila
}
