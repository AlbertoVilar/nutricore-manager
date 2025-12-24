package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicalAnamnesisMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ClinicalAnamnesis toEntity(ClinicalAnamnesisRequest request);

    ClinicalAnamnesisResponse toResponse(ClinicalAnamnesis entity);
}
