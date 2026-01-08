package com.annluciole.filesorter.service.generator.filename.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.generator.filename.FileNameGenerator;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class BookFileNameGeneratorImpl extends FileNameGenerator {

    private static final String EXTENSION = "fb2";

    public BookFileNameGeneratorImpl(FileInfoRepository fileInfoRepository) {
        super(fileInfoRepository, EXTENSION);
    }

    @Override
    public void generateFileName(Path path) {
        FileInfo fileInfo = repository.findByPath(path.toString());
        StringBuilder newFileName = new StringBuilder();
        newFileName.append(fileInfo.getMetadata().get("book-title").toString());
        newFileName.append(" - ");
        newFileName.append(String.join(", ", ((List<String>) fileInfo.getMetadata().get("authors"))));
        newFileName.append(".");
        newFileName.append(fileInfo.getExtension());
        fileInfo.setNewFileName(newFileName.toString());
    }
}
