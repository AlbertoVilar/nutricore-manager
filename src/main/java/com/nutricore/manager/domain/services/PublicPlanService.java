package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicPlanResponseDTO;
import com.nutricore.manager.api.mappers.PublicPlanMapper;
import com.nutricore.manager.infrastructure.db.repositories.PublicPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicPlanService {

    private final PublicPlanRepository publicPlanRepository;
    private final PublicPlanMapper publicPlanMapper;

    @Transactional(readOnly = true)
    public List<PublicPlanResponseDTO> findAll() {
        return publicPlanRepository.findAllByActiveTrueOrderByDisplayOrderAsc().stream()
                .map(publicPlanMapper::toResponse)
                .toList();
    }
}
