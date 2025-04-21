package com.docuffice.auth.interfaces.exception;

import com.docuffice.shared.interfaces.exception.types.HttpException;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class OAuthUnauthorizedException extends HttpException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public OAuthUnauthorizedException(String message) {
        super.setStatus(401);
        super.setMessage(message);
        super.setError("Unauthorized");
    }
}
