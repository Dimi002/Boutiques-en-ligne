package com.dimsoft.clinicStackProd.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "speciality", uniqueConstraints = { @UniqueConstraint(columnNames = { "speciality_name" }) })
@XmlRootElement
public class Speciality extends ReachText implements Serializable {
    // appelation du specialiste
    private static final long serialVersionUID = 1L;

    @Column(name = "speciality_name", length = 255, nullable = false)
    private String specialityName;

    @Column(name = "specialist_common_name", length = 255, nullable = false)
    private String specialistCommonName;

    @Column(name = "speciality_desc", length = 500, nullable = true)
    private String specialityDesc;

    @Column(name = "speciality_image_path", length = 255, nullable = false)
    private String specialityImagePath;

    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    @OneToMany(mappedBy = "specialistSpecialityId.speciality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<SpecialistSpeciality> specialistSpecialityList;

    public Speciality() {
    }

    public Speciality(
        String specialityName,
        String specialistCommonName,
        String specialityDesc,
        String longDescription,
        String specialityImagePath,
        short status,
        Date createdOn,
        Date lastUpdateOn,
        List<SpecialistSpeciality> specialistSpecialityList
    ) {
        this.specialityName = specialityName;
        this.specialistCommonName = specialistCommonName;
        this.specialityDesc = specialityDesc;
        this.setLongDescription(longDescription);
        this.specialityImagePath = specialityImagePath;
        this.status = status;
        this.createdOn = createdOn;
        this.lastUpdateOn = lastUpdateOn;
        this.specialistSpecialityList = specialistSpecialityList;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public void setSpecialistCommonName(String specialistCommonName) {
        this.specialistCommonName = specialistCommonName;
    }

    public String getSpecialistCommonName() {
        return this.specialistCommonName;
    }

    public String getSpecialityImagePath() {
        return this.specialityImagePath;
    }

    public void setSpecialityDesc(String specialityDesc) {
        this.specialityDesc = specialityDesc;
    }

    public void setSpecialityImagePath(String specialityImagePath) {
        this.specialityImagePath = specialityImagePath;
    }

    public String getSpecialityDesc() {
        return specialityDesc;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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

    @XmlTransient
    public List<SpecialistSpeciality> getSpecialistSpecialityList() {
        return specialistSpecialityList;
    }

    public void setSpecialistSpecialityList(List<SpecialistSpeciality> specialistSpecialityList) {
        this.specialistSpecialityList = specialistSpecialityList;
    }

}
