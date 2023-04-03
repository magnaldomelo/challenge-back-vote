package com.southsystem.challengebackvote.domain.model.internal.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Answer {
    NAO("Não"),
    SIM("Sim");

    private String answer;

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
