package com.annluciole.filesorter.service.generator.filepath;

import java.nio.file.Path;

/**
 * Сервис для генерации пути к сортируемым файлам
 */
public interface FilePathGenerator {

    /**
     * Генерирует конечный путь для сортируемого файла
     *
     * @param path исходный путь к файлу
     * @return путь к отсортированному файлу
     */
    Path generateDestinationPath(Path path);
}
