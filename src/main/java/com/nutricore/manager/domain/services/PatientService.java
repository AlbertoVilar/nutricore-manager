package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PatientRequestDTO;
import com.nutricore.manager.api.dto.PatientResponseDTO;
import com.nutricore.manager.api.mappers.PatientEntityConverter;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.DatabaseException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientEntityConverter entityConverter;

    public PatientService(PatientRepository patientRepository, PatientEntityConverter entityConverter) {
        this.patientRepository = patientRepository;
        this.entityConverter = entityConverter;
    }

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO request) {
        patientRepository.findByEmail(request.email())
                .ifPresent(p -> {
                    throw new BusinessException("Já existe um paciente cadastrado com o e-mail: " + request.email());
                });

        var entity = entityConverter.toEntity(request);
        return entityConverter.toResponse(patientRepository.save(entity));
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO request) {
        var entity = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));

        entityConverter.toUpdate(request, entity);
        return entityConverter.toResponse(patientRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> findAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(entityConverter::toResponse);
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO findPatientById(Long id) {
        return patientRepository.findById(id)
                .map(entityConverter::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
    }

    @Transactional
    public void deletePatient(Long id) {
        var entity = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));

        try {
            patientRepository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir o paciente pois existem registros vinculados a ele.");
        }
    }
}