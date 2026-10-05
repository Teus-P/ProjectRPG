package com.teus.projectrpg.backup.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class GoogleDriveOAuthService {

    private final GoogleAuthorizationCodeFlow flow;

    @Value("${google.oauth.redirect-uri}")
    private String redirectUri;

    public GoogleDriveOAuthService(
            @Value("${google.oauth.credentials-path}") String credentialsPath,
            @Value("${google.oauth.tokens-directory}") String tokensDirectory
    ) throws Exception {
        var transport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        var clientSecrets = GoogleClientSecrets
                .load(
                        jsonFactory,
                        new FileReader(credentialsPath)
                );

        this.flow = new GoogleAuthorizationCodeFlow.Builder(
                transport,
                jsonFactory,
                clientSecrets,
                List.of(DriveScopes.DRIVE)
        )
                .setDataStoreFactory(
                        new FileDataStoreFactory(new File(tokensDirectory))
                )
                .setAccessType("offline")
                .build();
    }

    public String getAuthorizationUrl() {
        return flow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .setAccessType("offline")
                .build();
    }

    public void authorize(String code) throws IOException {
        GoogleTokenResponse response = flow
                .newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();

        flow.createAndStoreCredential(response, "user");
        System.out.println("Authorization successful.");
    }

    public Credential getCredential() throws IOException {
        return flow.loadCredential("user");
    }
}
