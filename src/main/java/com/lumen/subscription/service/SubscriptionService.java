package com.lumen.subscription.service;

import com.lumen.subscription.entity.Subscription;
import com.lumen.subscription.entity.SubscriptionStatus;
import com.lumen.subscription.repo.SubscriptionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SnowflakeIdGenerator idGenerator;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.idGenerator = new SnowflakeIdGenerator(5);
    }

    // Subscribe
    public Subscription subscribe(Long userId, Long planId, boolean autoRenew, boolean isTrial, int daysValid) {
        Long id = idGenerator.nextId();
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(daysValid);

        SubscriptionStatus status = isTrial ? SubscriptionStatus.TRIAL : SubscriptionStatus.ACTIVE;
        String newPlanId = planId.toString();
        Subscription subscription = new Subscription(id, userId, newPlanId, startDate, endDate, status, autoRenew);
        return subscriptionRepository.save(subscription);
    }

    // Upgrade/Downgrade/Renew
    public Subscription updateSubscription(Long subscriptionId, String newPlanId, boolean autoRenew, boolean cancel) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (cancel) {
            sub.setStatus(SubscriptionStatus.CANCELLED);
        } else {
            if (newPlanId != null) sub.setPlanId(newPlanId);
            sub.setAutoRenew(autoRenew);
            sub.setEndDate(sub.getEndDate().isBefore(LocalDateTime.now()) ?
                    LocalDateTime.now().plusDays(30) : sub.getEndDate().plusDays(30));
            sub.setStatus(SubscriptionStatus.ACTIVE);
        }

        return subscriptionRepository.save(sub);
    }

    // Get status/history
    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
}
