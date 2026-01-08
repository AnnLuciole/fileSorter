package com.annluciole.filesorter.service.handler.creationdate;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileSystemDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

/**
 * Сервис для получения актуальных данных о времени создания файла.
 * Способ получения зависит от типа файла.
 */
public abstract class CreationDateHandler {

    private final String fileType;

    public CreationDateHandler(String fileType) {
        this.fileType = fileType;
    }

    public abstract Instant getCreationDate(Path path);

    public final String getFileType() {
        return fileType;
    }

    protected final Instant getDateFromSystemDirectory(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            return metadata.getFirstDirectoryOfType(FileSystemDirectory.class).getDate(3).toInstant();
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
