package com.annluciole.filesorter.service.handler.creationdate.impl;

import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandler;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Instant;

@Component
public class DefaultCreationDateHandler extends CreationDateHandler {

    public DefaultCreationDateHandler() {
        super(null);
    }

    @Override
    public Instant getCreationDate(Path path) {
        return getDateFromSystemDirectory(path);
    }
}
