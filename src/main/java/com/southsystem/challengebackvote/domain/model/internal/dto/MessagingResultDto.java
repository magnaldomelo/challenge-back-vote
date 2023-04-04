package com.southsystem.challengebackvote.domain.model.internal.dto;


import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.VoteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagingResultDto implements Serializable {

    private static final long serialVersionUID = -3412893470219183274L;

    private Agenda agenda;
    private VoteResult voteResult;
}
