package com.ibrasoft.storeStackProd.response;

import java.util.Date;
import java.util.List;

import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.Planing;
import com.ibrasoft.storeStackProd.beans.SocialMediaLinks;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.SpecialistSpeciality;
import com.ibrasoft.storeStackProd.beans.User;

public class SpecialistDTO {
    private static final long serialVersionUID = 1L;
    private Integer specialistId;
    private Integer yearOfExperience;
    private SocialMediaLinks socialMediaLinksObject;
    private String socialMediaLinks;
    private String biography;
    private String gender;
    private String city;
    private short status;
    private Date createdOn;
    private Date lastUpdateOn;
    private User userId;
    private List<SpecialistSpeciality> specialistSpecialityList;
    private List<Appointment> appointmentsList;
    private List<String> specialitiesList;
    private List<Planing> planings;

    /**
     * 
     */
    public SpecialistDTO() {
    }

    /**
     * @param specialis
     */
    public SpecialistDTO(Specialist s) {
        this.yearOfExperience = s.getYearOfExperience();
        this.socialMediaLinksObject = s.getSocialMediaLinksObject();
        this.socialMediaLinks = s.getSocialMediaLinks();
        this.biography = s.getBiography();
        this.gender = s.getGender();
        this.city = s.getCity();
        this.status = s.getStatus();
        this.createdOn = s.getCreatedOn();
        this.lastUpdateOn = s.getLastUpdateOn();
        this.userId = s.getUserId();
        this.specialistSpecialityList = s.getSpecialistSpecialityList();
        this.appointmentsList = s.getAppointmentsList();
        this.specialitiesList = s.getSpecialitiesList();
        this.planings = s.getPlanings();
        this.specialistId = s.getSpecialistId();
    }

    public Specialist toSpecialist() {
        Specialist s = new Specialist();
        s.setAppointmentsList(appointmentsList);
        s.setBiography(biography);
        s.setCity(city);
        s.setCreatedOn(createdOn);
        s.setGender(gender);
        s.setLastUpdateOn(lastUpdateOn);
        s.setPlanings(planings);
        s.setSocialMediaLinks(socialMediaLinks);
        s.setSocialMediaLinksObject(socialMediaLinksObject);
        s.setSpecialistId(specialistId);
        s.setSpecialistSpecialityList(specialistSpecialityList);
        s.setSpecialitiesList(specialitiesList);
        s.setStatus(status);
        s.setUserId(userId);
        s.setYearOfExperience(yearOfExperience);
        return s;
    }

    /**
     * @return the specialistId
     */
    public Integer getSpecialistId() {
        return specialistId;
    }

    /**
     * @param specialistId the specialistId to set
     */
    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    /**
     * @return the yearOfExperience
     */
    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    /**
     * @param yearOfExperience the yearOfExperience to set
     */
    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    /**
     * @return the socialMediaLinksObject
     */
    public SocialMediaLinks getSocialMediaLinksObject() {
        return socialMediaLinksObject;
    }

    /**
     * @param socialMediaLinksObject the socialMediaLinksObject to set
     */
    public void setSocialMediaLinksObject(SocialMediaLinks socialMediaLinksObject) {
        this.socialMediaLinksObject = socialMediaLinksObject;
    }

    /**
     * @return the socialMediaLinks
     */
    public String getSocialMediaLinks() {
        return socialMediaLinks;
    }

    /**
     * @param socialMediaLinks the socialMediaLinks to set
     */
    public void setSocialMediaLinks(String socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }

    /**
     * @return the biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * @param biography the biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the status
     */
    public short getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(short status) {
        this.status = status;
    }

    /**
     * @return the createdOn
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the lastUpdateOn
     */
    public Date getLastUpdateOn() {
        return lastUpdateOn;
    }

    /**
     * @param lastUpdateOn the lastUpdateOn to set
     */
    public void setLastUpdateOn(Date lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;
    }

    /**
     * @return the userId
     */
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return the specialistSpecialityList
     */
    public List<SpecialistSpeciality> getSpecialistSpecialityList() {
        return specialistSpecialityList;
    }

    /**
     * @param specialistSpecialityList the specialistSpecialityList to set
     */
    public void setSpecialistSpecialityList(List<SpecialistSpeciality> specialistSpecialityList) {
        this.specialistSpecialityList = specialistSpecialityList;
    }

    /**
     * @return the appointmentsList
     */
    public List<Appointment> getAppointmentsList() {
        return appointmentsList;
    }

    /**
     * @param appointmentsList the appointmentsList to set
     */
    public void setAppointmentsList(List<Appointment> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    /**
     * @return the specialitiesList
     */
    public List<String> getSpecialitiesList() {
        return specialitiesList;
    }

    /**
     * @param specialitiesList the specialitiesList to set
     */
    public void setSpecialitiesList(List<String> specialitiesList) {
        this.specialitiesList = specialitiesList;
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

}
