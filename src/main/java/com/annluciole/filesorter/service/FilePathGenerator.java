package com.annluciole.filesorter.service;

import com.annluciole.filesorter.enums.FileType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Сервис для генерации пути к сортируемым файлам
 */
public abstract class FilePathGenerator {

    protected final String destinationFilesPath;
    protected final CreationDateHandlerRepository repository;
    protected final FileNameGenerator fileNameGenerator;

    public FilePathGenerator(String destinationFilesPath,
                             CreationDateHandlerRepository repository, FileNameGenerator fileNameGenerator) {
        this.destinationFilesPath = destinationFilesPath;
        this.repository = repository;
        this.fileNameGenerator = fileNameGenerator;
    }

    /**
     * Генерирует конечный путь для сортируемого файла
     *
     * @param path исходный путь к файлу
     * @return путь к отсортированному файлу
     */
    public Path generateDestinationPath(Path path) {
        StringBuilder commonPath = generateCommonPath(path);
        if (commonPath != null) {
            String extension = fileNameGenerator.getFileExtension(path.getFileName().toString());
            CreationDateHandler handler = repository.getCreationDateHandler(extension);
            ZonedDateTime creationDate = ZonedDateTime.ofInstant(handler.getCreationDate(path),
                    ZoneId.systemDefault());
            generatePathByStrategy(creationDate, commonPath);
            createDirectories(commonPath);
            String newFileName = fileNameGenerator.generateFileName(path, creationDate);
            commonPath.append(newFileName);
            return Paths.get(commonPath.toString());
        }
        return null;
    }

    /**
     * Генерирует часть пути к сортируемому файлу, которая зависит от стратегии сортировки
     *
     * @param creationDate дата создания исходного файла
     * @param commonPath сгенерированный общий путь к отсортированному файлу
     */
    protected abstract void generatePathByStrategy(ZonedDateTime creationDate, StringBuilder commonPath);

    protected StringBuilder generateCommonPath(Path path) {
        String extension = fileNameGenerator.getFileExtension(path.getFileName().toString());
        String directoryName = getDirectoryName(extension);
        if (directoryName != null) {
            StringBuilder pathBuilder = new StringBuilder(destinationFilesPath);
            if (!destinationFilesPath.endsWith("/")) {
                pathBuilder.append("/");
            }
            pathBuilder.append(getDirectoryName(extension));
            pathBuilder.append("/");
            return pathBuilder;
        }
        return null;
    }

    protected String getDirectoryName(String extension) {
        FileType fileType = Arrays.stream(FileType.values())
                .filter(type -> type.getExtTypes().contains(extension))
                .findFirst()
                .orElse(null);
        if (fileType != null) {
            return fileType
                    .getDirectoryName();
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
}
