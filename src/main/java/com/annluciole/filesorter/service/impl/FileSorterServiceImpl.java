package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FileSorterService;
import com.annluciole.filesorter.service.generator.filepath.FilePathGenerator;
import com.annluciole.filesorter.service.handler.format.ArchiveHandler;
import com.annluciole.filesorter.service.metadata.MetadataReader;
import com.annluciole.filesorter.service.metadata.MetadataReaderRepository;
import com.annluciole.filesorter.utils.FileExtensionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class FileSorterServiceImpl implements FileSorterService {

    private final ArchiveHandler archiveHandler;
    private final MetadataReaderRepository metadataReaderRepository;
    private final FilePathGenerator filePathGenerator;

    public FileSorterServiceImpl(ArchiveHandler archiveHandler,
                                 MetadataReaderRepository metadataReaderRepository,
                                 FilePathGenerator filePathGenerator) {
        this.archiveHandler = archiveHandler;
        this.metadataReaderRepository = metadataReaderRepository;
        this.filePathGenerator = filePathGenerator;
    }

    public void sortFilesByPath(Path filesPath) {
        try (Stream<Path> pathStream = Files.walk(filesPath)) {
            pathStream.filter(Files::isRegularFile)
                    .map(archiveHandler::unpackArchive)
                    .flatMap(Collection::stream)
                    .forEach(path -> {
                        String extension = FileExtensionUtils.getFileExtension(path);
                        MetadataReader metadataReader = metadataReaderRepository
                                .getMetadataReader(extension);
                        metadataReader.readAndSaveFileMetadata(path, extension);
                        Path destinationPath = filePathGenerator.generateDestinationPath(path);
                        try {
                            if (destinationPath != null) {
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
