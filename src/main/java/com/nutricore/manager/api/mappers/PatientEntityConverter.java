package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.domain.entities.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientEntityConverter {

    Patient toEntity(PatientRequest request);

    PatientResponse toResponse(Patient entity);
    // Get All
    List<PatientResponse> toResponseList(List<Patient> patients);
    
    // Update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdate(PatientRequest request, @MappingTarget Patient entity);
}
