package com.dimsoft.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.beans.User;
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
import com.dimsoft.clinicStackProd.response.SpecialistSpecialityMin;
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.service.SpecialistService;
import com.dimsoft.clinicStackProd.service.SpecialistSpecialityService;
import com.dimsoft.clinicStackProd.service.SpecialityService;
import com.dimsoft.clinicStackProd.service.UserService;
import com.dimsoft.clinicStackProd.util.Constants;

@SpringBootTest(classes = ClinicStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpecialistSpecialityServiceImplTest {

        @Autowired
        SpecialistSpecialityService specialistSpecialityService;

        @Autowired
        SpecialistService specialistService;

        @Autowired
        SpecialityService specialityService;

        @Autowired
        UserService userService;

        @Autowired
        RoleService roleService;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

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
                userRepository.deleteAll();
                appointmentRepository.deleteAll();
                specialistSpecialityRepository.deleteAll();
                specialityRepository.deleteAll();
                specialistRepository.deleteAll();
                userRepository.deleteAll();
        }

        // cette fonction produit une chaine de caractere
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

        // cette fonction instancie un utilisateur aleatoire
        public User buildRandomUser(Boolean login, Boolean email, Boolean password) {
                User expectedUser = new User();
                String defaultEmail = "dongmo@gmail.com";
                String defaultPassword = "alex";
                String defaultlogin = "alex";
                expectedUser.setBirthDate(new Date());
                if (password) {
                        expectedUser.setClearPassword(defaultPassword);
                        expectedUser.setPassword(passwordEncoder.encode("alex"));
                } else {
                        expectedUser.setClearPassword(generatRandomString(4));
                        expectedUser.setPassword(passwordEncoder.encode(expectedUser.getClearPassword()));
                }
                expectedUser.setComment(generatRandomString(25));
                if (email)
                        expectedUser.setEmail(defaultEmail);
                else
                        expectedUser.setEmail(generatRandomString(15));
                expectedUser.setCreatedOn(new Date());
                expectedUser.setFirstName(generatRandomString(6));
                expectedUser.setLastName(generatRandomString(4));
                expectedUser.setPhone(generatRandomString(9));
                if (login)
                        expectedUser.setUsername(defaultlogin);
                else
                        expectedUser.setUsername(generatRandomString(4));
                return expectedUser;
        }

        // cette fonction instancie un role aleatoire
        public Role buildRandomRole(String roleName) {
                Role expectedRole = new Role();
                expectedRole.setRoleDesc(generatRandomString(20));
                expectedRole.setRoleName(roleName);
                expectedRole.setStatus(Constants.STATE_ACTIVATED);
                return expectedRole;
        }

        // cette fonction instancie une specialite aleatoire
        public Speciality initialiseSpeciality(Boolean name) {
                Speciality speciality = new Speciality();
                String defaultSpecialityName = "Cardiologie";
                speciality.setCreatedOn(new Date());
                speciality.setLongDescription("problemes cardiaques");
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

        // Obtenir une liste vide de toutes les associations
        // specialist-speciality actives et non actives
        @Test
        void getAllSpecialistSpecialityWithEmptyArray() {
                List<SpecialistSpeciality> specialistSpecialities = specialistSpecialityService
                                .getAllSpecialistsSpecialities();
                assertTrue(specialistSpecialities.isEmpty());
        }

        // Obtenir la liste de toutes les associations
        // specialist-speciality actives et non actives
        @Test
        void getAllSpecialistSpecialityWithSuccess() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création de 2 utilisateurs
                User user1 = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                User user2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialist1 = specialistService.findByUserId(user1.getId());
                Specialist specialist2 = specialistService.findByUserId(user2.getId());
                // Création des spécialités
                Speciality specialitySaved1 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                Speciality specialitySaved2 = specialityService.createOrUpdateSpeciality(initialiseSpeciality(false));
                // instanciation des associations specialist-speciality minimales
                SpecialistSpecialityMin specialistSpecialityMin1 = new SpecialistSpecialityMin(
                                specialist1.getSpecialistId(),
                                specialitySaved1.getId());
                SpecialistSpecialityMin specialistSpecialityMin2 = new SpecialistSpecialityMin(
                                specialist2.getSpecialistId(),
                                specialitySaved2.getId());
                // création des associations specialist-speciality
                SpecialistSpeciality specialistSpeciality1 = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin1);
                SpecialistSpeciality specialistSpeciality2 = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin2);
                // modification du status de la seconde association et mise à jour
                specialistSpeciality2.setStatus(Constants.STATE_DEACTIVATED);
                specialistSpecialityService.updateSpecialistSpeciality(specialistSpeciality2);

                List<SpecialistSpeciality> specialistSpecialities = specialistSpecialityService
                                .getAllSpecialistsSpecialities();

                // Vérifications
                assertNotNull(specialistRole);
                assertNotNull(specialistRole.getRoleId());
                assertNotNull(user1);
                assertNotNull(user1.getId());
                assertNotNull(user2);
                assertNotNull(user2.getId());
                assertNotNull(specialistSpeciality1);
                assertNotNull(specialistSpeciality1.getSpecialistSpecialityId());
                assertNotNull(specialistSpeciality2);
                assertNotNull(specialistSpeciality2.getSpecialistSpecialityId());
                assertEquals(Constants.STATE_ACTIVATED, specialistSpeciality1.getStatus());
                assertEquals(Constants.STATE_DEACTIVATED, specialistSpeciality2.getStatus());
                assertNotNull(specialistSpecialities);
                assertFalse(specialistSpecialities.isEmpty());
                assertEquals(2, specialistSpecialities.size());
        }

        // Création d'une association specialist-speciality
        // NB: le spécialiste et la spécialité existe bien
        @Test
        void createSpecialistSpecialityWithSuccess() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimales correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);

                // Vérifications
                assertNotNull(specialistRole);
                assertNotNull(specialistRole.getRoleId());
                assertNotNull(userSaved);
                assertNotNull(userSaved.getId());
                assertNotNull(specialistSaved);
                assertNotNull(specialistSaved.getSpecialistId());
                assertNotNull(specialistSpecialityMin);
                assertNotNull(specialistSpeciality.getSpecialistSpecialityId());
                assertEquals(specialistSaved.getSpecialistId(), specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpeciality.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialityId(), specialistSpeciality.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialistId(),
                                specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(Constants.STATE_ACTIVATED, specialistSpeciality.getStatus());
        }

        // Ce test tente d'associer une spécialité existante à un
        // spécialiste inexistant en BD
        @Test
        void createSpecialistSpecialityWithoutSpecialistIdWithError() throws ClinicException {
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimales
                // correspondante en utilisant un id de spécialiste inexistant
                Integer specialistId = 1;
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(specialistId,
                                specialitySaved.getId());

                // le spécialiste n'existe pas, une exception est lévée
                ClinicException e = assertThrows(ClinicException.class, () -> specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
        }

        // Ce test tente d'associer une spécialité inexistante à
        // un spécialiste existant
        @Test
        void createSpecialistSpecialityWithoutSpecialityIdWithError() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // instanciation de l'association specialist-speciality minimales correspondante
                // en utilisant un id de spécialité inexistant
                Integer specialityId = 1;
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialityId);

                // la spécialité n'existe pas, une exception est lévée
                assertNotNull(specialistRole);
                assertNotNull(specialistRole.getRoleId());
                assertNotNull(userSaved);
                assertNotNull(userSaved.getId());
                assertNotNull(specialistSaved);
                assertNotNull(specialistSaved.getSpecialistId());
                ClinicException e = assertThrows(ClinicException.class, () -> specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // Ce test tente d'associer un spécialité inexistant à un
        // spécialiste inexistant en BD
        @Test
        void createSpecialistSpecialityWithoutSpecialistAndSpecialityIdWithError() {
                // instanciation de l'association specialist-speciality minimales
                // correspondante.
                // Création des ids fictifs
                Integer specialistId = 1;
                Integer specialityId = 1;
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(specialistId,
                                specialityId);

                // la spécialité et le spécialiste n'existent pas, une exception est lévée
                ClinicException e = assertThrows(ClinicException.class, () -> specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // Mise à jour d'une association specialist-speciality avec succès
        @Test
        void updateSpecialistSpecialityWithSuccess() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimales correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpecialitySaved = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // Mise à jour de l'association specialist-speciality
                specialistSpecialitySaved.setStatus(Constants.STATE_ARCHIVE);
                SpecialistSpeciality specialistSpecialityUpdated = specialistSpecialityService
                                .updateSpecialistSpeciality(specialistSpecialitySaved);

                // Vérifications
                assertNotNull(specialistRole);
                assertNotNull(specialistRole.getRoleId());
                assertNotNull(userSaved);
                assertNotNull(userSaved.getId());
                assertNotNull(specialistSaved);
                assertNotNull(specialistSaved.getSpecialistId());
                assertNotNull(specialistSpecialityMin);
                assertNotNull(specialistSpecialitySaved.getSpecialistSpecialityId());
                assertEquals(specialistSaved.getSpecialistId(),
                                specialistSpecialitySaved.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpecialitySaved.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialityId(),
                                specialistSpecialitySaved.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialistId(),
                                specialistSpecialitySaved.getSpecialist().getSpecialistId());
                assertEquals(specialistSaved.getSpecialistId(),
                                specialistSpecialityUpdated.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpecialityUpdated.getSpeciality().getId());
                assertEquals(Constants.STATE_ARCHIVE, specialistSpecialityUpdated.getStatus());
        }

        // Mise à jour d'une association specialist-speciality inexistante
        @Test
        void updateSpecialistSpecialityWithError() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création des utilisateurs
                User userSaved1 = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                User userSaved2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialistSaved1 = specialistService.findByUserId(userSaved1.getId());
                Specialist specialistSaved2 = specialistService.findByUserId(userSaved2.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation des associations specialist-speciality minimales
                // correspondantes
                SpecialistSpecialityMin specialistSpecialityMin1 = new SpecialistSpecialityMin(
                                specialistSaved1.getSpecialistId(),
                                specialitySaved.getId());
                SpecialistSpecialityMin specialistSpecialityMin2 = new SpecialistSpecialityMin(
                                specialistSaved2.getSpecialistId(),
                                specialitySaved.getId());
                // création des associations specialist-speciality
                SpecialistSpeciality specialistSpecialitySaved1 = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin1);
                SpecialistSpeciality specialistSpecialitySaved2 = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin2);
                // Suppression de la seconde association
                specialistSpecialityRepository.delete(specialistSpecialitySaved2);
                // en tentant la mise à jour de a seconde association
                // specialist-speciality qu'on vient de supprimer,
                // une exception sera lévée
                ClinicException e = assertThrows(ClinicException.class, () -> specialistSpecialityService
                                .updateSpecialistSpeciality(specialistSpecialitySaved2));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // Suppression d'une association specialist-speciality existante
        @Test
        void deleteExistingSpecialistSpecialityWithSuccess() throws ClinicException {
                // création d'un rôle SPECIALIST
                Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimales correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // Suppression de l'association specialist-speciality créée
                SpecialistSpeciality specialistSpecialitydeleted = specialistSpecialityService
                                .deleteSpecialistSpeciality(
                                                specialistSaved.getSpecialistId(),
                                                specialitySaved.getId());

                // Vérifications
                assertNotNull(specialistRole);
                assertNotNull(specialistRole.getRoleId());
                assertNotNull(userSaved);
                assertNotNull(userSaved.getId());
                assertNotNull(specialistSaved);
                assertNotNull(specialistSaved.getSpecialistId());
                assertNotNull(specialistSpecialityMin);
                assertNotNull(specialistSpeciality.getSpecialistSpecialityId());
                assertEquals(specialistSaved.getSpecialistId(), specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpeciality.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialityId(), specialistSpeciality.getSpeciality().getId());
                assertEquals(specialistSpecialityMin.getSpecialistId(),
                                specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(Constants.STATE_ACTIVATED, specialistSpeciality.getStatus());
                assertNull(specialistSpecialitydeleted);
        }

        // Suppression d'une association specialist-speciality dont
        // le spécialiste et la spécialité n'existent pas
        @Test
        void deleteNotExistingSpecialistSpecialityWithError() throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création des utilisateurs
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // ids non existants
                Integer specialistId = 2;
                Integer specialityId = 2;
                // une exception sera lévée

                // Vérifications
                assertNotNull(specialistSpeciality);
                assertEquals(specialistSaved.getSpecialistId(), specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpeciality.getSpeciality().getId());
                ClinicException e = assertThrows(ClinicException.class,
                                () -> specialistSpecialityService.deleteSpecialistSpeciality(
                                                specialistId, specialityId));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // Suppression d'une association specialist-speciality dont
        // le spécialiste n'existe pas
        @Test
        void deleteSpecialistSpecialityWithoutSpecialistIWithError() throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création des utilisateurs
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // ids non existants
                Integer specialistId = 2;
                // une exception sera lévée

                // Vérifications
                assertNotNull(specialistSpeciality);
                assertEquals(specialistSaved.getSpecialistId(), specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpeciality.getSpeciality().getId());
                ClinicException e = assertThrows(ClinicException.class,
                                () -> specialistSpecialityService.deleteSpecialistSpeciality(
                                                specialistId, specialitySaved.getId()));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
        }

        // Suppression d'une association specialist-speciality dont
        // la spécialité n'existe pas
        @Test
        void deleteSpecialistSpecialityWithoutSpecialityIWithError() throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création des utilisateurs
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // ids non existants
                Integer specialityId = 2;
                // une exception sera lévée

                // Vérifications
                assertNotNull(specialistSpeciality);
                assertEquals(specialistSaved.getSpecialistId(), specialistSpeciality.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpeciality.getSpeciality().getId());
                ClinicException e = assertThrows(ClinicException.class,
                                () -> specialistSpecialityService.deleteSpecialistSpeciality(
                                                specialistSaved.getSpecialistId(), specialityId));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // rechercher une association specialist-speciality
        // cas 1: le spécialiste et la spécialité existent
        @Test
        void findSpecialistSpecialityByExistingSpecialistIdAndExistingSpecialityIdWithSuccess()
                        throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création des utilisateurs
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche des spécialistes associés
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // recherche de l'association créée
                SpecialistSpeciality specialistSpecialityFound = specialistSpecialityService
                                .findSpecialistSpecialityBySpecialistIdAndSpecialityId(
                                                specialistSaved.getSpecialistId(), specialitySaved.getId());

                // Vérifications
                assertNotNull(userSaved);
                assertNotNull(userSaved.getId());
                assertNotNull(specialistSaved);
                assertNotNull(specialistSaved.getSpecialistId());
                assertNotNull(specialistSpeciality);
                assertNotNull(specialistSpecialityFound);
                assertEquals(specialistSaved.getSpecialistId(),
                                specialistSpecialityFound.getSpecialist().getSpecialistId());
                assertEquals(specialitySaved.getId(), specialistSpecialityFound.getSpeciality().getId());
        }

        // rechercher une association specialist-speciality
        // cas 2: le spécialiste existent et la spécialité n'existe pas
        @Test
        void findSpecialistSpecialityByExistingSpecialistIdAndNotExistingSpecialityIdWithError()
                        throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // recherche de l'association créée
                Integer specialityId = 2;
                ClinicException e = assertThrows(ClinicException.class,
                                () -> specialistSpecialityService
                                                .findSpecialistSpecialityBySpecialistIdAndSpecialityId(
                                                                specialistSaved.getSpecialistId(), specialityId));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALITY_NOT_FOUND, e.getMessage());
        }

        // rechercher une association specialist-speciality
        // cas 3: le spécialiste n'existe pas et la spécialité existe
        @Test
        void findSpecialistSpecialityByNotExistingSpecialistIdAndExistingSpecialityIdWithError()
                        throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // recherche de l'association créée
                Integer specialistId = 2;
                ClinicException e = assertThrows(ClinicException.class, () -> specialistSpecialityService
                                .findSpecialistSpecialityBySpecialistIdAndSpecialityId(
                                                specialistId, specialitySaved.getId()));
                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
        }

        // rechercher une association specialist-speciality
        // cas 4: le spécialiste et la spécialité n'existent pas
        @Test
        void findSpecialistSpecialityByNotExistingSpecialistIdAndNotExistingSpecialityIdWithError()
                        throws ClinicException {
                // création d'un rôle SPECIALIST
                roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
                // création d'un utilisateur
                User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
                // recherche du spécialiste associé
                Specialist specialistSaved = specialistService.findByUserId(userSaved.getId());
                // Création d'une spécialité
                Speciality specialitySaved = specialityService.createOrUpdateSpeciality(initialiseSpeciality(true));
                // instanciation de l'association specialist-speciality minimale
                // correspondante
                SpecialistSpecialityMin specialistSpecialityMin = new SpecialistSpecialityMin(
                                specialistSaved.getSpecialistId(),
                                specialitySaved.getId());
                // création de l'association specialist-speciality
                SpecialistSpeciality specialistSpeciality = specialistSpecialityService
                                .createSpecialistSpeciality(specialistSpecialityMin);
                // recherche de l'association créée
                Integer specialistId = 2;
                Integer specialityId = 2;
                ClinicException e = assertThrows(ClinicException.class,
                                () -> specialistSpecialityService
                                                .findSpecialistSpecialityBySpecialistIdAndSpecialityId(
                                                                specialistId, specialityId));

                assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
                assertEquals(Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND, e.getMessage());
        }
}
