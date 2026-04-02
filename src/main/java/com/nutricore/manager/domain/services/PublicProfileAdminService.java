package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicProfileAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicProfileAdminResponseDTO;
import com.nutricore.manager.api.mappers.PublicProfileMapper;
import com.nutricore.manager.domain.entities.PublicProfile;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.PublicProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublicProfileAdminService {

    private final PublicProfileRepository publicProfileRepository;
    private final PublicProfileMapper publicProfileMapper;

    @Transactional(readOnly = true)
    public PublicProfileAdminResponseDTO getProfile() {
        return publicProfileMapper.toAdminResponse(getProfileEntity());
    }

    @Transactional
    public PublicProfileAdminResponseDTO updateProfile(PublicProfileAdminRequestDTO request) {
        PublicProfile entity = publicProfileRepository.findFirstByOrderByIdAsc()
                .orElseGet(PublicProfile::new);

        publicProfileMapper.updateEntity(entity, request);
        return publicProfileMapper.toAdminResponse(publicProfileRepository.save(entity));
    }

    private PublicProfile getProfileEntity() {
        return publicProfileRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new ResourceNotFoundException("Perfil público não encontrado."));
    }
}
