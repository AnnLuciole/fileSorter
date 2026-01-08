package com.annluciole.filesorter.service.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MetadataReaderRepository {

    private final Map<String, MetadataReader> metadataReaders = new HashMap<String, MetadataReader>();

    @Autowired
    public MetadataReaderRepository(List<MetadataReader> readers) {
        readers.forEach(metadataReader ->
                metadataReaders.put(metadataReader.getFileExtension(), metadataReader));
    }

    public MetadataReader getMetadataReader(String fileExtension) {
        return metadataReaders.getOrDefault(fileExtension, metadataReaders.get(null));
    }
}
