package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.internal.enums.ResultCodeEnum;
import com.southsystem.challengebackvote.domain.model.internal.response.ResponseException;
import com.southsystem.challengebackvote.infrastructure.exception.BadRequestException;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.exception.EntityNotFoundException;
import com.southsystem.challengebackvote.infrastructure.exception.UsersIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> exception(final Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(ResponseException.builder().code(ResultCodeEnum.INTERNAL_SERVER_ERROR)
                .message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsersIntegrationException.class)
    public ResponseEntity<ResponseException> usersIntegrationException(final UsersIntegrationException e){
        log.debug("handling exception:: " + e);
        return new ResponseEntity<>(ResponseException.builder().code(ResultCodeEnum.UNPROCESSABLE_ENTITY)
                .message(e.getMessage()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseException> entityNotFoundException(final EntityNotFoundException e){
        log.debug("handling exception:: " + e);
        return new ResponseEntity<>(ResponseException.builder().code(ResultCodeEnum.NOT_FOUND)
                .message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseException> badRequestException(final BadRequestException e){
        log.debug("handling exception:: " + e);
        return new ResponseEntity<>(ResponseException.builder().code(ResultCodeEnum.BAD_REQUEST)
                .message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseException> businessException(final BusinessException e){
        log.debug("handling exception:: " + e);
        return new ResponseEntity<>(ResponseException.builder().code(ResultCodeEnum.UNPROCESSABLE_ENTITY)
                .message(e.getMessage()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
