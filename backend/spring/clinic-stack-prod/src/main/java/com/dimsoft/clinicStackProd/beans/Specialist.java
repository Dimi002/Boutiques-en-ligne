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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "specialist", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id" }) })
@XmlRootElement
public class Specialist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "specialist_id", nullable = false)
    private Integer specialistId;
    @Column(name = "year_of_experience")
    private Integer yearOfExperience;
    @Transient
    @Column(name = "social_media_links")
    private SocialMediaLinks socialMediaLinksObject;
    @Lob
    @Column(name = "social_media_links")
    private String socialMediaLinks;
    @Lob
    @Column(name = "biography")
    private String biography;
    @Column(name = "gender")
    private String gender;
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "status")
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User userId;
    @OneToMany(mappedBy = "specialistSpecialityId.specialist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<SpecialistSpeciality> specialistSpecialityList;

    @OneToMany(mappedBy = "specialistId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Appointment> appointmentsList;

    @Transient
    private List<String> specialitiesList;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Planing> planings;

    public Specialist() {
    }

    /**
     * @return the planings
     */
    public List<Planing> getPlanings() {
        return planings;
    }

    /**
     * @param planings the planings to set
     */
    public void setPlanings(List<Planing> planings) {
        this.planings = planings;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    @XmlTransient
    public List<Appointment> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(List<Appointment> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    public SocialMediaLinks getSocialMediaLinksObject() {
        return socialMediaLinksObject;
    }

    public void setSocialMediaLinksObject(SocialMediaLinks socialMediaLinksObject) {
        this.socialMediaLinksObject = socialMediaLinksObject;
    }

    public String getSocialMediaLinks() {
        return socialMediaLinks;
    }

    public void setSocialMediaLinks(String socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }

    @XmlTransient
    public List<SpecialistSpeciality> getSpecialistSpecialityList() {
        return specialistSpecialityList;
    }

    public void setSpecialistSpecialityList(List<SpecialistSpeciality> specialistSpecialityList) {
        this.specialistSpecialityList = specialistSpecialityList;
    }

    @JsonIgnore
    public List<String> getSpecialistSpecialitiesList() {
        if (specialitiesList == null)
            specialitiesList = new ArrayList<String>();
        specialistSpecialityList.forEach(specialistSpeciality -> {
            if (specialistSpeciality.getSpeciality() != null
                    && !specialitiesList.contains(specialistSpeciality.getSpeciality().getSpecialistCommonName())) {
                specialitiesList.add(specialistSpeciality.getSpeciality().getSpecialistCommonName());
            }
        });
        return specialitiesList;
    }

    public List<String> getSpecialitiesList() {
        return specialitiesList;
    }

    public void setSpecialitiesList(List<String> specialitiesList) {
        this.specialitiesList = specialitiesList;
    }
}
