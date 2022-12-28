package com.dimsoft.clinicStackProd.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "appointment")
@XmlRootElement
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appointment_id", nullable = false)
    private Integer appointmentId;
    @Column(name = "appointment_date")
    private Date appointmentDate;
    @Column(name = "state")
    private String state;
    @Column(name = "appointment_hour", length = 255)
    private Date appointmentHour;
    @Column(name = "original_hour")
    private String originalAppointmentHour;
    @Column(name = "patient_name", length = 255)
    private String patientName;
    @Column(name = "patient_phone", length = 255)
    private String patientPhone;
    @Column(name = "patient_email", length = 255)
    private String patientEmail;
    @Column(name = "patient_message", length = 255)
    private String patientMessage;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private short status;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "last_update_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateOn;

    @JoinColumn(name = "specialist_id", referencedColumnName = "specialist_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Specialist specialistId;

    public Appointment() {
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getAppointmentHour() {
        return appointmentHour;
    }

    public void setAppointmentHour(Date appointmentHour) {
        this.appointmentHour = appointmentHour;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientMessage() {
        return patientMessage;
    }

    public void setPatientMessage(String patientMessage) {
        this.patientMessage = patientMessage;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short stateDeleted) {
        this.status = stateDeleted;
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

    public Specialist getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Specialist specialistId) {
        this.specialistId = specialistId;
    }

    public String getOriginalAppointmentHour() {
        return originalAppointmentHour;
    }

    public void setOriginalAppointmentHour(String originalAppointmentHour) {
        this.originalAppointmentHour = originalAppointmentHour;
    }
}
