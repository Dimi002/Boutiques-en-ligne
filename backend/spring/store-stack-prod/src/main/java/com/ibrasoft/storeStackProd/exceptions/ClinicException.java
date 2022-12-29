package com.ibrasoft.storeStackProd.exceptions;

public class ClinicException extends Exception {
    private String code;
    private String message;

    public ClinicException(String message) {
        super(message);
    }

    public ClinicException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
