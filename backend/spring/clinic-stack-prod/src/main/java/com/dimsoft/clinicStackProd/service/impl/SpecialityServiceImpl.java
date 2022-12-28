
package com.dimsoft.clinicStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.beans.SpecialityMin;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.SpecialityRepository;
import com.dimsoft.clinicStackProd.service.SpecialityService;
import com.dimsoft.clinicStackProd.util.Constants;

@Service
@Transactional
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    SpecialityRepository specialityRepository;

    public Speciality createOrUpdateSpeciality(Speciality speciality) throws ClinicException {
        Speciality specialityToFound = specialityRepository.findBySpecialityName(speciality.getSpecialityName());
        if (speciality.getId() != null) {
            if (specialityToFound != null && speciality.getId() != specialityToFound.getId()) {
                throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALITY_NAME_ALREADY_EXIST);
            } else if (specialityToFound != null &&
                    specialityToFound.getStatus() == Constants.STATE_DELETED) {
                throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.SPECIALITY_ALREADY_DELETED);
            } else {
                speciality.setLastUpdateOn(new Date());
                return specialityRepository.save(speciality);
            }
        } else {
            if (specialityToFound != null)
                throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALITY_NAME_ALREADY_EXIST);
            else {
                speciality.setLastUpdateOn(new Date());
                speciality.setCreatedOn(new Date());
                return specialityRepository.save(speciality);
            }
        }
    }

    @Override
    public void createSpecialities(Speciality[] specialities) throws ClinicException {
        for (Speciality speciality : specialities) {
            createOrUpdateSpeciality(speciality);
        }
    }

    @Override
    public List<Speciality> getAllSpecialities() {
        List<Speciality> specialities = new ArrayList<Speciality>();
        specialityRepository.getAllSpecialities(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED)
                .forEach(specialities::add);
        return specialities;
    }

    @Override
    public List<Speciality> getActivatedSpeciality() {
        List<Speciality> specialities = new ArrayList<Speciality>();
        specialityRepository.getActivatedSpecialities(Constants.STATE_ACTIVATED)
                .forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality deleteSpeciality(Integer specialityId) throws ClinicException {
        Optional<Speciality> specialityToDelete = specialityRepository.findById(specialityId);
        if (specialityToDelete.isPresent() && specialityToDelete.get().getStatus() == Constants.STATE_ACTIVATED) {
            specialityToDelete.get().setLastUpdateOn(new Date());
            specialityToDelete.get().setStatus(Constants.STATE_DELETED);
        } else if (specialityToDelete.isPresent() && specialityToDelete.get().getStatus() == Constants.STATE_DELETED) {
            throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.SPECIALITY_ALREADY_DELETED);
        } else {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALITY_NOT_FOUND);
        }
        return specialityRepository.save(specialityToDelete.get());
    }

    @Override
    public List<Speciality> getAllSpeciality() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality getSpecialityById(Integer specialityId) {
        Optional<Speciality> data = specialityRepository.findById(specialityId);
        if (data.isPresent())
            return data.get();
        return null;
    }

    @Override
    public List<SpecialityMin> findAllSpecialitiesMin() {
        List<Speciality> list = specialityRepository.findAll();
        List<SpecialityMin> listSpecialityMin = new ArrayList<SpecialityMin>();
        for (Speciality speciality : list) {
            if (speciality.getStatus() == Constants.STATE_ACTIVATED)
                listSpecialityMin.add(new SpecialityMin(speciality.getId(), speciality.getSpecialityName(),
                        speciality.getSpecialistCommonName(), speciality.getSpecialityDesc(),
                        speciality.getSpecialityImagePath()));
        }
        return listSpecialityMin;
    }
}
