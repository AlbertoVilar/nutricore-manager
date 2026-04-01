package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.AdminPostResponseDTO;
import com.nutricore.manager.api.dto.AdminPostUpsertRequestDTO;
import com.nutricore.manager.domain.entities.Post;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostAdminMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "featured", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "galleryImageUrls", source = "galleryImageUrls", qualifiedByName = "joinLines")
    Post toEntity(AdminPostUpsertRequestDTO request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "featured", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "galleryImageUrls", source = "galleryImageUrls", qualifiedByName = "joinLines")
    void updateEntity(AdminPostUpsertRequestDTO request, @MappingTarget Post entity);

    @Mapping(target = "galleryImageUrls", source = "galleryImageUrls", qualifiedByName = "splitLines")
    AdminPostResponseDTO toResponse(Post entity);

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
