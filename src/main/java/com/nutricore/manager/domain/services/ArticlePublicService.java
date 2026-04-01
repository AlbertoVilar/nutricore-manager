package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicArticleResponseDTO;
import com.nutricore.manager.api.mappers.PublicArticleMapper;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticlePublicService {

    private final ArticleRepository articleRepository;
    private final PublicArticleMapper publicArticleMapper;

    @Transactional(readOnly = true)
    public List<PublicArticleResponseDTO> findAllPublished() {
        return articleRepository.findAllByStatusOrderByPublishedAtDesc(EditorialStatus.PUBLISHED).stream()
                .map(publicArticleMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicArticleResponseDTO findBySlug(String slug) {
        return articleRepository.findBySlugAndStatus(slug, EditorialStatus.PUBLISHED)
                .map(publicArticleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo público não encontrado."));
    }
}
