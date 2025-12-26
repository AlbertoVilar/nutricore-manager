package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.NutritionistRequestDTO;
import com.nutricore.manager.api.dto.NutritionistResponseDTO;
import com.nutricore.manager.domain.entities.Nutritionist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NutritionistMapper {

    Nutritionist toEntity(NutritionistRequestDTO dto);

    NutritionistResponseDTO toResponse(Nutritionist entity);

    void updateEntityFromDto(NutritionistRequestDTO dto, @MappingTarget Nutritionist entity);
}
