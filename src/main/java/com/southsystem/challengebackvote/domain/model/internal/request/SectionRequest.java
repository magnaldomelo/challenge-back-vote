package com.southsystem.challengebackvote.domain.model.internal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionRequest implements Serializable {

    private static final long serialVersionUID = -6380447127856912623L;

    private static final String VALID_AGENDA_ID_MESSAGE = "The agendId field is mandatory: ";

    @NotNull(message = VALID_AGENDA_ID_MESSAGE + "Name can not be null")
    @NotEmpty(message = VALID_AGENDA_ID_MESSAGE + "Name can not be empty")
    @NotBlank(message = VALID_AGENDA_ID_MESSAGE+ "Name can not be blank")
    private String agendaId;
    private Integer minutesToFinish;
    private Boolean cloused;

}
