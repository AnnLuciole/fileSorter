package com.annluciole.filesorter.enums;

/**
 * Стратегия переименования файлов
 */
public enum RenameStrategy {

    /**
     * Файл переименовывается в дату своего создания формата dd.MM.yyyy - HH.mm.ss
     */
    RENAME_TO_CREATION_DATE,

    /**
     * Файл не переименовывается
     */
    NO_RENAME
}
