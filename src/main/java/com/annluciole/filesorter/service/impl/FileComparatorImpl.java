package com.annluciole.filesorter.service.impl;

import com.annluciole.filesorter.service.FileComparator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class FileComparatorImpl implements FileComparator {

    @Override
    public boolean compareFiles(Path firstPath, Path secondPath) {
        try {
            if (compareSize(firstPath, secondPath)) {
                return false;
            }
            if (compareHash(firstPath, secondPath)) {
                return false;
            }
            long mismatch = Files.mismatch(firstPath, secondPath);
            return mismatch == -1L;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String calculateFileHash(Path filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(Files.readAllBytes(filePath));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean compareHash(Path firstPath, Path secondPath) {
        return !calculateFileHash(firstPath).equals(calculateFileHash(secondPath));
    }

    private boolean compareSize(Path firstPath, Path secondPath) throws IOException {
        return Files.size(firstPath) != Files.size(secondPath);
    }
}
