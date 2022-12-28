package com.dimsoft.clinicStackProd.rbac.exceptions;

import com.dimsoft.clinicStackProd.util.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler(value = { ApiAuthenticationException.class })
    public ResponseEntity<?> handleApiException(ApiAuthenticationException ex) {
        logger.error("ApiAuthenticationException Exception: " + ex.getMessage(), ex);
        ErrorObject errorObject = new ErrorObject(ex.getHttpStatusForResponse().value(), ex.getMessage());
        return new ResponseEntity<Object>(errorObject, ex.getHttpStatusForResponse());
    }
}
