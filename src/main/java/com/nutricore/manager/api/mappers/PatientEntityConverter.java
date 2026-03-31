package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PatientRequestDTO;
import com.nutricore.manager.api.dto.PatientResponseDTO;
import com.nutricore.manager.domain.entities.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientEntityConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anamnesisRecords", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Patient toEntity(PatientRequestDTO request);

    PatientResponseDTO toResponse(Patient entity);
    // Get All
    List<PatientResponseDTO> toResponseList(List<Patient> patients);
    
    // Update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anamnesisRecords", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void toUpdate(PatientRequestDTO request, @MappingTarget Patient entity);
}
