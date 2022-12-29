package com.ibrasoft.storeStackProd.controller;

import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;
import com.ibrasoft.storeStackProd.response.SpecialistDTO;
import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.SocialMediaLinks;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.service.SpecialistSpecialityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/specialists")
public class SpecialistController {

	@Autowired
	private SpecialistService specialistService;

	@Autowired
	private SpecialistSpecialityService specialistSpecialityService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllSpecialist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllSpecialist() {
		return new ResponseEntity<>(specialistService.getAllSpecialist(), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllActiveSpecialist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllActiveSpecialist() {
		return new ResponseEntity<>(specialistService.getActiveSpecialist(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSpecialistsAllResources", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Specialist>> getAllSpecialistPlaning() {
		return new ResponseEntity<List<Specialist>>(specialistService.getAllSpecialistPlaning(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createSpecialist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSpecialist(@RequestBody Specialist specialist) throws ClinicException {
		if (specialistService.findByUserId(specialist.getUserId()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALIST_ALREADY_EXIST);
		}
		Specialist specialistCreated = specialistService.createOrUpdateSpecialist(specialist);
		return new ResponseEntity<>(specialistCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateSpecialist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSpecialist(@RequestBody Specialist specialist)
			throws InvalidInputException, ClinicException {
		Specialist specialistByUserId = specialistService.findByUserId(specialist.getUserId());
		Specialist specialistUpdated = new Specialist();
		if (specialistByUserId != null && specialistByUserId.getUserId() != specialist.getUserId()) {
			specialistUpdated = specialistService.createOrUpdateSpecialist(specialist);
		}
		return new ResponseEntity<>(specialistUpdated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteSpecialist/{specialistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable("specialistId") int specialistId) throws ClinicException {
		Specialist specialistDeleted = specialistService.deleteSpecialist(specialistId);
		if (specialistDeleted != null) {
			return new ResponseEntity<>(specialistDeleted, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findSpecialistById/{specialistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findSpecialistById(@PathVariable("specialistId") int specialistId) {
		Specialist specialistFound = specialistService.findBySpecialistId(specialistId);
		List<Appointment> appointments = this.specialistSpecialityService.getAppointmentBySpecialist(specialistFound);
		SpecialistDTO s = new SpecialistDTO(specialistFound);
		s.setAppointmentsList(appointments);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateSocialMediaById/{specialistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSocialMediaById(@RequestBody SocialMediaLinks socialMediaLinks,
			@PathVariable("specialistId") Integer specialistId) throws ClinicException {
		Specialist specialistFound = specialistService.findBySpecialistId(specialistId);
		if (specialistFound != null) {
			specialistService.updateSocialMediaById(socialMediaLinks, specialistFound);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		}
		return new ResponseEntity<>(specialistFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getSocialMediaById/{specialistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSocialMediaById(@PathVariable("specialistId") Integer specialistId)
			throws ClinicException {
		Specialist specialistFound = specialistService.findBySpecialistId(specialistId);
		SocialMediaLinks socialMediaLinksObject = new SocialMediaLinks();
		if (specialistFound != null) {
			socialMediaLinksObject = specialistService.getSocialMediaById(specialistId);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		}
		return new ResponseEntity<>(socialMediaLinksObject, HttpStatus.OK);
	}
}
