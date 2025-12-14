package com.annluciole.filesorter.service.handler;

import com.annluciole.filesorter.service.CreationDateHandler;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Component
public class DngCreationDateHandler extends CreationDateHandler {

    private static final String FILE_TYPE = "dng";

    public DngCreationDateHandler() {
        super(FILE_TYPE);
    }

    @Override
    public Instant getCreationDate(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String dateString = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class).getString(306);
            return formatter.parse(dateString).toInstant();
        } catch (ImageProcessingException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
