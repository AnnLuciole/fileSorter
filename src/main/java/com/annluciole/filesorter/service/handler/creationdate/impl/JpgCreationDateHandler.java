package com.annluciole.filesorter.service.handler.creationdate.impl;

import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandler;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Component
public class JpgCreationDateHandler extends CreationDateHandler {

    private static final String FILE_TYPE = "jpg";

    public JpgCreationDateHandler() {
        super(FILE_TYPE);
    }

    @Override
    public Instant getCreationDate(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            String dateString = null;
            if (directory != null) {
                dateString = directory.getString(306);
            }
            if (dateStringNotFound(dateString)) {
                directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (directory != null) {
                    dateString = directory.getString(36867);
                }
                if (dateStringNotFound(dateString)) {
                    return getDateFromSystemDirectory(path);
                }
            }
            return formatter.parse(dateString).toInstant();
        } catch (ImageProcessingException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean dateStringNotFound(String dateString) {
        return dateString == null
                || dateString.isEmpty()
                || dateString.equals("0000:00:00 00:00:00");
    }
}
