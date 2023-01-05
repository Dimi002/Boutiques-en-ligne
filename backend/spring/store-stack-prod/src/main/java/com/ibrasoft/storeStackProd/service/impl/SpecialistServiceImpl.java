package com.ibrasoft.storeStackProd.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibrasoft.storeStackProd.beans.SocialMediaLinks;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.models.Boutique;
import com.ibrasoft.storeStackProd.repository.PlaningRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistSpecialityRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.JsonSerializer;

import javafx.scene.image.Image;

@Service
@Transactional
public class SpecialistServiceImpl implements SpecialistService {

    @Value("${application.upload-directory}")
    private String userBucketPath;

    @Autowired
    SpecialistRepository specialistRepository;

    @Autowired
    SpecialistSpecialityRepository specialistSpecialityRepository;

    @Autowired
    PlaningRepository planingRepository;

    @Autowired
    UserRepository userService;

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

    @Override
    public Specialist createBoutique(Boutique boutique) throws ClinicException, IOException {

        String path = userBucketPath;
        String fileName = boutique.getBoutiqueImage().getOriginalFilename();
        String ExtensionFileName = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
        if (ExtensionFileName.equals("PNG") || ExtensionFileName.equals("png") || ExtensionFileName.equals("jpeg")
                || ExtensionFileName.equals("JPEG") || ExtensionFileName.equals("jpg")
                || ExtensionFileName.equals("JPG")
                || ExtensionFileName.equals("gif") || ExtensionFileName.equals("GIF") || ExtensionFileName.equals("gif")
                || ExtensionFileName.equals("GIF") || ExtensionFileName.equals("svg")
                || ExtensionFileName.equals("SVG")) {
            Files.copy(boutique.getBoutiqueImage().getInputStream(),
                    Paths.get(path + File.separator + boutique.getBoutiqueImage().getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } else {
            throw new ClinicException("le fichier selectionne n'est pas une photo  ou n'a pas la bonne extension");
        }

        if (specialistRepository.findByBoutiqueName(boutique.getBoutiqueName()) == null) {
            String id = boutique.getBoutiqueUser().substring(boutique.getBoutiqueUser().indexOf(":") + 1,
                    boutique.getBoutiqueUser().length());
            Integer idnew = Integer.parseInt(id);
            Optional<User> user = userService.findById(idnew);
            Specialist specialist = new Specialist();
            specialist.setBoutiqueDescription(boutique.getBoutiqueDescription());
            specialist.setBoutiqueName(boutique.getBoutiqueName());
            specialist.setBoutiqueQuater(boutique.getBoutiqueQuater());
            specialist.setBoutiqueImage(fileName);
            specialist.setUserId(user.get());

            specialistRepository.save(specialist);
            return specialist;
        } else {
            throw new ClinicException("le nom de boutique est deja utilise");
        }

    }

    @Override
    public Specialist updateBoutique(Specialist boutique) {
        Specialist specialist = new Specialist();
        specialist.setBoutiqueDescription(boutique.getBoutiqueDescription());
        specialist.setBoutiqueImage(boutique.getBoutiqueImage());
        specialist.setBoutiqueQuater(boutique.getBoutiqueQuater());
        specialist.setBoutiqueImage(boutique.getBoutiqueImage());

        specialistRepository.save(specialist);
        return specialist;
    }

    @Override
    public Specialist deleteBoutique(Integer boutiqueId) {
        Specialist specialist = findBySpecialistId(boutiqueId);
        specialistRepository.delete(specialist);
        return specialist;
    }

    @Override
    public List<Specialist> getAllBoutique() {
        return specialistRepository.findAll();
    }

}
