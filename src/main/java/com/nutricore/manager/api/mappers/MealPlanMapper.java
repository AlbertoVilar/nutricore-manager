package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.MealPlanResponseDTO;
import com.nutricore.manager.domain.entities.MealPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {
    MealPlanResponseDTO toResponse(MealPlan entity);
}
