package com.southsystem.challengebackvote.domain.model.internal.response;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@With
@NoArgsConstructor
public class AgendaResponse implements Serializable {

    private static final long serialVersionUID = -2919817755749443454L;

    private String id;
    private String name;
}
