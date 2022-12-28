package com.dimsoft.clinicStackProd.controller;

import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.beans.Appointment;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.service.AppointmentService;
import com.dimsoft.clinicStackProd.service.SpecialistService;

import java.util.Date;
import java.util.List;

import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private SpecialistService specialistService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllAppointment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllAppointment() {
		return new ResponseEntity<>(appointmentService.getAllAppointment(), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllAppointments() {
		return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllDistinctPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllDistinctPatients() {
		return new ResponseEntity<>(appointmentService.getAllDistinctPatients(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCountAllAppointment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCountCountDoctorDashbord(@PathVariable Integer id) throws ClinicException {
		if (specialistService.findBySpecialistId(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
		} else {
			Specialist id1 = specialistService.findBySpecialistId(id);
			return new ResponseEntity<>(appointmentService.getCountDoctorDashbord(id1), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllAppointmentSpecialist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllAppointmentSpecialist(@PathVariable Integer id) throws ClinicException {
		if (specialistService.findBySpecialistId(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
		} else {
			Specialist id1 = specialistService.findBySpecialistId(id);
			return new ResponseEntity<>(appointmentService.getAllAppointmentSpecialist(id1), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateSpecialistState/{id}/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSpecialistState(@PathVariable Integer id, @PathVariable String state)
			throws ClinicException {
		if (appointmentService.findAppointmentById(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.APPOINTMENT_NOT_FOUND);
		} else {
			return new ResponseEntity<>(appointmentService.updateSpecialistState(id, state), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllTodayAppointment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTodayAppointment(@PathVariable Integer id) throws ClinicException {
		Date date = new Date();
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		Date date1 = new Date();
		date1.setHours(23);
		date1.setMinutes(59);
		date1.setSeconds(59);
		if (specialistService.findBySpecialistId(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		} else {
			Specialist id1 = specialistService.findBySpecialistId(id);
			return new ResponseEntity<>(appointmentService.getAllTodayAppointment(date, id1, date1), HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllSupTodayAppointment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllSupTodayAppointment(@PathVariable Integer id) throws ClinicException {
		Date date = new Date();
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		if (specialistService.findBySpecialistId(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
		} else {
			Specialist id1 = specialistService.findBySpecialistId(id);
			return new ResponseEntity<>(appointmentService.getAllSupTodayAppointment(date, id1), HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createAppointment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment)
			throws ClinicException {
		if (appointment.getPatientName() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_NAME_IS_REQUIRED);
		} else if (appointment.getPatientEmail() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_EMAIL_IS_REQUIRED);
		} else if (appointment.getPatientPhone() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_PHONE_IS_REQUIRED);
		} else if (appointment.getOriginalAppointmentHour() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED);
		} else if (appointment.getAppointmentDate() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.APPOINTMENT_DATE_IS_REQUIRED);
		} else if (appointment.getAppointmentHour() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.APPOINTMENT_HOUR_IS_REQUIRED);
		} else if (appointment.getPatientMessage() == null) {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_MESSAGE_IS_REQUIRED);
		} else if (!Utils.matchWithPattern(appointment.getOriginalAppointmentHour())) {
			throw new ClinicException(Constants.INVALID_INPUT, Constants.HOUR_PATTERN_NOT_MATCHES);
		}

		Appointment appointmentCreated = appointmentService.createOrUpdateAppointment(appointment);
		this.appointmentService.sendConfirmNotifications(appointment, Constants.N_A_CREATE);
		return new ResponseEntity<>(appointmentCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllActivedAppoitment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Appointment>> getAllActivedAppoitment() {
		return new ResponseEntity<List<Appointment>>(appointmentService.getActiveAppointment(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllArchivedAppoitment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<Appointment>> getAllArchivedAppoitment() {
		return new ResponseEntity<List<Appointment>>(appointmentService.getAllArchivedAppointment(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteAppoitment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Appointment> deleteAppoitment(@RequestParam int appointId)
			throws ClinicException {
		return new ResponseEntity<Appointment>(appointmentService.deleteAppointment(appointId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllByIdAppoitment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Appointment> getAllByIdAppoitment(@RequestParam int appointId)
			throws ClinicException {
		return new ResponseEntity<Appointment>(appointmentService.findAppointmentById(appointId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllById", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Integer> getAllById(@RequestParam int appointId) throws ClinicException {
		return new ResponseEntity<Integer>(appointmentService.findAppointmentById(appointId).getAppointmentId(),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getByspecialistIdAppoitment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Appointment> getByspecialistIdAppoitment(@RequestBody Specialist specialistId) {
		return new ResponseEntity<Appointment>(appointmentService.findBySpecialistId(specialistId), HttpStatus.OK);
	}

}
