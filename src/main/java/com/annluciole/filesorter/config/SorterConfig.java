package com.annluciole.filesorter.config;

import com.annluciole.filesorter.service.CreationDateHandlerRepository;
import com.annluciole.filesorter.service.FileNameGenerator;
import com.annluciole.filesorter.service.FilePathGenerator;
import com.annluciole.filesorter.service.impl.FileNameByCreationDateGenerator;
import com.annluciole.filesorter.service.impl.FileNameGeneratorImpl;
import com.annluciole.filesorter.service.impl.FilePathGeneratorByMonthAndYearImpl;
import com.annluciole.filesorter.service.impl.FilePathGeneratorByYearImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SorterConfig {

    private final ApplicationProperties applicationProperties;
    private final String destinationFilesPath;

    @Autowired
    public SorterConfig(ApplicationProperties applicationProperties,
                        @Value("${destination.file.path}") String destinationFilesPath) {
        this.applicationProperties = applicationProperties;
        this.destinationFilesPath = destinationFilesPath;
    }

    @Bean
    public FilePathGenerator filePathGenerator(CreationDateHandlerRepository creationDateHandlerRepository) {
        return switch (applicationProperties.getSortStrategy()) {
            case BY_YEAR_AND_MONTH ->
                    new FilePathGeneratorByMonthAndYearImpl(destinationFilesPath, creationDateHandlerRepository);
            case BY_YEAR -> new FilePathGeneratorByYearImpl(destinationFilesPath, creationDateHandlerRepository);
        };
    }

    @Bean
    public FileNameGenerator fileNameGenerator() {
        return switch (applicationProperties.getRenameStrategy()) {
            case NO_RENAME -> new FileNameGeneratorImpl();
            case RENAME_TO_CREATION_DATE -> new FileNameByCreationDateGenerator();
        };
    }
}
