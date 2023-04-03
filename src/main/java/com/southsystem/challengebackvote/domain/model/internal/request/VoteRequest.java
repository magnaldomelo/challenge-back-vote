package com.southsystem.challengebackvote.domain.model.internal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest implements Serializable {

    private static final long serialVersionUID = 6406672795608843560L;

    private static final String NOT_NULL_MESSAGE = "Name can not be null";
    private static final String NOT_EMPTY_MESSAGE = "Name can not be null";
    private static final String NOT_BLANK_MESSAGE = "Name can not be null";


    @NotNull(message = NOT_NULL_MESSAGE)
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String cpf;

    @NotNull(message = NOT_NULL_MESSAGE)
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String answer;

    @NotNull(message = NOT_NULL_MESSAGE)
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String sectionId;
}
