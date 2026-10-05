package com.teus.projectrpg.exception;

public class DatabaseBackupException extends RuntimeException {
    public DatabaseBackupException(String message) {
        super(message);
    }

    public DatabaseBackupException(String message, Throwable cause) {
        super(message, cause);
    }
}
