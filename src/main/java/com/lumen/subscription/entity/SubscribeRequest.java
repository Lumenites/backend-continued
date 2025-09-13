package com.lumen.subscription.entity;

public class SubscribeRequest {
    private Long userId;
    private Long planId;
    private boolean autoRenew = true;
    private boolean trial = false;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public boolean isAutoRenew() { return autoRenew; }
    public void setAutoRenew(boolean autoRenew) { this.autoRenew = autoRenew; }

    public boolean isTrial() { return trial; }
    public void setTrial(boolean trial) { this.trial = trial; }
}

