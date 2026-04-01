package com.nutricore.manager.infrastructure.storage;

import com.nutricore.manager.domain.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

@Service
public class MediaStorageService {

    private final Path imageDirectory;
    private final long maxImageBytes;

    public MediaStorageService(
            @Value("${app.media.storage-path:${user.dir}/storage/editorial-media}") String storagePath,
            @Value("${app.media.max-image-size-mb:8}") long maxImageSizeMb
    ) {
        this.imageDirectory = Paths.get(storagePath).resolve("images");
        this.maxImageBytes = maxImageSizeMb * 1024 * 1024;

        try {
            Files.createDirectories(this.imageDirectory);
        } catch (IOException ex) {
            throw new IllegalStateException("Não foi possível preparar o diretório de mídia.", ex);
        }
    }

    public String storeImage(MultipartFile file) {
        validateImage(file);

        String extension = resolveExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + extension;
        Path targetFile = imageDirectory.resolve(filename);

        try {
            file.transferTo(targetFile);
        } catch (IOException ex) {
            throw new IllegalStateException("Não foi possível salvar a imagem enviada.", ex);
        }

        return "/api/media/images/" + filename;
    }

    public String getImageDirectoryUri() {
        return imageDirectory.toUri().toString();
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Envie um arquivo de imagem válido.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new BusinessException("Apenas arquivos de imagem são aceitos neste MVP.");
        }

        if (file.getSize() > maxImageBytes) {
            throw new BusinessException("A imagem excede o tamanho máximo permitido para o MVP.");
        }
    }

    private String resolveExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return ".bin";
        }

        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
}
