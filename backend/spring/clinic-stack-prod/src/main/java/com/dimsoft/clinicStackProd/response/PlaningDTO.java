package com.dimsoft.clinicStackProd.response;

import com.dimsoft.clinicStackProd.beans.Specialist;

public class PlaningDTO {

    private Long id;
    private String startTime;
    private String endTime;
    private Integer planDay;
    private Specialist specialist;

    /**
     * 
     */
    public PlaningDTO() {
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPlanDay() {
        return planDay;
    }

    public void setPlanDay(Integer planDay) {
        this.planDay = planDay;
    }

    /**
     * @return the specialist
     */
    public Specialist getSpecialist() {
        return specialist;
    }

    /**
     * @param specialist the specialist to set
     */
    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
