package com.annluciole.filesorter.repository;

import com.annluciole.filesorter.entity.FileInfo;

/**
 * Репозиторий сущности FileInfo
 */
public interface FileInfoRepository {

    /**
     * Сохраняет сущность
     *
     * @param fileInfo сохраняемая сущность
     */
    void save(FileInfo fileInfo);

    /**
     * Возвращает сущность по пути
     *
     * @param path путь к сущности
     * @return сущность
     */
    FileInfo findByPath(String path);
}
