package com.teus.projectrpg.backup;

import com.teus.projectrpg.backup.service.GoogleDriveOAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
public class GoogleDriveOAuthController {

    private final GoogleDriveOAuthService googleDriveOAuthService;

    public GoogleDriveOAuthController(GoogleDriveOAuthService googleDriveOAuthService) {
        this.googleDriveOAuthService = googleDriveOAuthService;
    }

    @GetMapping("/api/google-drive/oauth2/authorize")
    public ResponseEntity<Void> authorize() {
        String authorizationUrl = googleDriveOAuthService.getAuthorizationUrl();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(authorizationUrl))
                .build();
    }

    @GetMapping("/api/google-drive/oauth2/callback")
    public String callback(@RequestParam String code) throws IOException {
        googleDriveOAuthService.authorize(code);

        return "Google Drive authorization successful.";
    }
}
