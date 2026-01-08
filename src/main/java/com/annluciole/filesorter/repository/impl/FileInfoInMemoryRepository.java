package com.annluciole.filesorter.repository.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.repository.FileInfoRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileInfoInMemoryRepository implements FileInfoRepository {

    private final Map<String, FileInfo> repository = new HashMap<>();

    @Override
    public void save(FileInfo fileInfo) {
        repository.put(fileInfo.getFilePath(), fileInfo);
    }

    @Override
    public FileInfo findByPath(String path) {
        return repository.get(path);
    }
}
