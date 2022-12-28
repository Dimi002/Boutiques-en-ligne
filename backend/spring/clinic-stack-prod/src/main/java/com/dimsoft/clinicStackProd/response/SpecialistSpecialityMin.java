package com.dimsoft.clinicStackProd.response;

public class SpecialistSpecialityMin {
    private Integer specialistId;
    private Integer specialityId;

    public SpecialistSpecialityMin() {
    }

    public SpecialistSpecialityMin(Integer specialistId, Integer specialityId) {
        this.specialistId = specialistId;
        this.specialityId = specialityId;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

}
