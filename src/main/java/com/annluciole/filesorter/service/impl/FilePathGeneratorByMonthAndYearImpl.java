package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.CreationDateHandlerRepository;
import com.annluciole.filesorter.service.FileNameGenerator;
import com.annluciole.filesorter.service.FilePathGenerator;

import java.time.ZonedDateTime;

public class FilePathGeneratorByMonthAndYearImpl extends FilePathGenerator {

    public FilePathGeneratorByMonthAndYearImpl(String destinationFilesPath,
                                               CreationDateHandlerRepository repository,
                                               FileNameGenerator fileNameGenerator) {
        super(destinationFilesPath, repository, fileNameGenerator);
    }

    @Override
    protected void generatePathByStrategy(ZonedDateTime creationDate, StringBuilder commonPath) {
        String year = String.valueOf(creationDate.getYear());
        commonPath.append(year);
        commonPath.append("/");
        String month = String.valueOf(creationDate.getMonthValue());
        commonPath.append(month);
        commonPath.append("/");
    }
}
