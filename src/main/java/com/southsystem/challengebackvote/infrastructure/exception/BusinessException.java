package com.southsystem.challengebackvote.infrastructure.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7413077157510098844L;

    public BusinessException(String message){
        super(message);
    }
}
