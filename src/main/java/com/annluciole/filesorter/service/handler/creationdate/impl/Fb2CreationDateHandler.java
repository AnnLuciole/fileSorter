package com.annluciole.filesorter.service.handler.creationdate.impl;

import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

@Component
public class Fb2CreationDateHandler extends CreationDateHandler {

    private static final String FILE_TYPE = "fb2";

    public Fb2CreationDateHandler() {
        super(FILE_TYPE);
    }

    @Override
    public Instant getCreationDate(Path path) {
        try {
            FileTime creationTime = Files.readAttributes(path, BasicFileAttributes.class).creationTime();
            return creationTime.toInstant();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
