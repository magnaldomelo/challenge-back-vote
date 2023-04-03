package com.southsystem.challengebackvote.domain.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Agenda implements Serializable {

    private static final long serialVersionUID = -8673141821239917606L;

    @Id
    private String id;
    private String name;
}
