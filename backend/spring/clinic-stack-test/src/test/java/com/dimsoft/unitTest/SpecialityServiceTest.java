package com.dimsoft.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.beans.SpecialityMin;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.AppointmentRepository;
import com.dimsoft.clinicStackProd.repository.PermissionRepository;
import com.dimsoft.clinicStackProd.repository.PlaningRepository;
import com.dimsoft.clinicStackProd.repository.RolePermissionRepository;
import com.dimsoft.clinicStackProd.repository.RoleRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistSpecialityRepository;
import com.dimsoft.clinicStackProd.repository.SpecialityRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.repository.UserRoleRepository;
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.service.SpecialistService;
import com.dimsoft.clinicStackProd.service.SpecialistSpecialityService;
import com.dimsoft.clinicStackProd.service.SpecialityService;
import com.dimsoft.clinicStackProd.util.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = ClinicStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpecialityServiceTest {

    @Autowired
    SpecialityService specialityService;

    @Autowired
    SpecialistSpecialityService specialistSpecialityService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SpecialistRepository specialistRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PlaningRepository planingRepository;

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    SpecialistSpecialityRepository specialistSpecialityRepository;

    @Autowired
    SpecialityRepository specialityRepository;

    @BeforeEach
    public void deleteAll() {
        rolePermissionRepository.deleteAll();
        userRoleRepository.deleteAll();
        permissionRepository.deleteAll();
        roleRepository.deleteAll();
        appointmentRepository.deleteAll();
        specialistSpecialityRepository.deleteAll();
        specialityRepository.deleteAll();
        specialistRepository.deleteAll();
        userRepository.deleteAll();
    }

    // génération d'une chaine de caractère aléatoire
    public static String generatRandomString(Integer length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            int index = rd.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        String generatedString = sb.toString();
        return generatedString;
    }

    // création d'une spécialité aléatoire
    public Speciality initialiseSpeciality(Boolean name) {
        Speciality speciality = new Speciality();
        String defaultSpecialityName = "Cardiologie";
        speciality.setCreatedOn(new Date());
        speciality.setLongDescription("probleme cardiaque");
        speciality.setSpecialistCommonName("Cardiologue");
        if (name) {
            speciality.setSpecialityName(defaultSpecialityName);
        } else {
            speciality.setSpecialityName(generatRandomString(10));
        }
        speciality.setStatus(Constants.STATE_ACTIVATED);
        speciality.setSpecialityDesc("description");
        speciality.setSpecialityImagePath("dimitri.jpg");
        return speciality;
    }

    // création d'une spécialité avec succès
    @Test
    void createSpecialityWithSucces() throws ClinicException {
        // instanciation d'une spécialité
        Speciality expectedSpeciality = initialiseSpeciality(false);
        // Création de la spécialité
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(expectedSpeciality);

        // Vérifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialitySaved.getSpecialityName());
        assertEquals(expectedSpeciality.getLongDescription(), specialitySaved.getLongDescription());
        assertEquals(expectedSpeciality.getSpecialistCommonName(), specialitySaved.getSpecialistCommonName());
        assertEquals(expectedSpeciality.getSpecialityDesc(), specialitySaved.getSpecialityDesc());
        assertEquals(expectedSpeciality.getSpecialityImagePath(), specialitySaved.getSpecialityImagePath());
        assertEquals(expectedSpeciality.getSpecialityName(), specialitySaved.getSpecialityName());
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved.getStatus());
    }

    // création d'une spécialité avec un nom existant
    @Test
    void createSpecialityWithExistingNameWithError() throws ClinicException {
        // Création d'une prémière spécialité avec
        // le nom par défaut ("Cardiologie")
        specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Si on tente de créer une nouvelle spécialité avec le même nom,
        // une exception sera lévée car le nom d'une spécialité doit être unique

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialityService.createOrUpdateSpeciality(initialiseSpeciality(true)));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALITY_NAME_ALREADY_EXIST, e.getMessage());
    }

    // Obtenir la liste de toutes les spécialités actives et non actives
    @Test
    void getAllSpecialitiesWithSucces() throws ClinicException {
        // Création d'une prémière spécialité
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Création d'une seconde spécialité
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on désactive la seconde spécialité et on fait la mise à jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // Obtention de toutes les spécialités actives et non actives de la BD
        List<Speciality> specialities = specialityService.getAllSpecialities();
        // on a maintenant 2 spécialités dont l'une est active et l'autre non active

        // Vérifications
        assertNotNull(specialitySaved1);
        assertNotNull(specialitySaved1.getId());
        assertNotNull(specialitySaved2);
        assertNotNull(specialitySaved2.getId());
        assertNotNull(specialities);
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialitySaved2.getStatus());
        assertEquals(2, specialities.size());
    }

    // Obtenir la liste de toutes les spécialités actives et non actives
    // dans le cas où il n'y a pas de spécialité enregistrée
    @Test
    void getAllSpecialitiesWithEmptyArray() {
        // Obtention de toutes les spécialités actives et non actives de la BD
        List<Speciality> specialities = specialityService.getAllSpecialities();
        // on obtiendra une liste vide

        // Vérifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Obtenir la liste de toutes les spécialités actives
    @Test
    void getAllActiveSpecialitiesWithSucces() throws ClinicException {
        // Création d'une prémière spécialité
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Création d'une seconde spécialité
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on désactive la seconde spécialité et on fait la mise à jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // Obtention de toutes les spécialités actives de la BD
        List<Speciality> specialities = specialityService.getActivatedSpeciality();
        // on a maintenant 2 spécialités dont l'une est active et l'autre non active
        // on attend alors 1 spécialité en sortie

        // Vérifications
        assertNotNull(specialitySaved1);
        assertNotNull(specialitySaved1.getId());
        assertNotNull(specialitySaved2);
        assertNotNull(specialitySaved2.getId());
        assertNotNull(specialities);
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialitySaved2.getStatus());
        assertEquals(1, specialities.size());
    }

    // Obtenir la liste de toutes les spécialités actives
    // dans le cas où il n'y a pas de spécialité enregistrée
    @Test
    void getAllActiveSpecialitiesWithEmptyArray() {
        // Obtention de toutes les spécialités actives de la BD
        List<Speciality> specialities = specialityService.getActivatedSpeciality();
        // on obtiendra une liste vide

        // Vérifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Obtenir la liste de toutes les spécialités de la BD ayant
    // n'importe quel statut
    @Test
    void getAllSpecialitiesFromDBWithSucces() throws ClinicException {
        // Création d'une prémière spécialité
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Création d'une seconde spécialité
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Création d'une troisième spécialité
        Speciality specialitySaved3 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on désactive la seconde spécialité et on fait la mise à jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // on supprime la troisième spécialité et on fait la mise à jour
        specialitySaved3 = specialityService.deleteSpeciality(specialitySaved3.getId());

        // Obtention de toutes les spécialités de la BD
        List<Speciality> specialities = specialityService.getAllSpeciality();
        // on a maintenant 3 spécialités ayant les statuts actives
        // non active et supprimée

        // Vérifications
        assertNotNull(specialitySaved1);
        assertNotNull(specialitySaved1.getId());
        assertNotNull(specialitySaved2);
        assertNotNull(specialitySaved2.getId());
        assertNotNull(specialitySaved3);
        assertNotNull(specialitySaved3.getId());
        assertNotNull(specialities);
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialitySaved2.getStatus());
        assertEquals(Constants.STATE_DELETED, specialitySaved3.getStatus());
        assertEquals(3, specialities.size());
    }

    // Obtenir la liste de toutes les spécialités de la BD
    // dans le cas où il n'y a pas de spécialité enregistrée
    @Test
    void getAllSpecialitiesFromDBWithEmptyArray() {
        // Obtention de toutes les spécialités de la BD
        List<Speciality> specialities = specialityService.getAllSpeciality();
        // on obtiendra une liste vide

        // Vérifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Supprimer une spécialité existante
    @Test
    void deleteExistingSpecialityWithSucces() throws ClinicException {
        // Création d'une spécialité
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // suppression de la spécialité
        Speciality specialitydeleted = specialityService.deleteSpeciality(specialitySaved.getId());

        // Vérifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialitydeleted);
        assertNotNull(specialitydeleted.getId());
        assertEquals(specialitySaved.getId(), specialitydeleted.getId());
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved.getStatus());
        assertEquals(Constants.STATE_DELETED, specialitydeleted.getStatus());
    }

    // Supprimer une spécialité deja supprime
    @Test
    void deleteAlreadyDeletedSpecialityWithError() throws ClinicException {
        // Création d'une spécialité
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // suppression de la spécialité
        Speciality specialitydeleted = specialityService.deleteSpeciality(specialitySaved.getId());

        // Vérifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialitydeleted);
        assertNotNull(specialitydeleted.getId());
        assertEquals(specialitySaved.getId(), specialitydeleted.getId());
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved.getStatus());
        assertEquals(Constants.STATE_DELETED, specialitydeleted.getStatus());

        ClinicException e = assertThrows(ClinicException.class,
                () -> specialityService.deleteSpeciality(specialitydeleted.getId()));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.SPECIALITY_ALREADY_DELETED, e.getMessage());
    }

    // Supprimer une spécialité inexistante
    @Test
    void deleteNotExistingSpecialityWithSucces() {
        // Création d'un id inexistant
        Integer id = 1;
        // suppression de la spécialité inexistante
        // une exception sera lévée puisque l'id n'existe pas
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialityService.deleteSpeciality(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.SPECIALITY_NOT_FOUND, e.getMessage());
    }

    // Rechercher une spécialité existante par son ID
    @Test
    void getExistingSpecialityByIdWithSucces() throws ClinicException {
        // Création d'une spécialité
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // recherche de la spécialité par son id
        Speciality specialityFound = specialityService.getSpecialityById(specialitySaved.getId());

        // Vérifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialityFound);
        assertNotNull(specialityFound.getId());
        assertEquals(specialitySaved.getId(), specialityFound.getId());
    }

    // Rechercher une spécialité inexistante par son ID
    @Test
    void getNotExistingSpecialityByIdWithSucces() {
        // Création d'un id inexistant
        Integer id = 1;
        // recherche de la spécialité par son id
        Speciality specialityFound = specialityService.getSpecialityById(id);

        // Vérifications
        assertNull(specialityFound);
    }

    // Obtenir la liste des spécialités minimales actives
    @Test
    void findAllSpecialitiesMinWithSucces() throws ClinicException {
        // Création d'une prémière spécialité
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Création d'une seconde spécialité
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Création d'une troisième spécialité
        Speciality specialitySaved3 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on désactive la seconde spécialité et on fait la mise à jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // on supprime la troisième spécialité et on fait la mise à jour
        specialitySaved3 = specialityService.deleteSpeciality(specialitySaved3.getId());

        // création de la liste des spécialités minimales
        List<SpecialityMin> listSpecialityMin = specialityService.findAllSpecialitiesMin();
        // à ce stade il n'existe qu'une spécialité active

        // Vérifications
        assertNotNull(listSpecialityMin);
        assertEquals(1, listSpecialityMin.size());
        assertEquals(specialitySaved1.getId(), listSpecialityMin.get(0).getId());
        assertEquals(specialitySaved1.getSpecialityName(), listSpecialityMin.get(0).getSpecialityName());
        assertEquals(specialitySaved1.getSpecialistCommonName(), listSpecialityMin.get(0).getSpecialistCommonName());
        assertEquals(specialitySaved1.getSpecialityDesc(), listSpecialityMin.get(0).getSpecialityDesc());
        assertEquals(specialitySaved1.getSpecialityImagePath(), listSpecialityMin.get(0).getSpecialityImagePath());
    }
}
