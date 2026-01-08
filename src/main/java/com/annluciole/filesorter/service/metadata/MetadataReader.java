package com.annluciole.filesorter.service.metadata;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandler;
import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandlerRepository;
import com.annluciole.filesorter.repository.FileInfoRepository;

import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public abstract class MetadataReader {

    protected final FileInfoRepository fileInfoRepository;
    protected final CreationDateHandlerRepository repository;
    private final String fileExtension;

    public MetadataReader(FileInfoRepository fileInfoRepository,
                          CreationDateHandlerRepository repository,
                          String fileExtension) {
        this.fileInfoRepository = fileInfoRepository;
        this.repository = repository;
        this.fileExtension = fileExtension;
    }

    public final String getFileExtension() {
        return fileExtension;
    }

    public abstract void readAndSaveFileMetadata(Path path);

    protected String getExtensionForFile(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    protected FileInfo defaultReadAndSaveFileMetadata(Path path) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setOldFileName(path.getFileName().toString());
        fileInfo.setFilePath(path.toString());
        fileInfo.setExtension(getExtensionForFile(fileInfo.getOldFileName()));
        CreationDateHandler handler = repository.getCreationDateHandler(fileInfo.getExtension());
        ZonedDateTime creationDate = ZonedDateTime.ofInstant(handler.getCreationDate(path),
                ZoneId.systemDefault());
        fileInfo.setCreatedDate(creationDate);
        fileInfo.setMetadata(new HashMap<>());
        fileInfoRepository.save(fileInfo);
        return fileInfo;
    }
}
