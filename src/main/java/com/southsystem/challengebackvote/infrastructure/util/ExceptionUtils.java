package com.southsystem.challengebackvote.infrastructure.util;

import com.southsystem.challengebackvote.infrastructure.enums.IntegrationTypeEnum;
import feign.Response;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import com.amazonaws.util.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@UtilityClass
public class ExceptionUtils {

    public static HttpStatus getHttpStatus(Response response) {

        if (Objects.isNull(response)) {
            log.warn("Response null!!!");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return HttpStatus.valueOf(response.status());
    }

    public static String handleExceptionMessage(IntegrationTypeEnum integrationType, String methodKey, HttpStatus statusCode, Response response) {
        StringBuilder bd = new StringBuilder();
        bd.append(integrationType).append("_FAIL")
                .append(" - StatusCode("+statusCode+")")
                .append(" - responseBody: " + getResponseBody(response))
                .append(" - Reason: " + getReason(response));
        return bd.toString();
    }

    private static String getResponseBody(Response response) {

        try(InputStream in = response.body().asInputStream()) {
            return IOUtils.toString(in);
        } catch (IOException e) {
            return "unreaded body response: " + e.getMessage();
        }
    }

    private static String getReason(Response response) {

        try {
            return response.reason();
        } catch (Exception e) {
            return "unreaded reason: " + e.getMessage();
        }
    }
}
