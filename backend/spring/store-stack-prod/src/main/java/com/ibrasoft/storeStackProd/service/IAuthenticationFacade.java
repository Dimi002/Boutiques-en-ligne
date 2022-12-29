package com.ibrasoft.storeStackProd.service;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    boolean hasRole(Authentication authentication, String role);
}