package com.annluciole.filesorter.service.generator.filename.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.repository.FileInfoRepository;
import com.annluciole.filesorter.service.generator.filename.FileNameGenerator;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileNameByCreationDateGenerator extends FileNameGenerator {

    public FileNameByCreationDateGenerator(FileInfoRepository repository) {
        super(repository, null);
    }

    @Override
    public void generateFileName(Path path) {
        FileInfo fileInfo = repository.findByPath(path.toString());
        ZonedDateTime creationDate = fileInfo.getCreatedDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH.mm.ss");
        String newFileName = formatter.format(creationDate) + "." + fileInfo.getExtension();
        fileInfo.setNewFileName(newFileName);
    }
}
