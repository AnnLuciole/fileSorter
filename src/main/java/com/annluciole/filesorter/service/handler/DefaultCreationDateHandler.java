package com.annluciole.filesorter.service.handler;

import com.annluciole.filesorter.service.CreationDateHandler;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;

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
