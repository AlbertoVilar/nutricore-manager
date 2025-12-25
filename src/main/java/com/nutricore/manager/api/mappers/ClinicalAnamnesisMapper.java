package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import com.nutricore.manager.domain.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClinicalAnamnesisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ClinicalAnamnesis toEntity(ClinicalAnamnesisRequest request);

    ClinicalAnamnesisResponse toResponse(ClinicalAnamnesis entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true) // Não permitimos mudar o paciente no update
    void updateEntityFromRequest(ClinicalAnamnesisRequest request, @MappingTarget ClinicalAnamnesis entity);

    List<ClinicalAnamnesisResponse> toResponseList(List<ClinicalAnamnesis> anamneses);
}
