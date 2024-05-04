package com.example.oss.api.services.Blob;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class BlobStorageServiceImpl implements BlobStorageService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    private BlobContainerClient getBlobContainerClient() {
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                accountName, accountKey);
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient()
                .getBlobContainerClient(containerName);
    }

    public void uploadBlob(String blobName) {
        BlobClient blobClient = getBlobContainerClient().getBlobClient(blobName);
        blobClient.upload(BinaryData.fromString(blobName), true);
    }

    public boolean checkBlobExists(String blobName) {
        return getBlobContainerClient().getBlobClient(blobName).exists();
    }

    public void deleteBlob(String blobName) {
        BlobClient blobClient = getBlobContainerClient().getBlobClient(blobName);
        if (blobClient.exists()) blobClient.delete();
    }
}
