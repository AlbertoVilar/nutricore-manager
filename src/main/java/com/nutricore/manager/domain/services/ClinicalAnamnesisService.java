package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.api.mappers.ClinicalAnamnesisMapper;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.ClinicalAnamnesisRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClinicalAnamnesisService {

    private final ClinicalAnamnesisRepository anamnesisRepository;
    private final PatientRepository patientRepository;
    private final ClinicalAnamnesisMapper anamnesisMapper;

    public ClinicalAnamnesisService(ClinicalAnamnesisRepository anamnesisRepository, PatientRepository patientRepository, ClinicalAnamnesisMapper anamnesisMapper) {
        this.anamnesisRepository = anamnesisRepository;
        this.patientRepository = patientRepository;
        this.anamnesisMapper = anamnesisMapper;
    }

    public ClinicalAnamnesisResponse createAnamnesis(ClinicalAnamnesisRequest request) {

        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: "
                        + request.patientId()));

        var anamnesi = anamnesisMapper.toEntity(request);

        patient.addAnamnesis(anamnesi);
                anamnesi = anamnesisRepository.save(anamnesi);

                return anamnesisMapper.toResponse(anamnesi);
    }
}
