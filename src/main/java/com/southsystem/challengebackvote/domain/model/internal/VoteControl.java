package com.southsystem.challengebackvote.domain.model.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("vote_control")
public class VoteControl implements Serializable {

    private static final long serialVersionUID = 4471858916274318982L;

    private String cpf;
    private String sectionId;
}
