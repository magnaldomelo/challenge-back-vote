package com.southsystem.challengebackvote.domain.model.internal;

import com.southsystem.challengebackvote.domain.model.internal.enums.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Vote implements Serializable {

    private static final long serialVersionUID = -366535154176877785L;

    private Answer answer;
    private String sectionId;
}
