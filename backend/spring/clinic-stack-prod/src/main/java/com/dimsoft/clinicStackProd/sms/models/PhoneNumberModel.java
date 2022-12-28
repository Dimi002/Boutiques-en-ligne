package com.dimsoft.clinicStackProd.sms.models;

public class PhoneNumberModel {
    private String postalCode;
    private String number;

    public PhoneNumberModel() {
    }

    public PhoneNumberModel(String postalCode, String number) {
        this.postalCode = postalCode;
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
