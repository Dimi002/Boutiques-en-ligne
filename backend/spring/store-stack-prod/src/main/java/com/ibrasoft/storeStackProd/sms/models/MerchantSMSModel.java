package com.ibrasoft.storeStackProd.sms.models;

import java.util.List;

public class MerchantSMSModel {
    private String merchantKey;
    private List<PhoneNumberModel> contacts;
    private String message;

    public MerchantSMSModel() {
    }

    public MerchantSMSModel(String merchantKey, List<PhoneNumberModel> contacts, String message) {
        this.merchantKey = merchantKey;
        this.contacts = contacts;
        this.message = message;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public List<PhoneNumberModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<PhoneNumberModel> contacts) {
        this.contacts = contacts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
