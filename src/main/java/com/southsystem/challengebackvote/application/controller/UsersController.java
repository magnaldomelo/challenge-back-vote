package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.external.UsersApiResponse;
import com.southsystem.challengebackvote.domain.service.UsersService;
import com.southsystem.challengebackvote.infrastructure.util.CustomStopWatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.southsystem.challengebackvote.infrastructure.util.Constants.*;
import static org.springframework.http.ResponseEntity.ok;
import static net.logstash.logback.marker.Markers.append;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CustomStopWatch customStopWatch;

    @PostMapping("valid_cpf/{cpf}")
    private ResponseEntity<UsersApiResponse> validUser(@PathVariable("cpf") String cpf){
        customStopWatch.start();

        var userValid = usersService.validCpf(cpf);

        var response = UsersApiResponse.builder().value(userValid.getValue()).build();

        log.info(append(ACTION_LOG, "Desafio back votos - Users")
                .and(append(REQUEST_LOG, "cpf: " + cpf))
                .and(append(RESPONSE_LOG, response))
                .and(append(RESPONSE_TIME, customStopWatch.getTotalTimeMillis()))
                , "Desafio back votos - Users finished successfully");

        return ok(response);
    }
}
