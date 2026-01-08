package com.annluciole.filesorter.utils;

import java.nio.file.Path;

public class FileExtensionUtils {

    public static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
