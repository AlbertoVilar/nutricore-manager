package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.api.mappers.NutritionGoalMapper;
import com.nutricore.manager.domain.enums.goal.GoalStatus;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.NutritionGoalRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutritionGoalService {

    private final NutritionGoalRepository nutritionGoalRepository;
    private final PatientRepository patientRepository;
    private final NutritionGoalMapper nutritionGoalMapper;

    @Transactional
    public NutritionGoalResponseDTO create(NutritionGoalRequestDTO request) {
        // TODO: Validate patient existence
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID: " + request.patientId()));
        // TODO: Map DTO to entity
        var entity = nutritionGoalMapper.toEntity(request);

        // TODO: Set default status/rules
        entity.setPatient(patient);
        entity.setStatus(GoalStatus.PLANNED);

        entity = nutritionGoalRepository.save(entity);
        // TODO: Return response DTO
        return nutritionGoalMapper.toResponse(entity);
    }

    public NutritionGoalResponseDTO update(Long id, NutritionGoalRequestDTO request) {
        // TODO: Find existing goal by ID or throw exception
        // TODO: Update entity using mapper
        // TODO: Validate status transitions if needed
        // TODO: Save updated entity
        // TODO: Return response DTO
        return null;
    }

    public NutritionGoalResponseDTO findById(Long id) {
        // TODO: Find goal by ID or throw exception
        // TODO: Return response DTO
        return null;
    }

    public Page<NutritionGoalResponseDTO> findAllByPatient(Long patientId, Pageable pageable) {
        // TODO: Validate patient existence (optional, depends on requirement)
        // TODO: Find all goals by patient ID
        // TODO: Map page content to response DTOs
        return null;
    }

    public void delete(Long id) {
        // TODO: Find goal by ID or throw exception
        // TODO: Delete goal (or soft delete)
    }
}
