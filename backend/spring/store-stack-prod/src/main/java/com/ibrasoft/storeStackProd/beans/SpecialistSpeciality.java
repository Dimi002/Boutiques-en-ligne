package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;
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
@Table(name = "specialist_speciality")
@AssociationOverrides({
        @AssociationOverride(name = "specialistSpecialityId.speciality", joinColumns = @JoinColumn(name = "speciality_id")),
        @AssociationOverride(name = "specialistSpecialityId.specialist", joinColumns = @JoinColumn(name = "specialist_id")) })

public class SpecialistSpeciality implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId();
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    public SpecialistSpeciality() {
    }

    public SpecialistSpeciality(SpecialistSpecialityId specialistSpecialityId) {
        this.specialistSpecialityId = specialistSpecialityId;
    }

    public SpecialistSpecialityId getSpecialistSpecialityId() {
        return specialistSpecialityId;
    }

    public void setSpecialistSpecialityId(SpecialistSpecialityId specialistSpecialityId) {
        this.specialistSpecialityId = specialistSpecialityId;
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

    @Transient
    public Specialist getSpecialist() {
        return getSpecialistSpecialityId().getSpecialist();
    }

    @Transient
    public Speciality getSpeciality() {
        return getSpecialistSpecialityId().getSpeciality();
    }
}
