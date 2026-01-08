package com.annluciole.filesorter.config;

import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.generator.filename.impl.BookFileNameGeneratorImpl;
import com.annluciole.filesorter.service.generator.filename.FileNameGenerator;
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
    public FileNameGenerator fileNameGenerator(FileInfoRepository fileInfoRepository) {
        return new BookFileNameGeneratorImpl(fileInfoRepository);
    }
}
