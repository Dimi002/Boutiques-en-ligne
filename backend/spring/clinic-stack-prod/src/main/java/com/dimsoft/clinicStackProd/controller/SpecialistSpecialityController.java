package com.dimsoft.clinicStackProd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dimsoft.clinicStackProd.beans.Appointment;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.exceptions.InvalidInputException;
import com.dimsoft.clinicStackProd.response.AgGridResponse;
import com.dimsoft.clinicStackProd.response.SpecialistDTO;
import com.dimsoft.clinicStackProd.response.SpecialistSpecialityMin;
import com.dimsoft.clinicStackProd.response.SpecialitiesIds;
import com.dimsoft.clinicStackProd.response.StateResponse;
import com.dimsoft.clinicStackProd.service.SpecialistSpecialityService;
import com.dimsoft.clinicStackProd.service.SpecialityService;
import com.dimsoft.models.SearchCriteriasModel;

/**
 * @email roslyn.temateu@dimsoft.eu
 * @author Maestros
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/specialist-specialities")
public class SpecialistSpecialityController {

        @Autowired
        public SpecialityService specialityService;

        @Autowired
        private SpecialistSpecialityService specialistSpecialityService;

        @RequestMapping(method = RequestMethod.POST, value = "/createSpecialistSpeciality", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<SpecialistSpeciality> createSpecialistSpeciality(
                        @RequestBody SpecialistSpecialityMin specialistSpecialityMin) throws ClinicException {
                return new ResponseEntity<SpecialistSpeciality>(
                                specialistSpecialityService.createSpecialistSpeciality(specialistSpecialityMin),
                                HttpStatus.CREATED);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/getAllSpecialistSpecialities", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<List<SpecialistSpeciality>> getAllSpecialistSpecialities() {
                return new ResponseEntity<List<SpecialistSpeciality>>(
                                specialistSpecialityService.getAllSpecialistsSpecialities(), HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/getAllSpecialistSpecialityById", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<List<Speciality>> getAllSpecialistSpecialityById(
                        @RequestParam Integer specialistId) {
                return new ResponseEntity<List<Speciality>>(
                                specialistSpecialityService.getAllSpecialistSpecialities(specialistId), HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/getAllSpecialitySpecialistsById", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<List<SpecialistDTO>> getAllSpecialitySpecialistsById(
                        @RequestParam Integer specialityId) {
                List<Specialist> list = specialistSpecialityService.getAllSpecialitySpecialistsById(specialityId);
                List<SpecialistDTO> listDot = new ArrayList<SpecialistDTO>();
                list.forEach((s) -> {
                        SpecialistDTO spec = new SpecialistDTO(s);
                        List<Appointment> appointments = specialistSpecialityService.getAppointmentBySpecialist(s);
                        spec.setAppointmentsList(appointments);
                        listDot.add(spec);
                });
                return new ResponseEntity<List<SpecialistDTO>>(listDot, HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.POST, value = "/updateSpecialistSpeciality", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<SpecialistSpeciality> updateSpecialistSpeciality(
                        @RequestBody SpecialistSpeciality specialistSpeciality) throws ClinicException {
                return new ResponseEntity<SpecialistSpeciality>(
                                specialistSpecialityService.updateSpecialistSpeciality(specialistSpeciality),
                                HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/deleteSpecialistSpecialityById", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<SpecialistSpeciality> deleteSpecialistSpecialityById(
                        @RequestParam Integer specialistId, @RequestParam Integer specialityId)
                        throws InvalidInputException, ClinicException {
                return new ResponseEntity<SpecialistSpeciality>(
                                specialistSpecialityService.deleteSpecialistSpeciality(specialistId, specialityId),
                                HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/getBySpecialistIdAndSpecialityId", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<SpecialistSpeciality> getBySpecialistIdAndSpecialityId(
                        @RequestParam int specialistId, @RequestParam int specialityId) throws ClinicException {
                return new ResponseEntity<SpecialistSpeciality>(specialistSpecialityService
                                .findSpecialistSpecialityBySpecialistIdAndSpecialityId(specialistId, specialityId),
                                HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/assignSpecialitiesToSpecialist", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<StateResponse> assignSpecialitiesToSpecialist(
                        @RequestParam int specialistId,
                        @RequestBody SpecialitiesIds specialitiesListWrapper) throws ClinicException {
                return new ResponseEntity<StateResponse>(
                                specialistSpecialityService.assignSpecialitiesToSpecialist(specialistId,
                                                specialitiesListWrapper),
                                HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET, value = "/removeSpecialityFromList", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<StateResponse> removeSpecialityFromList(@RequestParam int specialistId,
                        @RequestBody SpecialitiesIds specialitiesListWrapper) throws InvalidInputException {
                return new ResponseEntity<StateResponse>(
                                specialistSpecialityService.removeSpecialitiesToSpecialist(specialistId,
                                                specialitiesListWrapper),
                                HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.POST, value = "/specialityList", produces = MediaType.APPLICATION_JSON_VALUE)
        public @ResponseBody ResponseEntity<AgGridResponse> record(
                        @RequestParam(value = "filters", required = false) String filters,
                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "200") Integer size,
                        @RequestParam(value = "sort", required = false) String sort,
                        @RequestBody List<SearchCriteriasModel> filterCriteriaList) {

                return new ResponseEntity<AgGridResponse>(
                                specialistSpecialityService.recordCriteria(filters, page, size, sort,
                                                filterCriteriaList),
                                HttpStatus.OK);
        }
}
