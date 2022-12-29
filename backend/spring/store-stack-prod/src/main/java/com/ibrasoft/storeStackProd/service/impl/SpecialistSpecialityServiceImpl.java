package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.SpecialistSpeciality;
import com.ibrasoft.storeStackProd.beans.SpecialistSpecialityId;
import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.AppointmentRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistSpecialityRepository;
import com.ibrasoft.storeStackProd.repository.SpecialityRepository;
import com.ibrasoft.storeStackProd.response.AgGridResponse;
import com.ibrasoft.storeStackProd.response.SpecialistSpecialityMin;
import com.ibrasoft.storeStackProd.response.SpecialitiesIds;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.service.SpecialistSpecialityService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.ObjectMapper;
import com.dimsoft.filters.FilterModelSpecificationBuilder;
import com.dimsoft.models.SearchCriteriasModel;

@Service
@Transactional
public class SpecialistSpecialityServiceImpl implements SpecialistSpecialityService {

	@Autowired
	SpecialistRepository specialistRepo;
	@Autowired
	SpecialityRepository specialityRepo;
	@Autowired
	SpecialistSpecialityRepository specialistSpecialityRepo;
	@Autowired
	AppointmentRepository appointmentRepository;

	private static ObjectMapper<Speciality> objectMapper = new ObjectMapper<Speciality>();

	@Override
	public SpecialistSpeciality createSpecialistSpeciality(SpecialistSpecialityMin specialistSpecialityMin)
			throws ClinicException {
		Optional<Specialist> specialist = specialistRepo.findById(specialistSpecialityMin.getSpecialistId());
		Optional<Speciality> speciality = specialityRepo.findById(specialistSpecialityMin.getSpecialityId());
		SpecialistSpeciality specialistSpeciality = new SpecialistSpeciality();

		if (specialist.isPresent() && speciality.isPresent()) {
			SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId(specialist.get(),
					speciality.get());
			specialistSpeciality.setLastUpdateOn(new Date());
			specialistSpeciality.setCreatedOn(new Date());
			specialistSpeciality.setSpecialistSpecialityId(specialistSpecialityId);
		} else if (specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
		} else if (!specialist.isPresent() && speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		} else if (!specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND);
		}
		return specialistSpecialityRepo.save(specialistSpeciality);
	}

	@Override
	public SpecialistSpeciality updateSpecialistSpeciality(SpecialistSpeciality specialistSpeciality)
			throws ClinicException {
		SpecialistSpeciality specialistSpecialityFound = this.findSpecialistSpecialityBySpecialistIdAndSpecialityId(
				specialistSpeciality.getSpecialist().getSpecialistId(),
				specialistSpeciality.getSpeciality().getId());
		if (specialistSpecialityFound != null && specialistSpecialityFound.getStatus() == Constants.STATE_ACTIVATED) {
			specialistSpeciality.setLastUpdateOn(new Date());
		} else if (specialistSpecialityFound == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_SPECIALITY_NOT_FOUND);
		} else if (specialistSpecialityFound != null
				&& specialistSpecialityFound.getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.SPECIALIST_SPECIALITY_ALREADY_DELETED);
		} else if (specialistSpecialityFound != null
				&& specialistSpecialityFound.getStatus() == Constants.STATE_DEACTIVATED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED,
					Constants.SPECIALIST_SPECIALITY_ALREADY_DEACTIVATED);
		}
		return specialistSpecialityRepo.save(specialistSpeciality);
	}

	@Override
	public List<SpecialistSpeciality> getAllSpecialistsSpecialities() {
		List<SpecialistSpeciality> specialistSpeciality = new ArrayList<SpecialistSpeciality>();
		specialistSpecialityRepo.getAllSpecialistSpecialities(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED)
				.forEach(specialistSpeciality::add);
		return specialistSpeciality;
	}

	@Override
	public SpecialistSpeciality deleteSpecialistSpeciality(Integer specialistId, Integer specialityId)
			throws ClinicException {
		SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId();
		Optional<Specialist> specialist = specialistRepo.findById(specialistId);
		Optional<Speciality> speciality = specialityRepo.findById(specialityId);
		SpecialistSpeciality specialistSpecialityToDelete = new SpecialistSpeciality();

		if (specialist.isPresent() && speciality.isPresent()) {
			specialistSpecialityId.setSpecialist(specialist.get());
			specialistSpecialityId.setSpeciality(speciality.get());
			specialistSpecialityToDelete = specialistSpecialityRepo
					.findBySpecialistSpecialityId(specialistSpecialityId);
			if (specialistSpecialityToDelete != null) {
				specialistSpecialityRepo.delete(specialistSpecialityToDelete);
			}
		} else if (specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
		} else if (!specialist.isPresent() && speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		} else if (!specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND);
		}
		return null;
	}

	@Override
	public SpecialistSpeciality findSpecialistSpecialityBySpecialistIdAndSpecialityId(int specialistId,
			int specialityId) throws ClinicException {
		SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId();
		Optional<Specialist> specialist = specialistRepo.findById(specialistId);
		Optional<Speciality> speciality = specialityRepo.findById(specialityId);
		SpecialistSpeciality specialistSpecialityFound = new SpecialistSpeciality();

		if (specialist.isPresent() && speciality.isPresent()) {
			specialistSpecialityId.setSpecialist(specialist.get());
			specialistSpecialityId.setSpeciality(speciality.get());
			specialistSpecialityFound = specialistSpecialityRepo
					.findBySpecialistSpecialityId(specialistSpecialityId);
		} else if (specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
		} else if (!specialist.isPresent() && speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		} else if (!specialist.isPresent() && !speciality.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND);
		}

		return specialistSpecialityFound;
	}

	@Override
	public StateResponse assignSpecialitiesToSpecialist(int specialistId, SpecialitiesIds specialitiesListWrapper)
			throws ClinicException {
		Optional<Specialist> specialist = specialistRepo.findById(specialistId);
		// Liste des id des specialités envoyées dépuis le client
		List<Integer> specialityIdsList = specialitiesListWrapper.getSpecialitiesIdsList();
		// Liste des specialités envoyées dépuis le client
		List<Speciality> specialitysToAssingList = new ArrayList<Speciality>();
		// Liste des specialitys de l'utilisateur
		List<Speciality> specialistSpecialityList = new ArrayList<Speciality>();
		// Liste des specialitys de l'utilisateur qui ont encore été envoyées
		List<Speciality> specialitysToMaintainList = new ArrayList<Speciality>();

		if (specialist.isPresent() && specialist.get().getStatus() == Constants.STATE_ACTIVATED) {
			// Initialiser le tableau de specialitys envoyés dépuis le client
			specialityIdsList.forEach(specialityId -> {
				Optional<Speciality> speciality = specialityRepo.findById(specialityId);
				if (speciality.isPresent() && speciality.get().getStatus() == Constants.STATE_ACTIVATED) {
					specialitysToAssingList.add(speciality.get());
				} else {
					try {
						throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
					} catch (ClinicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			// Initialiser la liste des specialitys de l'utilisateur
			specialistSpecialityList = this.getAllSpecialistSpecialities(specialist.get().getSpecialistId());

			// On parcours la liste des rôles à assigner à l'utilisateur, si l'utilisateur
			// ne possède pas le rôle à assigner, on lui assigne le rôle. Sinon si
			// l'utilisateur possède déjà le rôle à assigner, on le sauvegerde dans une
			// liste
			for (int i = 0; i < specialitysToAssingList.size(); i++) {
				if (!specialistSpecialityList.contains(specialitysToAssingList.get(i))) {
					SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId(specialist.get(),
							specialitysToAssingList.get(i));
					SpecialistSpeciality specialistSpeciality = new SpecialistSpeciality(specialistSpecialityId);
					specialistSpecialityRepo.save(specialistSpeciality);
				} else {
					specialitysToMaintainList.add(specialitysToAssingList.get(i));
				}
			}
			// On enlève tout le rôles maintenus pour ne rester qu'avec les specialitys à
			// supprimer
			specialistSpecialityList.removeAll(specialitysToMaintainList);

			// On supprime les specialitys qui ne sont plus assignés
			specialistSpecialityList.forEach(specialityToDelete -> {
				SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId(specialist.get(),
						specialityToDelete);
				SpecialistSpeciality specialistSpeciality = new SpecialistSpeciality(specialistSpecialityId);
				specialistSpecialityRepo.delete(specialistSpeciality);
			});

			return new StateResponse("SUCCEEDED");
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		}
	}

	public StateResponse removeSpecialitiesToSpecialist(int specialistId, SpecialitiesIds specialitiesListWrapper) {

		Specialist specialist = specialistRepo.findById(specialistId).get();
		List<Integer> specialityIdsList = specialitiesListWrapper.getSpecialitiesIdsList();
		if (specialist != null) {
			specialityIdsList.forEach(specialityId -> {
				Speciality speciality = specialityRepo.findById(specialityId).get();
				if (speciality != null) {
					SpecialistSpecialityId specialistSpecialityId = new SpecialistSpecialityId(specialist, speciality);
					SpecialistSpeciality specialistSpeciality = specialistSpecialityRepo
							.findBySpecialistSpecialityId(specialistSpecialityId);
					if (specialistSpeciality != null) {
						specialistSpecialityRepo.delete(specialistSpeciality);
					}
				}
			});
			return new StateResponse("SUCCEEDED");
		}
		return new StateResponse("FAILED");
	}

	public List<Speciality> getAllSpecialistSpecialities(Integer specialistId) {
		List<Speciality> specialitiesList = new ArrayList<Speciality>();
		Specialist specialist = specialistRepo.findById(specialistId).get();
		if (specialist != null) {
			List<SpecialistSpeciality> specialistSpecialityList = specialistSpecialityRepo
					.findBySpecialistSpecialityIdSpecialist(specialist);
			specialistSpecialityList.forEach(specialistSpeciality -> {
				specialitiesList.add(specialistSpeciality.getSpeciality());
			});
		}
		return specialitiesList;
	}

	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public AgGridResponse record(Integer page, Integer size) {
		Page<Speciality> params;

		Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
		params = specialityRepo.findAll(pageRequest);

		AgGridResponse agGridPret = new AgGridResponse(params);
		agGridPret.setContent(objectMapper.castToObject(params.getContent()));

		return agGridPret;
	}

	/**
	 * 
	 * @param filters
	 * @param page
	 * @param size
	 * @param sort
	 * @param filterCriteriaList
	 * @return
	 */
	@Override
	public AgGridResponse recordCriteria(String filters, Integer page, Integer size, String sort,
			List<SearchCriteriasModel> filterCriteriaList) {
		if (null == filterCriteriaList || filterCriteriaList.isEmpty()) {
			AgGridResponse specialities = record(page, size);
			return specialities;
		}

		FilterModelSpecificationBuilder<Speciality> builder = new FilterModelSpecificationBuilder<Speciality>(
				filterCriteriaList);
		AgGridResponse specialities = searchAll(builder.toSpecification(), page, size);

		return specialities;
	}

	/**
	 * 
	 * @param spec
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public AgGridResponse searchAll(Specification<Speciality> spec, Integer page, Integer size) {
		Pageable pageRequest = PageRequest.of(page, size, Sort.by("id"));
		Page<Speciality> params = specialityRepo.findAll(spec, pageRequest);
		AgGridResponse agGridResponse = new AgGridResponse(params);
		agGridResponse.setContent(objectMapper.castToObject(params.getContent()));
		return agGridResponse;
	}

	@Override
	public List<Specialist> getAllSpecialitySpecialistsById(Integer specialityId) {
		List<Specialist> specialistsList = new ArrayList<Specialist>();
		Speciality speciality = specialityRepo.findById(specialityId).get();
		if (speciality != null) {
			List<SpecialistSpeciality> specialistSpecialityList = specialistSpecialityRepo
					.findBySpecialistSpecialityIdSpeciality(speciality);
			specialistSpecialityList.forEach(specialistSpeciality -> {
				specialistSpeciality.getSpecialist().getSpecialistSpecialitiesList();
				specialistsList.add(specialistSpeciality.getSpecialist());
			});
		}
		return specialistsList;
	}

	@Override
	public List<Appointment> getAppointmentBySpecialist(Specialist s) {
		return this.appointmentRepository.getAllSupTodayAppointment(new Date(), s);
	}
}
