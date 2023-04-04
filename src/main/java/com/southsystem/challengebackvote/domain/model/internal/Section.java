package com.southsystem.challengebackvote.domain.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Section {

    @Id
    private String id;
    private Agenda agenda;
    private Integer minutesToFinish;
    private Instant expiration;
    private Boolean cloused;
}
