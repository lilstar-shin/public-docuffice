package com.docuffice.shared.interfaces.exception.handler;

import com.docuffice.shared.interfaces.exception.types.HttpErrorResponse;
import com.docuffice.shared.interfaces.exception.types.HttpException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof HttpException) {
            HttpException httpException = (HttpException) exception;
            log.error("HttpException: {}", httpException.getMessage(), exception);
            return Response.status(httpException.getStatus())
                .entity(new HttpErrorResponse(
                    httpException.getStatus(),
                    httpException.getMessage(),
                    httpException.getError()
                ))
                .build();
        } else {
            log.error("Unhandled exception: {}", exception.getMessage(), exception);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new HttpErrorResponse(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Internal Server Error",
                    "An unexpected error occurred"
                ))
                .build();
        }
    }
}