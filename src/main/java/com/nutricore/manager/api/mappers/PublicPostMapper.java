package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicPostMapper {

    @Mapping(target = "galleryImageUrls", source = "galleryImageUrls", qualifiedByName = "splitLines")
    PublicPostResponseDTO toResponse(Post entity);

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
