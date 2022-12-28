package com.dimsoft.clinicStackProd.beans;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "role_permission")
@AssociationOverrides({
        @AssociationOverride(name = "rolePermissionId.role", joinColumns = @JoinColumn(name = "role_id")),
        @AssociationOverride(name = "rolePermissionId.permission", joinColumns = @JoinColumn(name = "permission_id")) })
public class RolePermission {
    @EmbeddedId
    private RolePermissionId rolePermissionId = new RolePermissionId();
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    public RolePermission() {
    }

    public RolePermission(RolePermissionId rolePermissionId) {
        this.createdOn = new Date();
        this.rolePermissionId = rolePermissionId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @XmlTransient
    @JsonProperty(access = Access.WRITE_ONLY)
    public Role getRole() {
        return this.getRolePermissionId().getRole();
    }

    @XmlTransient
    @JsonProperty(access = Access.WRITE_ONLY)
    public Permission getPermission() {
        return this.getRolePermissionId().getPermission();
    }

    public RolePermissionId getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(RolePermissionId rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastUpdateOn() {
        return lastUpdateOn;
    }

    public void setLastUpdateOn(Date lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;
    }
}
