package com.dimsoft.clinicStackProd.rbac.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.util.Constants;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringUser implements UserDetails {
    private User user;

    public SpringUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (this.user.getUserPermissionList() != null) {
            this.user.getPermissionList().forEach(p -> {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + p);
                authorities.add(authority);
            });
        }

        if (this.user.getUserRoleList() != null) {
            this.user.getRoleList().forEach(r -> {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
                authorities.add(authority);
            });
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == Constants.STATE_ACTIVATED;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        return this.isEnabled() && this.isAccountNonExpired() && this.isAccountNonLocked()
                && this.isCredentialsNonExpired();
    }
}
