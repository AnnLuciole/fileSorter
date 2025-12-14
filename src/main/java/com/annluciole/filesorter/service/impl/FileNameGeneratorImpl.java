package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FileNameGenerator;

import java.nio.file.Path;
import java.time.ZonedDateTime;

public class FileNameGeneratorImpl implements FileNameGenerator {

    @Override
    public String generateFileName(Path path, ZonedDateTime creationDate) {
        return path.getFileName().toString();
    }
}
