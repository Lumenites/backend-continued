package com.lumen.subscription.service;

import com.lumen.subscription.entity.Plan;
import com.lumen.subscription.repo.PlanRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(2); // nodeId = 1

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan createPlan(Plan plan) {
        plan.setId(idGenerator.nextId());
        return planRepository.save(plan);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {
        Plan plan = getPlanById(id);
        plan.setName(updatedPlan.getName());
        plan.setPrice(updatedPlan.getPrice());
        plan.setQuotas(updatedPlan.getQuotas());
        plan.setAutoRenew(updatedPlan.isAutoRenew());
        plan.setActive(updatedPlan.isActive());
        return planRepository.save(plan);
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }
}

