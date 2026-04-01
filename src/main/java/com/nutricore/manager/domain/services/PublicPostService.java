package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.api.mappers.PublicPostMapper;
import com.nutricore.manager.infrastructure.db.repositories.PublicPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicPostService {

    private final PublicPostRepository publicPostRepository;
    private final PublicPostMapper publicPostMapper;

    @Transactional(readOnly = true)
    public List<PublicPostResponseDTO> findAll() {
        return publicPostRepository.findAllByOrderByPublishedAtDesc().stream()
                .map(publicPostMapper::toResponse)
                .toList();
    }
}
