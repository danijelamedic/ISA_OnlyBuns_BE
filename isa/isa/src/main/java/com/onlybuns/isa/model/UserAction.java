package com.onlybuns.isa.model;

import java.time.Instant;

public class UserAction {
    private Instant lastActionTime;
    private int actionCount;

    public UserAction(Instant lastActionTime, int actionCount) {
        this.lastActionTime = lastActionTime;
        this.actionCount = actionCount;
    }

    public Instant getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(Instant lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public void incrementActionCount() {
        this.actionCount++;
    }
}
