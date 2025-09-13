package com.lumen.subscription.repo;

import com.lumen.subscription.entity.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanRepository extends MongoRepository<Plan, Long> {
}
