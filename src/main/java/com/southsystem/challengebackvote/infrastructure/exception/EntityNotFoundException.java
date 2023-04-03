package com.southsystem.challengebackvote.infrastructure.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1582553605606117500L;

    public EntityNotFoundException(String message){
        super(message);
    }
}
