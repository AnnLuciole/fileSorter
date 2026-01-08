package com.annluciole.filesorter.service.generator.filename;

import com.annluciole.filesorter.repository.FileInfoRepository;

import java.nio.file.Path;

public abstract class FileNameGenerator {

    protected final FileInfoRepository repository;
    private final String fileExtension;

    public FileNameGenerator(FileInfoRepository repository,
                             String fileExtension) {
        this.repository = repository;
        this.fileExtension = fileExtension;
    }

    public abstract void generateFileName(Path path);

    public final String getFileExtension() {
        return fileExtension;
    }
}
