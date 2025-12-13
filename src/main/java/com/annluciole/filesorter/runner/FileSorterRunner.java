package com.annluciole.filesorter.runner;

import com.annluciole.filesorter.service.FileSorterService;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;

public class FileSorterRunner {

    private final String sourceFilesPath;
    private final FileSorterService fileSorterService;

    public FileSorterRunner(@Value("{$source.file.path}") String sourceFilesPath,
                            FileSorterService fileSorterService) {
        this.sourceFilesPath = sourceFilesPath;
        this.fileSorterService = fileSorterService;
    }

    public void run() {
        fileSorterService.sortFilesByPath(Paths.get(sourceFilesPath));
    }
}
