package com.southsystem.challengebackvote.domain.model.internal.response;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponse implements Serializable {

    private static final long serialVersionUID = 7622695466362062456L;

    private String id;
    private Agenda agenda;
    private Integer minutesToFinish;
    private Instant expiration;
    private Boolean cloused;
}
