package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.domain.entities.NutritionGoal;
import com.nutricore.manager.domain.enums.goal.GoalStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NutritionGoalMapper {

    // ==================================================
    // CREATE — RequestDTO -> Entity
    // ==================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true) // setado no Service
    @Mapping(target = "status", expression = "java(GoalStatus.PLANNED)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    NutritionGoal toEntity(NutritionGoalRequestDTO request);

    // ==================================================
    // RESPONSE — Entity -> ResponseDTO
    // ==================================================
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(target = "overdueDays", expression = "java(entity.getOverdueDays())")
    NutritionGoalResponseDTO toResponse(NutritionGoal entity);

    // ==================================================
    // UPDATE — RequestDTO -> Existing Entity
    // ==================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true) // paciente não muda
    @Mapping(target = "status", ignore = true) // status controlado por regras
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(
            NutritionGoalRequestDTO request,
            @MappingTarget NutritionGoal entity);
}
