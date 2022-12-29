package com.ibrasoft.storeStackProd.service.impl;

import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.service.TestService;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getString() {
        return "Hello";
    }
    
}
