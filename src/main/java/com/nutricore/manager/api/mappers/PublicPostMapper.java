package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.domain.entities.PublicPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicPostMapper {

    PublicPostResponseDTO toResponse(PublicPost entity);
}
