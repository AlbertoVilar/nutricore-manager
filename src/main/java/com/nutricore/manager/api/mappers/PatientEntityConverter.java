package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.domain.entities.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientEntityConverter {

    Patient toEntity(PatientRequest request);

    PatientResponse toResponse(Patient entity);

}
