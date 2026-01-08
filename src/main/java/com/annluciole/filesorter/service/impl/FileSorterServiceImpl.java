package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.*;
import com.annluciole.filesorter.service.generator.filename.FileNameGeneratorRepository;
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

    private final FileNameGeneratorRepository fileNameGeneratorRepository;
    private final ArchiveHandler archiveHandler;
    private final MetadataReaderRepository metadataReaderRepository;
    private final FileRenameService fileRenameService;
    private final FilePathGenerator filePathGenerator;

    public FileSorterServiceImpl(FileNameGeneratorRepository fileNameGeneratorRepository,
                                 ArchiveHandler archiveHandler,
                                 MetadataReaderRepository metadataReaderRepository,
                                 FileRenameService fileRenameService,
                                 FilePathGenerator filePathGenerator) {
        this.fileNameGeneratorRepository = fileNameGeneratorRepository;
        this.archiveHandler = archiveHandler;
        this.metadataReaderRepository = metadataReaderRepository;
        this.fileRenameService = fileRenameService;
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
                        metadataReader.readAndSaveFileMetadata(path);
                        fileNameGeneratorRepository.getFileNameGenerator(extension).generateFileName(path);
                        Path pathToRenamedFile = fileRenameService.renameFile(path);
                        Path destinationPath = filePathGenerator.generateDestinationPath(path);
                        try {
                            if (destinationPath != null) {
                                Files.move(pathToRenamedFile, destinationPath);
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
