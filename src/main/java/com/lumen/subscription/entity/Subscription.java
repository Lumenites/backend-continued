package com.lumen.subscription.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "subscriptions")
@Data
public class Subscription {

    @Id
    private Long id; // Snowflake-generated

    private Long userId; // passed from frontend

    private String planId; // MongoDB Plan _id

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private SubscriptionStatus status; // ACTIVE, EXPIRED, CANCELLED, TRIAL

    private boolean autoRenew;

    // Constructors
    public Subscription() {}

    public Subscription(Long id, Long userId, String planId, LocalDateTime startDate,
                        LocalDateTime endDate, SubscriptionStatus status, boolean autoRenew) {
        this.id = id;
        this.userId = userId;
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.autoRenew = autoRenew;
    }

    // Getters & Setters
}
