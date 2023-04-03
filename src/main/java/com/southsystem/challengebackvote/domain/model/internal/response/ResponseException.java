package com.southsystem.challengebackvote.domain.model.internal.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.southsystem.challengebackvote.domain.model.internal.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseException {

    private ResultCodeEnum code;
    private String message;
}
