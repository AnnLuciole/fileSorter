package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FileNameGenerator;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameByCreationDateGenerator implements FileNameGenerator {

    @Override
    public String generateFileName(Path path, ZonedDateTime creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH.mm.ss");
        return creationDate.format(formatter) + "." +
                getFileExtension(path.getFileName().toString());
    }
}
