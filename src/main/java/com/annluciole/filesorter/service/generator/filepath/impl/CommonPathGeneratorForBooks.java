package com.annluciole.filesorter.service.generator.filepath.impl;

import com.annluciole.filesorter.entity.FileInfo;
import com.annluciole.filesorter.service.generator.filepath.CommonPathGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonPathGeneratorForBooks extends CommonPathGenerator {

    public CommonPathGeneratorForBooks(@Value("${destination.file.path}") String destinationFilesPath) {
        super(destinationFilesPath, "fb2");
    }

    @Override
    public StringBuilder generateCommonPath(FileInfo fileInfo) {
        StringBuilder commonPath = super.generateCommonPath(fileInfo);
        commonPath.append("/");
        commonPath.append(fileInfo.getMetadata().get("genre"));
        commonPath.append("/");
        return commonPath;
    }
}
