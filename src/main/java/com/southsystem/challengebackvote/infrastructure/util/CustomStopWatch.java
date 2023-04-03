package com.southsystem.challengebackvote.infrastructure.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class CustomStopWatch extends StopWatch {

    @Override
    public void stop() {
        if (this.isRunning())
            super.stop();
    }

    @Override
    public long getTotalTimeMillis() {
        this.stop();
        return super.getTotalTimeMillis();
    }
}
