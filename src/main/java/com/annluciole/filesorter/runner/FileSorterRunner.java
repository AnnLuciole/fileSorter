package com.annluciole.filesorter.runner;

import com.annluciole.filesorter.service.FileSorterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class FileSorterRunner implements CommandLineRunner {

    private final String sourceFilesPath;
    private final FileSorterService fileSorterService;

    public FileSorterRunner(@Value("${source.file.path}") String sourceFilesPath,
                            FileSorterService fileSorterService) {
        this.sourceFilesPath = sourceFilesPath;
        this.fileSorterService = fileSorterService;
    }

    @Override
    public void run(String... args) throws Exception  {
        fileSorterService.sortFilesByPath(Paths.get(sourceFilesPath));
    }
}
