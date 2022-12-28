package com.dimsoft.clinicStackProd.exceptions;

import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.ErrorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@dimsoft.eu
 */
@ControllerAdvice
public class ExceptionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex) {
        logger.error("Invalid Input Exception: ", ex.getMessage());
        ErrorObject errorObject = new ErrorObject(Integer.parseInt(Constants.SERVER_ERROR), ex.getMessage());
        return new ResponseEntity<Object>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { UnauthorizedException.class })
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        logger.error("Unauthorized Exception: ", ex.getMessage());
        ErrorObject errorObject = new ErrorObject(Integer.parseInt(Constants.USER_NON_AUTHENTICATED), ex.getMessage());
        return new ResponseEntity<Object>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ApiException.class })
    public ResponseEntity<?> handleApiException(ApiException ex) {
        logger.error("ApiException Exception: ", ex.getMessage());
        ErrorObject errorObject = new ErrorObject(ex.getCode(), ex.getMessage(), ex.getStatus());
        return new ResponseEntity<Object>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace();
        logger.error("Exception: ", ex.getMessage());
        ErrorObject errorObject = new ErrorObject(Integer.parseInt(Constants.SERVER_ERROR), ex.getMessage());
        return new ResponseEntity<Object>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { ClinicException.class })
    public ResponseEntity<?> handleClinicException(ClinicException ex) {
        logger.error("ClinicException Exception: ", ex.getMessage());
        ErrorObject errorObject = new ErrorObject(ex.getCode(), ex.getMessage());
        return new ResponseEntity<Object>(errorObject, HttpStatus.BAD_REQUEST);
    }
}
