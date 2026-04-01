package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.api.mappers.PublicPostMapper;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicPostService {

    private final PostRepository postRepository;
    private final PublicPostMapper publicPostMapper;

    @Transactional(readOnly = true)
    public List<PublicPostResponseDTO> findAllPublished() {
        return postRepository.findAllByStatusOrderByPublishedAtDesc(EditorialStatus.PUBLISHED).stream()
                .map(publicPostMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicPostResponseDTO findBySlug(String slug) {
        return postRepository.findBySlugAndStatus(slug, EditorialStatus.PUBLISHED)
                .map(publicPostMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Post público não encontrado."));
    }
}
