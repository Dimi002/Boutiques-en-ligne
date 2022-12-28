package com.dimsoft.clinicStackProd.response;

public class RolePermissionMin {
    private int roleId;
    private int permissionId;

    public RolePermissionMin() {
    }

    public RolePermissionMin(int permissionId, int roleId) {
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
