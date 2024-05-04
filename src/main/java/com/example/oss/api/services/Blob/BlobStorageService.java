package com.example.oss.api.services.Blob;

import org.springframework.stereotype.Component;

@Component
public interface BlobStorageService {
    void uploadBlob(String blobName);

    boolean checkBlobExists(String blobName);

    void deleteBlob(String blobName);
}
