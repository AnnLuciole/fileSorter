package com.annluciole.filesorter.service.generator.filepath;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonPathGeneratorRepository {

    private final Map<String, CommonPathGenerator> generators = new HashMap<>();

    public CommonPathGeneratorRepository(List<CommonPathGenerator> generatorsList) {
        generatorsList.forEach(generator -> generators.put(generator.getExtension(), generator));
    }

    public CommonPathGenerator getGenerator(String extension) {
        return generators.getOrDefault(extension, generators.get(null));
    }
}
