package com.dimsoft.clinicStackProd.models;

import java.util.Date;

public class CountDoctorDashbord {
    private int allPatient;
    private int allAceptTodayPatient;
    private int allCancelTodayPatient;
    private Date allAceptTodayPatientDate;
    private Date allCancelTodayPatientDate;
    
    public CountDoctorDashbord(Date allAceptTodayPatientDate, Date allCancelTodayPatientDate) {
        this.allAceptTodayPatientDate = allAceptTodayPatientDate;
        this.allCancelTodayPatientDate = allCancelTodayPatientDate;
    }

    public CountDoctorDashbord() {
    }

    public CountDoctorDashbord(int allPatient, int allAceptTodayPatient, int allCancelTodayPatient) {
        this.allPatient = allPatient;
        this.allAceptTodayPatient = allAceptTodayPatient;
        this.allCancelTodayPatient = allCancelTodayPatient;
    }

    public int getAllPatient() {
        return allPatient;
    }

    public void setAllPatient(int allPatient) {
        this.allPatient = allPatient;
    }

    public int getAllAceptTodayPatient() {
        return allAceptTodayPatient;
    }

    public void setAllAceptTodayPatient(int allAceptTodayPatient) {
        this.allAceptTodayPatient = allAceptTodayPatient;
    }

    public int getAllCancelTodayPatient() {
        return allCancelTodayPatient;
    }

    public void setAllCancelTodayPatient(int allCancelTodayPatient) {
        this.allCancelTodayPatient = allCancelTodayPatient;
    }

    public Date getAllAceptTodayPatientDate() {
        return allAceptTodayPatientDate;
    }

    public void setAllAceptTodayPatientDate(Date allAceptTodayPatientDate) {
        this.allAceptTodayPatientDate = allAceptTodayPatientDate;
    }

    public Date getAllCancelTodayPatientDate() {
        return allCancelTodayPatientDate;
    }

    public void setAllCancelTodayPatientDate(Date allCancelTodayPatientDate) {
        this.allCancelTodayPatientDate = allCancelTodayPatientDate;
    }
    
}
