package com.teus.projectrpg.backup.service;

import com.teus.projectrpg.exception.DatabaseBackupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DatabaseBackupService {

    private final GoogleDriveService googleDriveService;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.backup.directory}")
    private String backupDirectory;

    public DatabaseBackupService(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    public void createBackup() {
        File directory;
        File backup;
        File errorFile;

        try {
            directory = createBackupDirectory();
            backup = createBackupFile(directory);
            errorFile = File.createTempFile("mysqldump-", ".err", directory);
        } catch (IOException ex) {
            throw new DatabaseBackupException(
                    "Could not prepare database backup.",
                    ex
            );
        }

        Process process = null;

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "mysqldump",
                    "-u" + username,
                    "--no-tablespaces",
                    databaseName
            );

            builder.environment().put("MYSQL_PWD", password);
            builder.redirectOutput(backup);
            builder.redirectError(errorFile);

            process = builder.start();

            int exitCode = process.waitFor();

            String errorOutput = Files.readString(
                    errorFile.toPath(),
                    StandardCharsets.UTF_8
            );

            if (exitCode != 0) {
                deleteFile(backup);

                throw new DatabaseBackupException(
                        "Database backup failed. Exit code: "
                                + exitCode
                                + ". Error message: "
                                + errorOutput
                );
            }

            if (!backup.exists() || backup.length() == 0) {
                deleteFile(backup);

                throw new DatabaseBackupException(
                        "Database backup file is empty."
                );
            }

        } catch (InterruptedException ex) {
            if (process != null) {
                process.destroyForcibly();
            }

            deleteFile(backup);
            Thread.currentThread().interrupt();

            throw new DatabaseBackupException(
                    "Database backup was interrupted.",
                    ex
            );

        } catch (IOException ex) {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }

            deleteFile(backup);

            throw new DatabaseBackupException(
                    "Could not create database backup.",
                    ex
            );

        } finally {
            deleteFile(errorFile);
        }

        googleDriveService.uploadFile(backup);
    }

    private File createBackupDirectory() throws IOException {
        File directory = new File(backupDirectory);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Could not create backup directory: " + backupDirectory);
        }

        if (!directory.isDirectory()) {
            throw new IOException(
                    "Backup path is not a directory: " + backupDirectory
            );
        }
        return directory;
    }

    private File createBackupFile(File directory) {
        String fileName = "backup_" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                + ".sql";

        return new File(directory, fileName);
    }

    private void deleteFile(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException ex) {
            System.err.println(
                    "Could not delete file: " + file.getAbsolutePath()
                            + ". Reason: " + ex.getMessage()
            );
        }
    }
}
