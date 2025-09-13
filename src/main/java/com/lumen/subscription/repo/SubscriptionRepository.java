package com.lumen.subscription.repo;

import com.lumen.subscription.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
}
