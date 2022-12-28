package com.dimsoft.clinicStackProd.rbac.config;


public class JwtProperties {
    public static final String SECRET = "clinic-SecretForJWTGeneration";
    public static final int EXPIRATION_TIME = 86_400_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}