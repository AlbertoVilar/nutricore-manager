package com.nutricore.manager.api.dto;

public record MediaUploadResponseDTO(
        String url,
        String originalFilename,
        String contentType,
        long sizeBytes
) {
}
