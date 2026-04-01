package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PublicProfileResponseDTO;
import com.nutricore.manager.domain.entities.PublicProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicProfileMapper {

    PublicProfileResponseDTO toResponse(PublicProfile entity);
}
