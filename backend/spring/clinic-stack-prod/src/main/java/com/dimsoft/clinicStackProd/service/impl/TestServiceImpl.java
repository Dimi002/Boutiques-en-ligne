package com.dimsoft.clinicStackProd.service.impl;

import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.service.TestService;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getString() {
        return "Hello";
    }
    
}
