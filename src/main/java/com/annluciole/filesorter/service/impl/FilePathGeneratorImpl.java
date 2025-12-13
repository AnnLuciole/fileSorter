package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.enums.FileType;
import com.annluciole.filesorter.service.FilePathGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class FilePathGeneratorImpl implements FilePathGenerator {

    private final String destinationFilesPath;

    public FilePathGeneratorImpl(@Value("{$destination.file.path}") String destinationFilesPath) {
        this.destinationFilesPath = destinationFilesPath;
    }

    public Path generateDestinationPath(Path path) {
        String fileName = path.getFileName().toString();
        String extension = getFileExtension(fileName);
        StringBuilder pathBuilder = new StringBuilder(destinationFilesPath);
        if (!destinationFilesPath.endsWith("/")) {
            pathBuilder.append("/");
        }
        pathBuilder.append(getDirectoryName(extension));
        pathBuilder.append("/");
        try {
            FileTime fileTime = Files.readAttributes(path, BasicFileAttributes.class).creationTime();
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
            String year = String.valueOf(zonedDateTime.getYear());
            pathBuilder.append(year);
            pathBuilder.append("/");
            String month = String.valueOf(zonedDateTime.getMonthValue());
            pathBuilder.append(month);
            pathBuilder.append("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pathBuilder.append(fileName);
        return Paths.get(pathBuilder.toString());
    }

    private static String getDirectoryName(String extension) {
        return Arrays.stream(FileType.values())
                .filter(type -> type.getExtTypes().contains(extension))
                .findFirst()
                .orElseThrow()
                .getDirectoryName();
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
