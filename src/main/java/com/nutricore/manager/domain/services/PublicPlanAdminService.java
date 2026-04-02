package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicPlanAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicPlanAdminResponseDTO;
import com.nutricore.manager.api.mappers.PublicPlanMapper;
import com.nutricore.manager.domain.entities.PublicPlan;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.PublicPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicPlanAdminService {

    private final PublicPlanRepository publicPlanRepository;
    private final PublicPlanMapper publicPlanMapper;

    @Transactional(readOnly = true)
    public List<PublicPlanAdminResponseDTO> findAll() {
        return publicPlanRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(publicPlanMapper::toAdminResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicPlanAdminResponseDTO findById(Long id) {
        return publicPlanRepository.findById(id)
                .map(publicPlanMapper::toAdminResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Plano público não encontrado com ID: " + id));
    }

    @Transactional
    public PublicPlanAdminResponseDTO create(PublicPlanAdminRequestDTO request) {
        PublicPlan entity = publicPlanMapper.toEntity(request);
        return publicPlanMapper.toAdminResponse(publicPlanRepository.save(entity));
    }

    @Transactional
    public PublicPlanAdminResponseDTO update(Long id, PublicPlanAdminRequestDTO request) {
        PublicPlan entity = publicPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano público não encontrado com ID: " + id));

        publicPlanMapper.updateEntity(entity, request);
        return publicPlanMapper.toAdminResponse(publicPlanRepository.save(entity));
    }

    @Transactional
    public PublicPlanAdminResponseDTO activate(Long id) {
        PublicPlan entity = publicPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano público não encontrado com ID: " + id));
        entity.setActive(true);
        return publicPlanMapper.toAdminResponse(publicPlanRepository.save(entity));
    }

    @Transactional
    public PublicPlanAdminResponseDTO deactivate(Long id) {
        PublicPlan entity = publicPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano público não encontrado com ID: " + id));
        entity.setActive(false);
        return publicPlanMapper.toAdminResponse(publicPlanRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!publicPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plano público não encontrado com ID: " + id);
        }
        publicPlanRepository.deleteById(id);
    }
}
