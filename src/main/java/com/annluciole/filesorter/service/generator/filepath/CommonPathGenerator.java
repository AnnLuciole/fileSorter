package com.annluciole.filesorter.service.generator.filepath;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.enums.FileType;

import java.util.Arrays;

public abstract class CommonPathGenerator {

    protected final String destinationFilesPath;
    private final String extension;

    public CommonPathGenerator(String destinationFilesPath,
                               String extension) {
        this.destinationFilesPath = destinationFilesPath;
        this.extension = extension;
    }

    public StringBuilder generateCommonPath(FileInfo fileInfo) {
        String extension = fileInfo.getExtension();
        String directoryName = getDirectoryName(extension);
        if (directoryName != null) {
            StringBuilder pathBuilder = new StringBuilder(destinationFilesPath);
            if (!destinationFilesPath.endsWith("/")) {
                pathBuilder.append("/");
            }
            pathBuilder.append(directoryName);
            pathBuilder.append("/");
            return pathBuilder;
        }
        return null;
    }

    public final String getExtension() {
        return extension;
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
}
