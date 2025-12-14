package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.CreationDateHandlerRepository;
import com.annluciole.filesorter.service.FileNameGenerator;
import com.annluciole.filesorter.service.FilePathGenerator;

import java.nio.file.Path;
import java.time.ZonedDateTime;

public class FilePathGeneratorByYearImpl extends FilePathGenerator {

    public FilePathGeneratorByYearImpl(String destinationFilesPath,
                                       CreationDateHandlerRepository repository,
                                       FileNameGenerator fileNameGenerator) {
        super(destinationFilesPath, repository, fileNameGenerator);
    }

    @Override
    public Path generateDestinationPath(Path path) {
        return null;
    }

    @Override
    protected void generatePathByStrategy(ZonedDateTime creationDate, StringBuilder commonPath) {
        String year = String.valueOf(creationDate.getYear());
        commonPath.append(year);
        commonPath.append("/");
    }
}
