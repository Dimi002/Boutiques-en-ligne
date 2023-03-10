package com.ibrasoft.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.beans.SpecialityMin;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.AppointmentRepository;
import com.ibrasoft.storeStackProd.repository.PermissionRepository;
import com.ibrasoft.storeStackProd.repository.PlaningRepository;
import com.ibrasoft.storeStackProd.repository.RolePermissionRepository;
import com.ibrasoft.storeStackProd.repository.RoleRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistSpecialityRepository;
import com.ibrasoft.storeStackProd.repository.SpecialityRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.repository.UserRoleRepository;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.service.SpecialistSpecialityService;
import com.ibrasoft.storeStackProd.service.SpecialityService;
import com.ibrasoft.storeStackProd.util.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = StoreStackProdApplication.class)
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

    // g??n??ration d'une chaine de caract??re al??atoire
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

    // cr??ation d'une sp??cialit?? al??atoire
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

    // cr??ation d'une sp??cialit?? avec succ??s
    @Test
    void createSpecialityWithSucces() throws ClinicException {
        // instanciation d'une sp??cialit??
        Speciality expectedSpeciality = initialiseSpeciality(false);
        // Cr??ation de la sp??cialit??
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(expectedSpeciality);

        // V??rifications
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

    // cr??ation d'une sp??cialit?? avec un nom existant
    @Test
    void createSpecialityWithExistingNameWithError() throws ClinicException {
        // Cr??ation d'une pr??mi??re sp??cialit?? avec
        // le nom par d??faut ("Cardiologie")
        specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Si on tente de cr??er une nouvelle sp??cialit?? avec le m??me nom,
        // une exception sera l??v??e car le nom d'une sp??cialit?? doit ??tre unique

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialityService.createOrUpdateSpeciality(initialiseSpeciality(true)));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALITY_NAME_ALREADY_EXIST, e.getMessage());
    }

    // Obtenir la liste de toutes les sp??cialit??s actives et non actives
    @Test
    void getAllSpecialitiesWithSucces() throws ClinicException {
        // Cr??ation d'une pr??mi??re sp??cialit??
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Cr??ation d'une seconde sp??cialit??
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on d??sactive la seconde sp??cialit?? et on fait la mise ?? jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // Obtention de toutes les sp??cialit??s actives et non actives de la BD
        List<Speciality> specialities = specialityService.getAllSpecialities();
        // on a maintenant 2 sp??cialit??s dont l'une est active et l'autre non active

        // V??rifications
        assertNotNull(specialitySaved1);
        assertNotNull(specialitySaved1.getId());
        assertNotNull(specialitySaved2);
        assertNotNull(specialitySaved2.getId());
        assertNotNull(specialities);
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialitySaved2.getStatus());
        assertEquals(2, specialities.size());
    }

    // Obtenir la liste de toutes les sp??cialit??s actives et non actives
    // dans le cas o?? il n'y a pas de sp??cialit?? enregistr??e
    @Test
    void getAllSpecialitiesWithEmptyArray() {
        // Obtention de toutes les sp??cialit??s actives et non actives de la BD
        List<Speciality> specialities = specialityService.getAllSpecialities();
        // on obtiendra une liste vide

        // V??rifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Obtenir la liste de toutes les sp??cialit??s actives
    @Test
    void getAllActiveSpecialitiesWithSucces() throws ClinicException {
        // Cr??ation d'une pr??mi??re sp??cialit??
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // Cr??ation d'une seconde sp??cialit??
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on d??sactive la seconde sp??cialit?? et on fait la mise ?? jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // Obtention de toutes les sp??cialit??s actives de la BD
        List<Speciality> specialities = specialityService.getActivatedSpeciality();
        // on a maintenant 2 sp??cialit??s dont l'une est active et l'autre non active
        // on attend alors 1 sp??cialit?? en sortie

        // V??rifications
        assertNotNull(specialitySaved1);
        assertNotNull(specialitySaved1.getId());
        assertNotNull(specialitySaved2);
        assertNotNull(specialitySaved2.getId());
        assertNotNull(specialities);
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialitySaved2.getStatus());
        assertEquals(1, specialities.size());
    }

    // Obtenir la liste de toutes les sp??cialit??s actives
    // dans le cas o?? il n'y a pas de sp??cialit?? enregistr??e
    @Test
    void getAllActiveSpecialitiesWithEmptyArray() {
        // Obtention de toutes les sp??cialit??s actives de la BD
        List<Speciality> specialities = specialityService.getActivatedSpeciality();
        // on obtiendra une liste vide

        // V??rifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Obtenir la liste de toutes les sp??cialit??s de la BD ayant
    // n'importe quel statut
    @Test
    void getAllSpecialitiesFromDBWithSucces() throws ClinicException {
        // Cr??ation d'une pr??mi??re sp??cialit??
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Cr??ation d'une seconde sp??cialit??
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Cr??ation d'une troisi??me sp??cialit??
        Speciality specialitySaved3 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on d??sactive la seconde sp??cialit?? et on fait la mise ?? jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // on supprime la troisi??me sp??cialit?? et on fait la mise ?? jour
        specialitySaved3 = specialityService.deleteSpeciality(specialitySaved3.getId());

        // Obtention de toutes les sp??cialit??s de la BD
        List<Speciality> specialities = specialityService.getAllSpeciality();
        // on a maintenant 3 sp??cialit??s ayant les statuts actives
        // non active et supprim??e

        // V??rifications
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

    // Obtenir la liste de toutes les sp??cialit??s de la BD
    // dans le cas o?? il n'y a pas de sp??cialit?? enregistr??e
    @Test
    void getAllSpecialitiesFromDBWithEmptyArray() {
        // Obtention de toutes les sp??cialit??s de la BD
        List<Speciality> specialities = specialityService.getAllSpeciality();
        // on obtiendra une liste vide

        // V??rifications
        assertNotNull(specialities);
        assertEquals(0, specialities.size());
    }

    // Supprimer une sp??cialit?? existante
    @Test
    void deleteExistingSpecialityWithSucces() throws ClinicException {
        // Cr??ation d'une sp??cialit??
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // suppression de la sp??cialit??
        Speciality specialitydeleted = specialityService.deleteSpeciality(specialitySaved.getId());

        // V??rifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialitydeleted);
        assertNotNull(specialitydeleted.getId());
        assertEquals(specialitySaved.getId(), specialitydeleted.getId());
        assertEquals(Constants.STATE_ACTIVATED, specialitySaved.getStatus());
        assertEquals(Constants.STATE_DELETED, specialitydeleted.getStatus());
    }

    // Supprimer une sp??cialit?? deja supprime
    @Test
    void deleteAlreadyDeletedSpecialityWithError() throws ClinicException {
        // Cr??ation d'une sp??cialit??
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // suppression de la sp??cialit??
        Speciality specialitydeleted = specialityService.deleteSpeciality(specialitySaved.getId());

        // V??rifications
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

    // Supprimer une sp??cialit?? inexistante
    @Test
    void deleteNotExistingSpecialityWithSucces() {
        // Cr??ation d'un id inexistant
        Integer id = 1;
        // suppression de la sp??cialit?? inexistante
        // une exception sera l??v??e puisque l'id n'existe pas
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialityService.deleteSpeciality(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.SPECIALITY_NOT_FOUND, e.getMessage());
    }

    // Rechercher une sp??cialit?? existante par son ID
    @Test
    void getExistingSpecialityByIdWithSucces() throws ClinicException {
        // Cr??ation d'une sp??cialit??
        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
        // recherche de la sp??cialit?? par son id
        Speciality specialityFound = specialityService.getSpecialityById(specialitySaved.getId());

        // V??rifications
        assertNotNull(specialitySaved);
        assertNotNull(specialitySaved.getId());
        assertNotNull(specialityFound);
        assertNotNull(specialityFound.getId());
        assertEquals(specialitySaved.getId(), specialityFound.getId());
    }

    // Rechercher une sp??cialit?? inexistante par son ID
    @Test
    void getNotExistingSpecialityByIdWithSucces() {
        // Cr??ation d'un id inexistant
        Integer id = 1;
        // recherche de la sp??cialit?? par son id
        Speciality specialityFound = specialityService.getSpecialityById(id);

        // V??rifications
        assertNull(specialityFound);
    }

    // Obtenir la liste des sp??cialit??s minimales actives
    @Test
    void findAllSpecialitiesMinWithSucces() throws ClinicException {
        // Cr??ation d'une pr??mi??re sp??cialit??
        Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Cr??ation d'une seconde sp??cialit??
        Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // Cr??ation d'une troisi??me sp??cialit??
        Speciality specialitySaved3 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
        // on d??sactive la seconde sp??cialit?? et on fait la mise ?? jour
        specialitySaved2.setStatus(Constants.STATE_DEACTIVATED);
        specialityService.createOrUpdateSpeciality(specialitySaved2);
        // on supprime la troisi??me sp??cialit?? et on fait la mise ?? jour
        specialitySaved3 = specialityService.deleteSpeciality(specialitySaved3.getId());

        // cr??ation de la liste des sp??cialit??s minimales
        List<SpecialityMin> listSpecialityMin = specialityService.findAllSpecialitiesMin();
        // ?? ce stade il n'existe qu'une sp??cialit?? active

        // V??rifications
        assertNotNull(listSpecialityMin);
        assertEquals(1, listSpecialityMin.size());
        assertEquals(specialitySaved1.getId(), listSpecialityMin.get(0).getId());
        assertEquals(specialitySaved1.getSpecialityName(), listSpecialityMin.get(0).getSpecialityName());
        assertEquals(specialitySaved1.getSpecialistCommonName(), listSpecialityMin.get(0).getSpecialistCommonName());
        assertEquals(specialitySaved1.getSpecialityDesc(), listSpecialityMin.get(0).getSpecialityDesc());
        assertEquals(specialitySaved1.getSpecialityImagePath(), listSpecialityMin.get(0).getSpecialityImagePath());
    }
}
