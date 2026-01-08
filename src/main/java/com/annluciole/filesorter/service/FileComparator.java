package com.annluciole.filesorter.service;

import java.nio.file.Path;

public interface FileComparator {

    /**
     * Сравнивает 2 файла с одинаковыми сгенерированными названиями.
     *
     * @param firstPath первый файл
     * @param secondPath второй файл
     * @return флаг, указывающий на то, идентичны ли файлы.
     * True - файлы идентичны, false - нет.
     */
    boolean compareFiles(Path firstPath, Path secondPath);
}
