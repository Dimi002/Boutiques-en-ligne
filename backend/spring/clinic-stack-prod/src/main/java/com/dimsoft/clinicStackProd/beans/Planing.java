package com.dimsoft.clinicStackProd.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.dimsoft.clinicStackProd.response.PlaningDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "planing")
public class Planing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    @Column(name = "plan_day")
    private Integer planDay;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "specialist", referencedColumnName = "specialist_id")
    @JsonProperty(access = Access.WRITE_ONLY)
    private Specialist specialist;

    /**
     * 
     */
    public Planing(PlaningDTO planingDTO) {
        this.id = planingDTO.getId();
        this.startTime = planingDTO.getStartTime();
        this.endTime = planingDTO.getEndTime();
        this.specialist = planingDTO.getSpecialist();
        this.planDay = planingDTO.getPlanDay();
    }

    /**
     * 
     */
    public Planing() {
    }

    /**
     * @param id
     */
    public Planing(Long id) {
        this.id = id;
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
    @XmlTransient
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
