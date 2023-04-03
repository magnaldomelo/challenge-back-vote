package com.southsystem.challengebackvote.domain.model.internal.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaRequest implements Serializable {

    private static final long serialVersionUID = 5990272893243317063L;

    private static final String NOT_NULL_MESSAGE = "Name can not be null";
    private static final String NOT_EMPTY_MESSAGE = "Name can not be null";
    private static final String NOT_BLANK_MESSAGE = "Name can not be null";

    @NotNull(message = NOT_NULL_MESSAGE)
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Length(min = 3, message = "Minimum of 3 characters")
    private String name;
}
