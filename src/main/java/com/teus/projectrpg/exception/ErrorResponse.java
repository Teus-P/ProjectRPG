package com.teus.projectrpg.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private Date date;

    public ErrorResponse(int status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
