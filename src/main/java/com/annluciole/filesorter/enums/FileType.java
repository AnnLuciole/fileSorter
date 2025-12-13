package com.annluciole.filesorter.enums;

import java.util.List;

public enum FileType {

    PHOTO("Фото", List.of("jpg", "jpeg", "gif")),
    VIDEO("Видео", List.of("mp4", "avi"));

    private final String directoryName;
    private final List<String> extTypes;

    FileType(String directoryName,
             List<String> extTypes) {
        this.directoryName = directoryName;
        this.extTypes = extTypes;
    }

    public List<String> getExtTypes() {
        return extTypes;
    }

    public String getDirectoryName() {
        return directoryName;
    }
}
