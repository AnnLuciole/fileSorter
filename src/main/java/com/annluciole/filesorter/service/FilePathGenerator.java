package com.annluciole.filesorter.service;

import java.nio.file.Path;

public interface FilePathGenerator {

    Path generateDestinationPath(Path path);
}
