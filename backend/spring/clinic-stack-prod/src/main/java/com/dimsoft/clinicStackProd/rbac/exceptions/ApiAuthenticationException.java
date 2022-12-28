package com.dimsoft.clinicStackProd.rbac.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class ApiAuthenticationException extends AuthenticationException {
    private HttpStatus httpStatusForResponse = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApiAuthenticationException(String message, HttpStatus httpStatusForResponse) {
        super(message);
        this.httpStatusForResponse = httpStatusForResponse;
    }

    public ApiAuthenticationException(String message, Throwable cause, HttpStatus httpStatusForResponse) {
        super(message, cause);
        this.httpStatusForResponse = httpStatusForResponse;
    }

    public HttpStatus getHttpStatusForResponse() {
        return httpStatusForResponse;
    }

    public void setHttpStatusForResponse(HttpStatus httpStatusForResponse) {
        this.httpStatusForResponse = httpStatusForResponse;
    }
}
