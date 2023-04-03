package com.southsystem.challengebackvote.infrastructure.exception;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -5537233879246838393L;

    public BadRequestException(String message){
        super(message);
    }
}
