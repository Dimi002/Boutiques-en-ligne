package com.dimsoft.clinicStackProd.beans;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "app_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_name" }),
        @UniqueConstraint(columnNames = { "email" })
})
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Column(name = "clearPassword", nullable = true, length = 255)
    private String clearPassword;
    @Basic(optional = false)
    @Column(name = "user_name", nullable = false, length = 255)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "comment", length = 500)
    private String comment;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic(optional = true)
    @Column(name = "user_image_path", length = 255, nullable = true)
    private String userImagePath;
    @Column(name = "status")
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    @OneToMany(mappedBy = "userRoleId.user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<UserRole> usersRolesList;
    @Transient
    private List<String> roleList;
    @Transient
    private List<String> permissionList;
    @Transient
    private Specialist specialist;

    public User() {
    }

    public User(Integer userId) {
        this.id = userId;
    }

    public User(String password, String username, String email, Date createdOn) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
    }

    public User(String password, String username, String email, String firstName, String lastName, Date createdOn,
            String clearPassword, String phone) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clearPassword = clearPassword;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @XmlTransient
    public List<UserRole> getUsersRolesList() {
        return usersRolesList;
    }

    public void setUsersRolesList(List<UserRole> usersRolesList) {
        this.usersRolesList = usersRolesList;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    @JsonIgnore
    public List<String> getUserRoleList() {
        if (roleList == null)
            roleList = new ArrayList<String>();
        usersRolesList.forEach(userRole -> {
            if (!roleList.contains(userRole.getRole().getRoleName())) {
                roleList.add(userRole.getRole().getRoleName());
            }
        });
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    @JsonIgnore
    public List<String> getUserPermissionList() {
        if (permissionList == null)
            permissionList = new ArrayList<String>();
        usersRolesList.forEach(userRole -> {
            userRole.getRole().getRolePermissionList().forEach(rolePermission -> {
                if (!permissionList.contains(rolePermission.getPermission().getPermissionName())) {
                    permissionList.add(rolePermission.getPermission().getPermissionName());
                }
            });
        });
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public String getClearPassword() {
        return clearPassword;
    }

    public void setClearPassword(String clearPassword) {
        this.clearPassword = clearPassword;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

}
