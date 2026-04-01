package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.MediaUploadResponseDTO;
import com.nutricore.manager.infrastructure.storage.MediaStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/media")
@Tag(name = "Admin Media", description = "Upload simples de midia para o MVP editorial")
public class AdminMediaController {

    private final MediaStorageService mediaStorageService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Envia uma imagem e retorna a URL publica do MVP")
    public ResponseEntity<MediaUploadResponseDTO> uploadImage(@RequestPart("file") MultipartFile file) {
        String url = mediaStorageService.storeImage(file);
        return ResponseEntity.ok(new MediaUploadResponseDTO(
                url,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
        ));
    }
}
