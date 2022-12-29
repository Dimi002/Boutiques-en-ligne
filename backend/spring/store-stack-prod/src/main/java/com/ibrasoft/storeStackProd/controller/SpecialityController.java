package com.ibrasoft.storeStackProd.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.beans.SpecialityMin;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.SpecialityRepository;
import com.ibrasoft.storeStackProd.service.SpecialityService;
import com.ibrasoft.storeStackProd.util.Constants;

/**
 * @email roslyn.temateu@ibrasoft.eu
 * @author Maestros
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/specialities")
public class SpecialityController {

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    SpecialityRepository specialityRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/createSpeciality", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Speciality> create(@RequestBody Speciality speciality)
            throws ClinicException {
        Speciality specialityFounded = specialityRepository.findBySpecialityName(speciality.getSpecialityName());
        if (specialityFounded != null)
            throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALITY_NAME_ALREADY_EXIST);
        return new ResponseEntity<Speciality>(specialityService.createOrUpdateSpeciality(speciality),
                HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateSpeciality", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Speciality> updateSpeciality(@RequestBody Speciality speciality) throws ClinicException {
        Speciality specialityFounded = specialityRepository.findBySpecialityName(speciality.getSpecialityName());
        if (specialityFounded == null) {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
        }
        if (specialityFounded != null && specialityFounded.getId() == speciality.getId()) {
            return new ResponseEntity<Speciality>(specialityService.createOrUpdateSpeciality(speciality),
                    HttpStatus.OK);
        } else {
            throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALITY_NAME_ALREADY_EXIST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllSpecialities", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Speciality>> getAllSpecialities() {
        return new ResponseEntity<List<Speciality>>(specialityService.getAllSpecialities(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActivatedSpecialities", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Speciality>> getAllActivatedSpecialities() {
        return new ResponseEntity<List<Speciality>>(specialityService.getActivatedSpeciality(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deleteSpeciality/{specialityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Speciality> deleteSpeciality(
            @PathVariable("specialityId") Integer specialityId) throws ClinicException {
        return new ResponseEntity<Speciality>(specialityService.deleteSpeciality(specialityId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findSpeciality/{specialityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Speciality> findSpecialityById(
            @PathVariable("specialityId") Integer specialityId) {
        return new ResponseEntity<Speciality>(specialityService.getSpecialityById(specialityId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllSpecialitiesMin", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<SpecialityMin>> findAllSpecialitiesMin() {
        return new ResponseEntity<List<SpecialityMin>>(specialityService.findAllSpecialitiesMin(), HttpStatus.OK);
    }

}
