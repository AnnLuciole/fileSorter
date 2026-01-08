package com.annluciole.filesorter.service.handler.format.impl;

import com.annluciole.filesorter.service.handler.format.ArchiveHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ArchiveHandlerImpl implements ArchiveHandler {

    @Override
    public List<Path> unpackArchive(Path root) {
        if (!isArchive(root.toString())) {
            return List.of(root);
        }
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(root.toString()))) {
            List<Path> paths = new ArrayList<>();
            String destDir = root.toString().replace(root.getFileName().toString(), "");
            ZipEntry zipEntry = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir + File.separator + fileName);
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            return paths;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isArchive(String root) {
        return root.endsWith("rar")
                || root.endsWith("zip")
                || root.endsWith("gz")
                || root.endsWith("7z");
    }
}
