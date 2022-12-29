package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RolePermissionId implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;
    @ManyToOne(cascade = CascadeType.ALL)
    private Permission permission;

    public RolePermissionId() {
    }

    public RolePermissionId(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
