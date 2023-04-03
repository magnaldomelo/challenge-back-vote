package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.internal.VoteControl;
import com.southsystem.challengebackvote.domain.model.internal.dto.VoteControlDto;

public interface VoteControlService {
    VoteControl saveVoteControl(VoteControlDto voteControlDto);
    Boolean checkExitsVoteBySection(VoteControlDto voteControlDto);
}
