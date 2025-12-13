package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FileSorterService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSorterServiceImpl implements FileSorterService {

    private final FilePathGeneratorImpl filePathGeneratorImpl;

    public FileSorterServiceImpl(FilePathGeneratorImpl filePathGeneratorImpl) {
        this.filePathGeneratorImpl = filePathGeneratorImpl;
    }

    public void sortFilesByPath(Path filesPath) {
        try (Stream<Path> pathStream = Files.walk(filesPath)) {
            pathStream.filter(Files::isRegularFile).forEach(path -> {
                try {
                    Files.move(path,
                            filePathGeneratorImpl.generateDestinationPath(path),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
