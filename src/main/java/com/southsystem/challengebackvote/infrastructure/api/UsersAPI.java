package com.southsystem.challengebackvote.infrastructure.api;

import com.southsystem.challengebackvote.domain.model.external.UsersApiResponse;
import com.southsystem.challengebackvote.infrastructure.api.pool.UsersPoolConfig;
import com.southsystem.challengebackvote.infrastructure.enums.IntegrationTypeEnum;
import com.southsystem.challengebackvote.infrastructure.exception.UsersIntegrationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.southsystem.challengebackvote.infrastructure.util.ExceptionUtils.getHttpStatus;
import static com.southsystem.challengebackvote.infrastructure.util.ExceptionUtils.handleExceptionMessage;

@FeignClient(value = "usersservice-api", url = "${application.usersservice.url}", configuration = {
        UsersAPI.UserDecoder.class, UsersPoolConfig.class
})
public interface UsersAPI {

    @GetMapping("/users/{cpf}")
    UsersApiResponse validCpf(@PathVariable("cpf") String cpf);

    class UserDecoder implements ErrorDecoder {

        @Override
        public UsersIntegrationException decode(String methodKey, Response response) {
            final HttpStatus statusCode = getHttpStatus(response);
            final String message = handleExceptionMessage(IntegrationTypeEnum.USERS_SERVICE, methodKey, statusCode, response);

            throw new UsersIntegrationException(message);
        }
    }
}
