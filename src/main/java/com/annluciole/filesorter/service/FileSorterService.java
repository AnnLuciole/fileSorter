package com.annluciole.filesorter.service;

import java.nio.file.Path;

/**
 * Сервис для сортировки файлов
 */
public interface FileSorterService {

    /**
     * Сортирует файлы по заданному пути
     *
     * @param filesPath путь в неотсортированным файлам
     */
    void sortFilesByPath(Path filesPath);
}
