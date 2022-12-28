package com.dimsoft.clinicStackProd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.beans.SpecialityMin;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;

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
