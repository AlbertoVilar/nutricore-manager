package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.AdminArticleResponseDTO;
import com.nutricore.manager.api.dto.AdminArticleUpsertRequestDTO;
import com.nutricore.manager.domain.entities.Article;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleAdminMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "readTimeMinutes", ignore = true)
    @Mapping(target = "featured", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tags", source = "tags", qualifiedByName = "joinLines")
    Article toEntity(AdminArticleUpsertRequestDTO request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "readTimeMinutes", ignore = true)
    @Mapping(target = "featured", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tags", source = "tags", qualifiedByName = "joinLines")
    void updateEntity(AdminArticleUpsertRequestDTO request, @MappingTarget Article entity);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "splitLines")
    AdminArticleResponseDTO toResponse(Article entity);

    @Named("joinLines")
    default String joinLines(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.stream()
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .reduce((left, right) -> left + "\n" + right)
                .orElse(null);
    }

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
