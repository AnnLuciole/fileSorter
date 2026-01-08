package com.annluciole.filesorter.runner;

import com.annluciole.filesorter.service.FileSorterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public void run(@Nullable String... args) {
        if (sourceFilesPath == null || sourceFilesPath.isEmpty()) {
            return;
        }
        Path path = Paths.get(sourceFilesPath);
        if (!Files.exists(path)) {
            return;
        }
        fileSorterService.sortFilesByPath(path);
    }
}
