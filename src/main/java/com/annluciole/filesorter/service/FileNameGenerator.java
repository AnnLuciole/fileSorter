package com.annluciole.filesorter.service;

import java.nio.file.Path;
import java.time.ZonedDateTime;

public interface FileNameGenerator {

    String generateFileName(Path path, ZonedDateTime creationDate);

    default String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
