package com.southsystem.challengebackvote.domain.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersApiResponse implements Serializable {
    private static final long serialVersionUID = 1711491874097959324L;

    private String value;
}
