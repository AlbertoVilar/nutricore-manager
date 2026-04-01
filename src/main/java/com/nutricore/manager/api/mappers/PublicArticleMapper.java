package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PublicArticleResponseDTO;
import com.nutricore.manager.domain.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicArticleMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "splitLines")
    PublicArticleResponseDTO toResponse(Article entity);

    @Named("splitLines")
    default List<String> splitLines(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();
    }
}
