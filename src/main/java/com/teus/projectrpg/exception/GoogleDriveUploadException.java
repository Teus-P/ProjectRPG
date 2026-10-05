package com.teus.projectrpg.exception;

public class GoogleDriveUploadException extends RuntimeException {
    public GoogleDriveUploadException(String message) {
        super(message);
    }

    public GoogleDriveUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
