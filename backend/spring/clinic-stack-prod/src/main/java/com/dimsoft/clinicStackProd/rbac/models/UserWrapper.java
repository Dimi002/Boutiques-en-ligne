package com.dimsoft.clinicStackProd.rbac.models;

import java.util.Date;

import com.dimsoft.clinicStackProd.beans.User;

public class UserWrapper {
    private User user;
    private String token;
    private Date tokenExpiresAt;

    public UserWrapper() {
    }

    public UserWrapper(User user, String token, Date tokenExpiresAt) {
        this.user = user;
        this.token = token;
        this.tokenExpiresAt = tokenExpiresAt;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTokenExpiresAt() {
        return tokenExpiresAt;
    }

    public void setTokenExpiresAt(Date tokenExpiresAt) {
        this.tokenExpiresAt = tokenExpiresAt;
    }
}