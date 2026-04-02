package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicProfileResponseDTO;
import com.nutricore.manager.api.mappers.PublicProfileMapper;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.PublicProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicProfileService {

    private final PublicProfileRepository publicProfileRepository;
    private final PublicProfileMapper publicProfileMapper;

    @Transactional(readOnly = true)
    public PublicProfileResponseDTO getProfile() {
        return publicProfileRepository.findFirstByOrderByIdAsc()
                .map(publicProfileMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil público não encontrado."));
    }
}
