package com.annluciole.filesorter.service.handler.format;

import java.nio.file.Path;
import java.util.List;

/**
 * Сервис, отвечающий за распаковку архива
 */
public interface ArchiveHandler {

    /**
     * Распаковывает архив, возвращая пути к созданным в результате файлам
     *
     * @param root путь к архиву
     * @return пути к созданным файлам
     */
    List<Path> unpackArchive(Path root);
}
