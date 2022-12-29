package com.ibrasoft.storeStackProd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.beans.SpecialityMin;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;

@Service
public interface SpecialityService {

	public Speciality createOrUpdateSpeciality(Speciality speciality) throws ClinicException;

	public void createSpecialities(Speciality[] specialities) throws ClinicException;

	public List<Speciality> getAllSpecialities();

	public List<Speciality> getAllSpeciality();

	public List<Speciality> getActivatedSpeciality();

	public Speciality deleteSpeciality(Integer specialityId) throws ClinicException;

	public Speciality getSpecialityById(Integer specialityId);

	public List<SpecialityMin> findAllSpecialitiesMin();
}
