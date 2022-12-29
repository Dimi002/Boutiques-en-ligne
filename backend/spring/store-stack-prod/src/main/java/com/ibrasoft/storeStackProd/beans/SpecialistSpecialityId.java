package com.ibrasoft.storeStackProd.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SpecialistSpecialityId implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.ALL)
    private Specialist specialist;
    @ManyToOne(cascade = CascadeType.ALL)
    private Speciality speciality;

    public SpecialistSpecialityId() {
    }

    public SpecialistSpecialityId(Specialist specialist, Speciality speciality) {
        this.specialist = specialist;
        this.speciality = speciality;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

}
