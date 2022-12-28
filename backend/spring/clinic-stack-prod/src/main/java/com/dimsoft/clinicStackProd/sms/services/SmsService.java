package com.dimsoft.clinicStackProd.sms.services;

import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    void sendSms(String phone, String message);
}
