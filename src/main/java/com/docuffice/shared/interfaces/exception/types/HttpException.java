package com.docuffice.shared.interfaces.exception.types;

import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
    private String error;

    public HttpException(String message) {
        super(message);
    }

    public HttpException() {
        super();
    }
}