package com.ibrasoft.storeStackProd.beans;

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
import javax.persistence.Transient;

@Entity
@Table(name = "user_role")
@AssociationOverrides({ @AssociationOverride(name = "userRoleId.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "userRoleId.role", joinColumns = @JoinColumn(name = "role_id")) })
public class UserRole {
    @EmbeddedId
    private UserRoleId userRoleId = new UserRoleId();
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    public UserRole() {
    }

    public UserRole(UserRoleId userRoleId) {
        this.createdOn = new Date();
        this.userRoleId = userRoleId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Transient
    public User getUser() {
        return getUserRoleId().getUser();
    }

    @Transient
    public Role getRole() {
        return getUserRoleId().getRole();
    }

    public UserRoleId getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(UserRoleId userRoleId) {
        this.userRoleId = userRoleId;
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
