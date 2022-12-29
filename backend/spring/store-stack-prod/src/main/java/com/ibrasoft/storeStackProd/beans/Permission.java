package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "permission_name" }) })
@XmlRootElement
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "permission_id", nullable = false)
    private Integer permissionId;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;
    @Column(name = "permission_name", length = 255)
    private String permissionName;
    @Column(name = "permission_desc", length = 500)
    private String permissionDesc;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;
    @OneToMany(mappedBy = "rolePermissionId.permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<RolePermission> rolePermissionList;

    public Permission() {
    }

    public Permission(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Permission(Date createdOn, String permissionName) {
        this.createdOn = createdOn;
        this.permissionName = permissionName;
    }

    public Permission(Date createdOn, String permissionName, String permissionDesc) {
        this.createdOn = createdOn;
        this.permissionName = permissionName;
        this.permissionDesc = permissionDesc;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @XmlTransient
    public List<RolePermission> getRolePermissionList() {
        return rolePermissionList;
    }

    public void setRolePermissionList(List<RolePermission> rolePermissionList) {
        this.rolePermissionList = rolePermissionList;
    }
}
