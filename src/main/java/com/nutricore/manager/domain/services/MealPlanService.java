package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.MealPlanResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanService {

    public MealPlanResponseDTO createMealPlan(Object request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public MealPlanResponseDTO getActiveMealPlanByPatient(Long patientId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void deactivateActiveMealPlan(Long patientId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
