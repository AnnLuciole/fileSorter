package com.annluciole.filesorter.service.handler.creationdate;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreationDateHandlerRepository {

    private final Map<String, CreationDateHandler> handlers = new HashMap<>();

    public CreationDateHandlerRepository(List<CreationDateHandler> creationDateHandlers) {
        creationDateHandlers.forEach(handler -> handlers.put(handler.getFileType(), handler));
    }

    public CreationDateHandler getCreationDateHandler(String fileType) {
        return handlers.getOrDefault(fileType, handlers.get(null));
    }
}
