package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;
import com.nutricore.manager.domain.entities.AnthropometricAssessment;
import com.nutricore.manager.domain.utils.NutriCalculators;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        imports = {NutriCalculators.class},
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE
)
public interface AnthropometricMapper {

    // 1. Request -> Entidade
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "bmi", ignore = true)
    @Mapping(target = "fatMassKg", ignore = true)
    @Mapping(target = "leanMassKg", ignore = true)
    @Mapping(target = "leanMassPercentage", ignore = true)
    @Mapping(target = "waistHipRatio", ignore = true)
    @Mapping(target = "totalEnergyExpenditure", ignore = true)
    AnthropometricAssessment toEntity(AnthropometricRequestDTO request);

    // 2. Entidade -> Response
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(target = "bmiClassification", source = "bmi", qualifiedByName = "mapBmiClassification")
    @Mapping(target = "waistHipRisk", source = "waistHipRatio", qualifiedByName = "mapWaistHipRisk")
    AnthropometricResponseDTO toResponse(AnthropometricAssessment entity);

    // 3. BMI → Classificação (AJUSTADO)
    @Named("mapBmiClassification")
    default String mapBmiClassification(BigDecimal bmi) {
        var classification = NutriCalculators.classifyBmi(bmi);
        return classification != null ? classification.getDescription() : null;
    }

    // 4. RCQ → Risco (AJUSTADO)
    @Named("mapWaistHipRisk")
    default String mapWaistHipRisk(BigDecimal waistHipRatio) {
        var risk = NutriCalculators.classifyWaistHip(waistHipRatio);
        return risk != null ? risk.name() : null;
    }

    // UPDATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "bmi", ignore = true)
    @Mapping(target = "fatMassKg", ignore = true)
    @Mapping(target = "leanMassKg", ignore = true)
    @Mapping(target = "leanMassPercentage", ignore = true)
    @Mapping(target = "waistHipRatio", ignore = true)
    @Mapping(target = "totalEnergyExpenditure", ignore = true)
    void updateEntityFromDto(AnthropometricRequestDTO request, @MappingTarget AnthropometricAssessment assessment);
}
