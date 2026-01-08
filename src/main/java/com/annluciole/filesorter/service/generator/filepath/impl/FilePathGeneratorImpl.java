package com.annluciole.filesorter.service.generator.filepath.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.FileComparator;
import com.annluciole.filesorter.service.generator.filepath.CommonPathGenerator;
import com.annluciole.filesorter.service.generator.filepath.CommonPathGeneratorRepository;
import com.annluciole.filesorter.service.generator.filepath.FilePathGenerator;
import com.annluciole.filesorter.service.handler.creationdate.CreationDateHandlerRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FilePathGeneratorImpl implements FilePathGenerator {

    protected final CreationDateHandlerRepository repository;
    protected final FileInfoRepository fileInfoRepository;
    private final FileComparator fileComparator;
    private final CommonPathGeneratorRepository commonPathGeneratorRepository;

    public FilePathGeneratorImpl(CreationDateHandlerRepository repository,
                                 FileInfoRepository fileInfoRepository,
                                 FileComparator fileComparator,
                                 CommonPathGeneratorRepository commonPathGeneratorRepository) {
        this.repository = repository;
        this.fileInfoRepository = fileInfoRepository;
        this.fileComparator = fileComparator;
        this.commonPathGeneratorRepository = commonPathGeneratorRepository;
    }

    public Path generateDestinationPath(Path path) {
        FileInfo fileInfo = fileInfoRepository.findByPath(path.toString());
        CommonPathGenerator commonPathGenerator =
                commonPathGeneratorRepository.getGenerator(fileInfo.getExtension());
        StringBuilder commonPath = commonPathGenerator.generateCommonPath(fileInfo);
        if (commonPath != null) {
            createDirectories(commonPath);
            commonPath.append(fileInfo.getNewFileName());
            Path destinationPath = Paths.get(commonPath.toString());
            if (Files.exists(destinationPath)) {
                if (fileComparator.compareFiles(path, destinationPath)) {
                    return null;
                }
                int counter = 1;
                while (Files.exists(destinationPath)) {
                    destinationPath = getNewPath(destinationPath, counter);
                    counter++;
                }
            }
            return destinationPath;
        }
        return null;
    }

    protected void createDirectories(StringBuilder commonPath) {
        try {
            Files.createDirectories(Paths.get(commonPath.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getNewPath(Path destinationPath, int counter) {
        StringBuilder newDstPath = new StringBuilder(destinationPath.toString());
        if (destinationPath.toString().contains(")")) {
            int openBracketIdx = destinationPath.toString().lastIndexOf("(");
            int closeBracketIdx = destinationPath.toString().lastIndexOf(")");
            newDstPath.replace(openBracketIdx + 1, closeBracketIdx, String.valueOf(counter));
        } else {
            int pointIdx = destinationPath.toString().lastIndexOf(".");
            newDstPath.insert(pointIdx, " ()");
            int closeBracketIdx = newDstPath.toString().lastIndexOf(")");
            newDstPath.insert(closeBracketIdx, counter);
        }
        destinationPath = Path.of(newDstPath.toString());
        return destinationPath;
    }
}
