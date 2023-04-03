package com.southsystem.challengebackvote.domain.model.internal;

import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagingResult implements Serializable {

    private static final long serialVersionUID = 1334493837508466981L;

    private Agenda agenda;
    private VoteResult voteResult;
}
