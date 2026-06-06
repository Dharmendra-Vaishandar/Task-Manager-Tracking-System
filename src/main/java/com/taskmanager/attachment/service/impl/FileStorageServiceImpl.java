package com.taskmanager.attachment.service.impl;

import com.taskmanager.attachment.exception.FileStorageException;
import com.taskmanager.attachment.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl
        implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String store(
            MultipartFile file
    ) {

        try {

            Path uploadPath =
                    Paths.get(uploadDir);

            Files.createDirectories(
                    uploadPath
            );

            String uniqueName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            Path target =
                    uploadPath.resolve(
                            uniqueName
                    );

            Files.copy(
                    file.getInputStream(),
                    target
            );

            return target.toString();

        } catch (Exception ex) {

            throw new FileStorageException(
                    ex.getMessage()
            );
        }
    }

    @Override
    public Resource load(
            String filePath
    ) {

        try {

            Path path =
                    Paths.get(filePath);

            return new UrlResource(
                    path.toUri()
            );

        } catch (Exception ex) {

            throw new FileStorageException(
                    ex.getMessage()
            );
        }
    }
}
