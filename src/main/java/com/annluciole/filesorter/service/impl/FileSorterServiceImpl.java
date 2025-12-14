package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FilePathGenerator;
import com.annluciole.filesorter.service.FileSorterService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileSorterServiceImpl implements FileSorterService {

    private final FilePathGenerator filePathGenerator;

    public FileSorterServiceImpl(FilePathGenerator filePathGenerator) {
        this.filePathGenerator = filePathGenerator;
    }

    public void sortFilesByPath(Path filesPath) {
        try (Stream<Path> pathStream = Files.walk(filesPath)) {
            pathStream.filter(Files::isRegularFile).forEach(path -> {
                try {
                    Path destinationPath = filePathGenerator.generateDestinationPath(path);
                    if (destinationPath != null) {
                        int counter = 1;
                        while (Files.exists(destinationPath)) {
                            int pointIdx = destinationPath.toString().lastIndexOf(".");
                            StringBuilder newDstPath = new StringBuilder(destinationPath.toString());
                            newDstPath.insert(pointIdx, " (");
                            newDstPath.insert(pointIdx, counter);
                            newDstPath.insert(pointIdx, ")");
                            destinationPath = Path.of(newDstPath.toString());
                            counter++;
                        }
                        Files.move(path, destinationPath);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
