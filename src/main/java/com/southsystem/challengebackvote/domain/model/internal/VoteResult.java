package com.southsystem.challengebackvote.domain.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResult implements Serializable {

    private static final long serialVersionUID = -2477205039481947050L;

    private Long yes;
    private Long no;
}
