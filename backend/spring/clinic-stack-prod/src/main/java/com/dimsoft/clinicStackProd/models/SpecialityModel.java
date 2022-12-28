package com.dimsoft.clinicStackProd.models;

import java.util.Date;
import java.util.List;

import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.Speciality;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialityModel {

    private String specialityName;
    private String specialistCommonName;
    private String specialityDesc;
    private String longDescription;
    private String specialityImagePath;
    private short status;
    private Date createdOn;
    private Date lastUpdateOn;
    private List<SpecialistSpeciality> specialistSpecialityList;

  public SpecialityModel(
    String specialityName,
    String specialistCommonName,
    String specialityDesc,
    String longDescription,
    String specialityImagePath,
    short status,
    Date createdOn,
    Date lastUpdateOn,
    List<SpecialistSpeciality> specialistSpecialityList
  ){
    this.specialityName = specialityName;
    this.specialistCommonName = specialistCommonName;
    this.specialityDesc = specialityDesc;
    this.longDescription = longDescription;
    this.specialityImagePath = specialityImagePath;
    this.status = status;
    this.createdOn = createdOn;
    this.lastUpdateOn = lastUpdateOn;
    this.specialistSpecialityList = specialistSpecialityList;
  }
  
  public Speciality getSpeciality(SpecialityModel model){
    Speciality speciality = new Speciality(
        model.specialityName,
        model.specialistCommonName,
        model.specialityDesc,
        model.longDescription,
        model.specialityImagePath,
        model.status,
        model.createdOn,
        model.lastUpdateOn,
        model.specialistSpecialityList
    );
    return speciality;
  }
}
