package com.annluciole.filesorter.config;

import com.annluciole.filesorter.enums.RenameStrategy;
import com.annluciole.filesorter.enums.SortStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "filesorter")
public class ApplicationProperties {

    private SortStrategy sortStrategy;
    private RenameStrategy renameStrategy;

    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public RenameStrategy getRenameStrategy() {
        return renameStrategy;
    }

    public void setRenameStrategy(RenameStrategy renameStrategy) {
        this.renameStrategy = renameStrategy;
    }
}
