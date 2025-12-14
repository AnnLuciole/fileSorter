package com.annluciole.filesorter.service.handler;

import com.annluciole.filesorter.service.CreationDateHandler;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;
import com.drew.metadata.mp4.Mp4Directory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;

@Component
public class Mp4CreationDateHandler extends CreationDateHandler {

    private static final String FILE_TYPE = "mp4";

    public Mp4CreationDateHandler() {
        super(FILE_TYPE);
    }

    @Override
    public Instant getCreationDate(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            Directory directory;
            directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
            if (directory == null) {
                directory = metadata.getFirstDirectoryOfType(QuickTimeDirectory.class);
            }
            return directory.getDate(256).toInstant();
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
