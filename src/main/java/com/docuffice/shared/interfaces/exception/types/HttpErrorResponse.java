package com.docuffice.shared.interfaces.exception.types;

import lombok.Data;

@Data
public class HttpErrorResponse {

    private int status;
    private String message;
    private String error;
    private Object details;

    public HttpErrorResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
