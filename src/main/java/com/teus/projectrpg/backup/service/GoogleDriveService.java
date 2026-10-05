package com.teus.projectrpg.backup.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.teus.projectrpg.exception.GoogleDriveUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GoogleDriveService {

    private final GoogleDriveOAuthService googleDriveOAuthService;

    @Value("${google.drive.folder-id}")
    private String folderId;

    private GoogleDriveService(GoogleDriveOAuthService googleDriveOAuthService) {
        this.googleDriveOAuthService = googleDriveOAuthService;
    }

    public void uploadFile(File file) {
        try {
            Drive driveService = createDriveService();

            com.google.api.services.drive.model.File metadata =
                    new com.google.api.services.drive.model.File();

            metadata.setName(file.getName());
            metadata.setParents(List.of(folderId));

            FileContent content = new FileContent("application/sql", file);

            var uploadedFile = driveService.files()
                    .create(metadata, content)
                    .setSupportsAllDrives(true)
                    .setFields("id, name")
                    .execute();

            System.out.println("Backup uploaded to Google Drive: " + uploadedFile.getName());

        } catch (GoogleJsonResponseException e) {
            throw new GoogleDriveUploadException(
                    "Google Drive upload failed. HTTP "
                            + e.getStatusCode()
                            + ": "
                            + e.getStatusMessage(),
                    e
            );
        } catch (IOException e) {
            throw new GoogleDriveUploadException(
                    "Could not upload backup to Google Drive.",
                    e
            );
        }
    }

    private Drive createDriveService() throws IOException {
        try {
            Credential credential = googleDriveOAuthService.getCredential();

            return new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
            )
                    .setApplicationName("ProjectRPG")
                    .build();
        } catch (Exception e) {
            throw new IOException("Could not create Google Drive service", e);
        }
    }
}
