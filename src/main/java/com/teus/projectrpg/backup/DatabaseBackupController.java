package com.teus.projectrpg.backup;

import com.teus.projectrpg.backup.service.DatabaseBackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseBackupController {
    private final DatabaseBackupService databaseBackupService;

    @PostMapping("/databaseBackup")
    public ResponseEntity<String> createDatabaseBackup() {
        databaseBackupService.createBackup();
        return ResponseEntity.ok("Backup successful.");
    }
}
