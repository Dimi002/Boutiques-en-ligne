package com.dimsoft.clinicStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimsoft.clinicStackProd.beans.SocialMediaLinks;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.PlaningRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistSpecialityRepository;
import com.dimsoft.clinicStackProd.service.SpecialistService;
import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.JsonSerializer;

@Service
@Transactional
public class SpecialistServiceImpl implements SpecialistService {

    @Autowired
    SpecialistRepository specialistRepository;

    @Autowired
    SpecialistSpecialityRepository specialistSpecialityRepository;

    @Autowired
    PlaningRepository planingRepository;

    @Override
    public Specialist createOrUpdateSpecialist(Specialist specialist) throws ClinicException {
        if (specialist.getUserId() == null)
            throw new ClinicException(Constants.NULL_POINTER, Constants.USER_IS_NULL);
        if (specialist.getUserId().getId() == null)
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
        Specialist specialistFound = this.findByUserId(specialist.getUserId().getId());

        if (specialist.getSpecialistId() != null) {
            if (specialistFound != null && specialist.getSpecialistId() != specialistFound.getSpecialistId()) {
                throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALIST_ALREADY_EXIST);
            } else if (specialistFound != null &&
                    specialistFound.getStatus() == Constants.STATE_DELETED) {
                throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.SPECIALIST_ALREADY_DELETED);
            } else {
                specialist.setLastUpdateOn(new Date());
                return specialistRepository.save(specialist);
            }
        } else {
            if (specialistFound != null)
                throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.SPECIALIST_ALREADY_EXIST);
            else {
                specialist.setCreatedOn(new Date());
                specialist.setLastUpdateOn(new Date());
                return specialistRepository.save(specialist);
            }
        }
    }

    @Override
    public List<Specialist> getAllSpecialist() {
        List<Specialist> specialists = new ArrayList<Specialist>();
        specialistRepository.getAllSpecialists(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED)
                .forEach(specialist -> {
                    specialist.getSpecialistSpecialitiesList();
                    specialists.add(specialist);
                });
        return specialists;
    }

    @Override
    public List<Specialist> getActiveSpecialist() {
        List<Specialist> specialists = new ArrayList<Specialist>();
        specialistRepository.getActivatedSpecialists(Constants.STATE_ACTIVATED).forEach(specialists::add);
        return specialists;
    }

    @Override
    public Specialist deleteSpecialist(int specialistId) throws ClinicException {
        Optional<Specialist> specialistToDelete = specialistRepository.findById(specialistId);
        if (specialistToDelete.isPresent()) {
            specialistToDelete.get().setLastUpdateOn(new Date());
            specialistToDelete.get().setStatus(Constants.STATE_DELETED);
            return specialistRepository.save(specialistToDelete.get());
        } else {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
        }
    }

    @Override
    public Specialist findByUserId(User userId) {
        return specialistRepository.findByUserId(userId);
    }

    @Override
    public Specialist findBySpecialistId(Integer specialistId) {
        Specialist specialist = specialistRepository.findBySpecialistId(specialistId);
        if (specialist != null) {
            specialist.getSpecialistSpecialitiesList();
            specialist.getPlanings();
        }
        return specialist;
    }

    @Override
    public Specialist findByUserId(Integer userId) {
        return specialistRepository.findByUserIdId(userId);
    }

    @Override
    public Specialist getSpecialistBeforeAuth(Integer userId) {
        Specialist specialist = specialistRepository.findByUserIdId(userId);
        if (specialist != null) {
            specialist.setSpecialistSpecialityList(
                    specialistSpecialityRepository
                            .findBySpecialistSpecialityIdSpecialistSpecialistId(specialist.getSpecialistId()));
            return specialist;
        }
        return null;
    }

    @Override
    public Specialist updateSocialMediaById(SocialMediaLinks socialMediaLinks, Specialist specialistFound) {
        String socialMedia = JsonSerializer.toJson(socialMediaLinks, SocialMediaLinks.class);
        if (socialMedia != null) {
            specialistFound.setSocialMediaLinks(socialMedia);
        }
        specialistRepository.updateSocialMediaById(socialMedia, specialistFound.getSpecialistId());
        return this.findBySpecialistId(specialistFound.getSpecialistId());
    }

    @Override
    public SocialMediaLinks getSocialMediaById(Integer specialistId) {
        SocialMediaLinks socialMediaLinksObject = new SocialMediaLinks();
        String socialMedia = specialistRepository.findBySpecialistId(specialistId).getSocialMediaLinks();
        if (socialMedia != null) {
            socialMediaLinksObject = JsonSerializer.fromJson(socialMedia, SocialMediaLinks.class);
        }
        return socialMediaLinksObject;
    }

    @Override
    public List<Specialist> getAllSpecialistPlaning() {
        List<Specialist> listSpecialists = getAllSpecialist();
        for (Specialist specialist : listSpecialists)
            specialist.getSpecialistSpecialitiesList();
        return listSpecialists;
    }
}
