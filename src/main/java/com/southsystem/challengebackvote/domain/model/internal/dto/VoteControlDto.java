package com.southsystem.challengebackvote.domain.model.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteControlDto implements Serializable {

    private static final long serialVersionUID = -3264446280033718413L;

    private String cpf;
    private String sectionId;
}
