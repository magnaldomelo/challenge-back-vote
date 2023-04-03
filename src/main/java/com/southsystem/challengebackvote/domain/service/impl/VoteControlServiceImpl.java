package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.internal.VoteControl;
import com.southsystem.challengebackvote.domain.model.internal.dto.VoteControlDto;
import com.southsystem.challengebackvote.domain.service.VoteControlService;
import com.southsystem.challengebackvote.infrastructure.repository.VoteControlRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class VoteControlServiceImpl implements VoteControlService {

    @Autowired
    private VoteControlRepository voteControlRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VoteControl saveVoteControl(VoteControlDto voteControlDto) {
        var voteControl = this.voteControlRepository.save(modelMapper.map(voteControlDto, VoteControl.class));
        return voteControl;
    }

    @Override
    public Boolean checkExitsVoteBySection(VoteControlDto voteControlDto) {
        var voteControl = this.voteControlRepository.findByCpfAndSectionId(voteControlDto.getCpf(),
                voteControlDto.getSectionId());

        return Objects.nonNull(voteControl);
    }
}
