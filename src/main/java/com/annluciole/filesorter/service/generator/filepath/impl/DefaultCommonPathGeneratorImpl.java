package com.annluciole.filesorter.service.generator.filepath.impl;

import com.annluciole.filesorter.config.ApplicationProperties;
import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.enums.SortStrategy;
import com.annluciole.filesorter.service.generator.filepath.CommonPathGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class DefaultCommonPathGeneratorImpl extends CommonPathGenerator {

    private final ApplicationProperties applicationProperties;

    public DefaultCommonPathGeneratorImpl(@Value("${destination.file.path}") String destinationFilesPath,
                                          ApplicationProperties applicationProperties) {
        super(destinationFilesPath, null);
        this.applicationProperties = applicationProperties;
    }

    @Override
    public StringBuilder generateCommonPath(FileInfo fileInfo) {
        StringBuilder commonPath = super.generateCommonPath(fileInfo);
        ZonedDateTime creationDate = fileInfo.getCreatedDate();
        String year = String.valueOf(creationDate.getYear());
        commonPath.append(year);
        commonPath.append("/");
        if (applicationProperties.getSortStrategy() == SortStrategy.BY_YEAR_AND_MONTH) {
            String month = String.valueOf(creationDate.getMonthValue());
            commonPath.append(month);
            commonPath.append("/");
        }
        return commonPath;
    }
}
