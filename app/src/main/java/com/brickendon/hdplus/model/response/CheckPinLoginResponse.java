package com.brickendon.hdplus.model.response;

public class CheckPinLoginResponse {
    private boolean hasPinSetup;
    private int attemptCount;
    private int maxAttemptCount;

    public boolean isHasPinSetup() {
        return hasPinSetup;
    }

    public void setHasPinSetup(boolean hasPinSetup) {
        this.hasPinSetup = hasPinSetup;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public int getMaxAttemptCount() {
        return maxAttemptCount;
    }

    public void setMaxAttemptCount(int maxAttemptCount) {
        this.maxAttemptCount = maxAttemptCount;
    }
}
