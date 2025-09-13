package com.lumen.subscription.controllers;

import com.lumen.subscription.entity.SubscribeRequest;
import com.lumen.subscription.entity.Subscription;
import com.lumen.subscription.entity.UpdateSubscriptionRequest;
import com.lumen.subscription.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    // Subscribe
    @PostMapping("/new")
    public ResponseEntity<Subscription> subscribe(@RequestBody SubscribeRequest request) {
        int daysValid = request.isTrial() ? 7 : 30;
        Subscription subscription = subscriptionService.subscribe(
                request.getUserId(),
                request.getPlanId(),
                request.isAutoRenew(),
                request.isTrial(),
                daysValid
        );
        return ResponseEntity.ok(subscription);
    }

    // Upgrade/Downgrade/Cancel/Renew
    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id,
                                                           @RequestBody UpdateSubscriptionRequest request) {
        Subscription subscription = subscriptionService.updateSubscription(
                id,
                request.getPlanId(),
                request.isAutoRenew(),
                request.isCancel()
        );
        return ResponseEntity.ok(subscription);
    }

    // Get user subscriptions (status/history)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }
}
