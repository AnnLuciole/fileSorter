package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.config.ApplicationProperties;
import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.enums.RenameStrategy;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.FileRenameService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
public class FileRenameServiceImpl implements FileRenameService {

    private final ApplicationProperties applicationProperties;
    private final FileInfoRepository repository;

    public FileRenameServiceImpl(ApplicationProperties applicationProperties,
                                 FileInfoRepository repository) {
        this.applicationProperties = applicationProperties;
        this.repository = repository;
    }

    @Override
    public Path renameFile(Path path) {
        FileInfo fileInfo = repository.findByPath(path.toString());
        if (applicationProperties.getRenameStrategy() == RenameStrategy.NO_RENAME) {
            fileInfo.setNewFileName(fileInfo.getOldFileName());
            return path;
        }
        File oldFile = new File(fileInfo.getFilePath());
        String newPath = fileInfo.getNewFileName().replace(fileInfo.getOldFileName(), fileInfo.getNewFileName());
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);
        return Path.of(newFile.getAbsolutePath());
    }
}
