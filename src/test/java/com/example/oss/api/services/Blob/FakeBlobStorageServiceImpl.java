package com.example.oss.api.services.Blob;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Profile("test")
public class FakeBlobStorageServiceImpl implements BlobStorageService {

    private static final String STORAGE_DIRECTORY = "src/test/resources/test-storage";

    public FakeBlobStorageServiceImpl() {
        try {
            Files.createDirectories(Paths.get(STORAGE_DIRECTORY));
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public void uploadBlob(String blobName) {
        try {
            Path file = Paths.get(STORAGE_DIRECTORY, blobName);
            Files.createFile(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not upload blob", e);
        }
    }

    @Override
    public boolean checkBlobExists(String blobName) {
        Path file = Paths.get(STORAGE_DIRECTORY, blobName);
        return Files.exists(file);
    }

    @Override
    public void deleteBlob(String blobName) {
        try {
            Path file = Paths.get(STORAGE_DIRECTORY, blobName);
            Files.deleteIfExists(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not delete blob", e);
        }
    }
}
