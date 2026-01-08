package com.annluciole.filesorter.service.metadata.impl;

import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandlerRepository;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.metadata.MetadataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class DefaultMetadataReaderImpl extends MetadataReader {

    @Autowired
    public DefaultMetadataReaderImpl(FileInfoRepository fileInfoRepository,
                                     CreationDateHandlerRepository repository) {
        super(fileInfoRepository, repository, null);
    }

    @Override
    public void readAndSaveFileMetadata(Path path) {
        defaultReadAndSaveFileMetadata(path);
    }
}
