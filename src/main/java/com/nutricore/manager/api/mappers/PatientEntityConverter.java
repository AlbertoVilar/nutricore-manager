package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PatientRequestDTO;
import com.nutricore.manager.api.dto.PatientResponseDTO;
import com.nutricore.manager.domain.entities.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientEntityConverter {

    Patient toEntity(PatientRequestDTO request);

    PatientResponseDTO toResponse(Patient entity);
    // Get All
    List<PatientResponseDTO> toResponseList(List<Patient> patients);
    
    // Update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(PatientRequestDTO request, @MappingTarget Patient entity);
}
