package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequestDTO;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponseDTO;
import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClinicalAnamnesisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ClinicalAnamnesis toEntity(ClinicalAnamnesisRequestDTO request);

    ClinicalAnamnesisResponseDTO toResponse(ClinicalAnamnesis entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true) // Não permitimos mudar o paciente no update
    void updateEntityFromRequest(ClinicalAnamnesisRequestDTO request, @MappingTarget ClinicalAnamnesis entity);

    List<ClinicalAnamnesisResponseDTO> toResponseList(List<ClinicalAnamnesis> anamneses);
}
