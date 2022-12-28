package com.dimsoft.clinicStackProd.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.Appointment;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.response.AgGridResponse;
import com.dimsoft.clinicStackProd.response.SpecialistSpecialityMin;
import com.dimsoft.clinicStackProd.response.SpecialitiesIds;
import com.dimsoft.clinicStackProd.response.StateResponse;
import com.dimsoft.models.SearchCriteriasModel;

@Service
public interface SpecialistSpecialityService {

	public SpecialistSpeciality createSpecialistSpeciality(SpecialistSpecialityMin specialistSpeciality)
			throws ClinicException;

	public SpecialistSpeciality updateSpecialistSpeciality(SpecialistSpeciality specialistSpeciality)
			throws ClinicException;

	public List<SpecialistSpeciality> getAllSpecialistsSpecialities();

	public SpecialistSpeciality deleteSpecialistSpeciality(Integer specialistId, Integer specialityId)
			throws ClinicException;

	public SpecialistSpeciality findSpecialistSpecialityBySpecialistIdAndSpecialityId(int specialistId,
			int specialityId) throws ClinicException;

	public StateResponse assignSpecialitiesToSpecialist(int specialistId, SpecialitiesIds specialitiesListWrapper)
			throws ClinicException;

	public StateResponse removeSpecialitiesToSpecialist(int specialistId, SpecialitiesIds specialitiesListWrapper);

	public List<Speciality> getAllSpecialistSpecialities(Integer specialistId);

	public List<Specialist> getAllSpecialitySpecialistsById(Integer specialityId);

	public AgGridResponse record(Integer page, Integer size);

	public AgGridResponse recordCriteria(String filters, Integer page, Integer size, String sort,
			List<SearchCriteriasModel> filterCriteriaList);

	public AgGridResponse searchAll(Specification<Speciality> spec, Integer page, Integer size);

	public List<Appointment> getAppointmentBySpecialist(Specialist specialistFound);
}
