package com.lumen.subscription.entity;


public class UpdateSubscriptionRequest {
    private String planId; // optional
    private boolean autoRenew = true;
    private boolean cancel = false;

    // Getters and setters
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public boolean isAutoRenew() { return autoRenew; }
    public void setAutoRenew(boolean autoRenew) { this.autoRenew = autoRenew; }

    public boolean isCancel() { return cancel; }
    public void setCancel(boolean cancel) { this.cancel = cancel; }
}
