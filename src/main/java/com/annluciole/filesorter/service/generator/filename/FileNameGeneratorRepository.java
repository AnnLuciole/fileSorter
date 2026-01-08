package com.annluciole.filesorter.service.generator.filename;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileNameGeneratorRepository {

    private final Map<String, FileNameGenerator> generators = new HashMap<>();

    public FileNameGeneratorRepository(List<FileNameGenerator> generatorsList) {
        generatorsList.forEach(generator -> generators.put(generator.getFileExtension(), generator));
    }

    public FileNameGenerator getFileNameGenerator(String extension) {
        return generators.getOrDefault(extension, generators.get(null));
    }
}
