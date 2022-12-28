package com.dimsoft.clinicStackProd.response;

public class UserRoleMin {
    private int userId;
    private int roleId;

    public UserRoleMin() {
    }

    public UserRoleMin(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
