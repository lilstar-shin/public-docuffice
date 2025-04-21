package com.docuffice.user.interfaces.exception;

import com.docuffice.shared.interfaces.exception.types.HttpException;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.io.Serial;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OAuthException extends HttpException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OAuthException(String message) {
        super.setStatus(HttpResponseStatus.UNAUTHORIZED.code());
        super.setMessage(message);
        super.setError("Unauthorized");
    }
}
