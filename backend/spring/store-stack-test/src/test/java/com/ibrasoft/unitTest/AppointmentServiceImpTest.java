package com.ibrasoft.unitTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.models.EnumStatus;
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
import com.ibrasoft.storeStackProd.response.PlaningDTO;
import com.ibrasoft.storeStackProd.service.AppointmentService;
import com.ibrasoft.storeStackProd.service.PlaningService;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.util.Constants;

import org.junit.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AppointmentServiceImpTest {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    SpecialistService specialistService;

    @Autowired
    PlaningService planingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // Instancier une date aleatoire
    public Date generatRandomDate(String date) throws ParseException {
        Date dateToOutput = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
        dateToOutput = formatter.parse(date);
        return dateToOutput;
    }

    // genere une chaine de caractere aleatoire
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

    // instancier un utilisateur aleatoire
    public User buildRandomUser(Boolean login, Boolean email, Boolean password) {
        User expectedUser = new User();
        String defaultEmail = "dongmoalex59@gmail.com";
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

    // instancier un role aleatoire
    public Role buildRandomRole(String roleName) {
        Role expectedRole = new Role();
        expectedRole.setRoleDesc(generatRandomString(20));
        expectedRole.setRoleName(roleName);
        expectedRole.setStatus(Constants.STATE_ACTIVATED);
        return expectedRole;
    }

    // instancier un rendez-vous aleatoire
    public Appointment buildRandomAppointment(Boolean date, Boolean hour, Boolean phone, Boolean email,
            Boolean originalHour, String appointmentDate, String appointmentHour, String patientPhone,
            String patientEmail,
            String originalHourString) throws ParseException {

        Appointment appointment = new Appointment();
        String defaultAppointmentHour = "28/10/2022 08:00:18 AM";
        String defaultAppointmentDate = "28/10/2022 08:00:18 AM";
        String defaultPhone = "681190361";
        String defaultEmail = "dongmoalex59@gmail.com";
        String defaultOriginalAppointmentHour = "08:00 AM";

        if (date) {
            appointment.setAppointmentDate(generatRandomDate(appointmentDate));
        } else {
            appointment.setAppointmentDate(generatRandomDate(defaultAppointmentDate));
        }
        if (hour) {
            appointment.setAppointmentHour(generatRandomDate(appointmentHour));
        } else {
            appointment.setAppointmentHour(generatRandomDate(defaultAppointmentHour));
        }
        if (phone) {
            appointment.setPatientPhone(patientPhone);
        } else {
            appointment.setPatientPhone(defaultPhone);
        }
        if (email) {
            appointment.setPatientEmail(patientEmail);
        } else {
            appointment.setPatientEmail(defaultEmail);
        }
        if (originalHour) {
            appointment.setOriginalAppointmentHour(originalHourString);
        } else {
            appointment.setOriginalAppointmentHour(defaultOriginalAppointmentHour);
        }
        appointment.setPatientName(generatRandomString(8));
        appointment.setPatientMessage(generatRandomString(15));
        appointment.setStatus(Constants.STATE_ACTIVATED);
        return appointment;
    }

    // instancier un planning aleatoire
    public PlaningDTO buildRandomPlanning(Boolean start, Boolean end, Boolean planDay, String inputStart,
            String inputEnd,
            int inputDay,
            Specialist specialist) {
        PlaningDTO planing = new PlaningDTO();
        String deFaultStart = "08:00 AM";
        String defaultEnd = "08:30 AM";
        int defaultDay = 1;
        if (start) {
            planing.setStartTime(inputStart);
        } else {
            planing.setStartTime(deFaultStart);
        }
        if (end) {
            planing.setEndTime(inputEnd);
        } else {
            planing.setEndTime(defaultEnd);
        }
        if (planDay) {
            planing.setPlanDay(inputDay);
        } else {
            planing.setPlanDay(defaultDay);
        }
        planing.setSpecialist(specialist);
        return planing;
    }

    @Test
    void getAllAppointmentWithEmptyArray() {
        List<Appointment> appointments = appointmentService.getAllAppointment();
        Assert.assertTrue(appointments.isEmpty());
    }

    // Création d'un rendez-vous avec succès
    // le specialiste choisi existe bien, il est libre, il est actif
    // les informations du rendez sont complets
    @Test
    void createAppointmentWithSuccess()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
        assertNotNull(appointmentSaved);
        assertNotNull(appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getAppointmentId(), appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getState(), appointmentSaved.getState());
        assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
        assertEquals(expectedAppointment.getAppointmentDate(), appointmentSaved.getAppointmentDate());
        assertEquals(expectedAppointment.getAppointmentHour(), appointmentSaved.getAppointmentHour());
        assertEquals(expectedAppointment.getPatientName(), appointmentSaved.getPatientName());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getPatientMessage(), appointmentSaved.getPatientMessage());
        assertEquals(expectedAppointment.getStatus(), appointmentSaved.getStatus());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getLastUpdateOn(), appointmentSaved.getLastUpdateOn());
        assertEquals(expectedAppointment.getSpecialistId(), appointmentSaved.getSpecialistId());
        assertEquals(specialistFound.getSpecialistId(), appointmentSaved.getSpecialistId().getSpecialistId());
    }

    // Création d'un rendez-vous avec echec
    // le specialiste choisi existe bien,il est actif
    // Mais il n'est pas libre
    @Test
    void createAppointmentWithNotFreeSpecialistWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Creation d'un Planning pour le spécialiste
        List<PlaningDTO> planingDTOs = new ArrayList<>();
        PlaningDTO planingDTO = buildRandomPlanning(false, false, true, "", "", 5, specialistFound);
        planingDTOs.add(planingDTO);
        Boolean planingSaved = planingService.create(planingDTOs);
        // Instanciation du rendez-vous dans un plage de repos du
        // specialiste
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);

        // Verifications
        assertTrue(true == planingSaved);
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.SLOT_TIME_NOT_FREE, e.getCode());
        assertEquals(Constants.TIME_NOT_FREE, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le specialiste choisi existe bien,il est desactive
    @Test
    void createAppointmentWithDeactivatedSpecialistWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation de 2 utilisateurs
        User userSaved1 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        User userSaved2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), true, true);
        // recherche des specialistes associes aux utilisateurs
        Specialist specialistFound1 = specialistService.findByUserId(userSaved1.getId());
        Specialist specialistFound2 = specialistService.findByUserId(userSaved2.getId());
        // desactivation du premier specialiste
        specialistFound1.setStatus(Constants.STATE_DEACTIVATED);
        Specialist specialistUpdated = specialistService.createOrUpdateSpecialist(specialistFound1);
        // Instanciation du rendez-vous avec
        // le specialiste desactive
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound1);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved1);
        assertNotNull(userSaved1.getId());
        assertNotNull(userSaved2);
        assertNotNull(userSaved2.getId());
        assertNotNull(specialistFound1);
        assertNotNull(specialistFound1.getSpecialistId());
        assertNotNull(specialistFound2);
        assertNotNull(specialistFound2.getSpecialistId());
        assertNotNull(specialistUpdated);
        assertNotNull(specialistUpdated.getSpecialistId());
        assertEquals(specialistFound1.getSpecialistId(), specialistUpdated.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound2.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialistUpdated.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_ALREADY_DEACTIVATED, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_DEACTIVATED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le specialiste choisi existe bien,il est supprime
    @Test
    void createAppointmentWithDeletedSpecialistWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation de 2 utilisateurs
        User userSaved1 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        User userSaved2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), true, true);
        // recherche des specialistes associes aux utilisateurs
        Specialist specialistFound1 = specialistService.findByUserId(userSaved1.getId());
        Specialist specialistFound2 = specialistService.findByUserId(userSaved2.getId());
        // suppression du premier specialiste
        specialistFound1.setStatus(Constants.STATE_DELETED);
        Specialist specialistDeleted = specialistService.createOrUpdateSpecialist(specialistFound1);
        // Instanciation du rendez-vous avec
        // le specialiste supprime
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound1);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved1);
        assertNotNull(userSaved1.getId());
        assertNotNull(userSaved2);
        assertNotNull(userSaved2.getId());
        assertNotNull(specialistFound1);
        assertNotNull(specialistFound1.getSpecialistId());
        assertNotNull(specialistFound2);
        assertNotNull(specialistFound2.getSpecialistId());
        assertNotNull(specialistDeleted);
        assertNotNull(specialistDeleted.getSpecialistId());
        assertEquals(specialistFound1.getSpecialistId(), specialistDeleted.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound2.getStatus());
        assertEquals(Constants.STATE_DELETED, specialistDeleted.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_DELETED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // Aucun spécialiste n'est actif
    @Test
    void createAppointmentWithAllNotActiveSpecialistsWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation de 2 utilisateurs
        User userSaved1 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        User userSaved2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), true, true);
        // recherche des specialistes associes aux utilisateurs
        Specialist specialistFound1 = specialistService.findByUserId(userSaved1.getId());
        Specialist specialistFound2 = specialistService.findByUserId(userSaved2.getId());
        // desactivation des 2 specialistes
        specialistFound1.setStatus(Constants.STATE_DEACTIVATED);
        specialistFound2.setStatus(Constants.STATE_DEACTIVATED);
        Specialist specialistUpdated1 = specialistService.createOrUpdateSpecialist(specialistFound1);
        Specialist specialistUpdated2 = specialistService.createOrUpdateSpecialist(specialistFound2);
        // Instanciation d'un rendez-vous avec l'un des specialistes
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound1);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved1);
        assertNotNull(userSaved1.getId());
        assertNotNull(userSaved2);
        assertNotNull(userSaved2.getId());
        assertNotNull(specialistFound1);
        assertNotNull(specialistFound1.getSpecialistId());
        assertNotNull(specialistFound2);
        assertNotNull(specialistFound2.getSpecialistId());
        assertNotNull(specialistUpdated1);
        assertNotNull(specialistUpdated1.getSpecialistId());
        assertNotNull(specialistUpdated2);
        assertNotNull(specialistUpdated2.getSpecialistId());
        assertEquals(specialistFound1.getSpecialistId(), specialistUpdated1.getSpecialistId());
        assertEquals(specialistFound2.getSpecialistId(), specialistUpdated2.getSpecialistId());
        assertEquals(Constants.STATE_DEACTIVATED, specialistUpdated1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, specialistUpdated2.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.NOT_ACTIVE_SPECIALIST, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // un autre patient a deja pris rendez-vous
    // a l'heure correspondante
    @Test
    void createAppointmentAtAlreadyTakeTimeWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du premier rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
        assertNotNull(appointmentSaved);
        assertNotNull(appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getAppointmentId(), appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getState(), appointmentSaved.getState());
        assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
        assertEquals(expectedAppointment.getAppointmentDate(), appointmentSaved.getAppointmentDate());
        assertEquals(expectedAppointment.getAppointmentHour(), appointmentSaved.getAppointmentHour());
        assertEquals(expectedAppointment.getPatientName(), appointmentSaved.getPatientName());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getPatientMessage(), appointmentSaved.getPatientMessage());
        assertEquals(expectedAppointment.getStatus(), appointmentSaved.getStatus());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getLastUpdateOn(), appointmentSaved.getLastUpdateOn());
        assertEquals(expectedAppointment.getSpecialistId(), appointmentSaved.getSpecialistId());
        assertEquals(specialistFound.getSpecialistId(), appointmentSaved.getSpecialistId().getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.SLOT_TIME_NOT_FREE, e.getCode());
        assertEquals(Constants.TIME_NOT_FREE, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le specialiste n'existe pas en BD
    @Test
    void createAppointmentWithNotFoundSpecialistWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a cet utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du specialiste inexistant
        Specialist specialistNotFound = new Specialist();
        specialistNotFound.setBiography(generatRandomString(20));
        specialistNotFound.setSpecialistId(2);
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste inexistant
        expectedAppointment.setSpecialistId(specialistNotFound);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le nom du patient est null
    @Test
    void createAppointmentWithNullablePatientNameWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons le nom du patient avec la valeur null
        expectedAppointment.setPatientName(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.PATIENT_NAME_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // l'adresse email du patient est null
    @Test
    void createAppointmentWithNullablePatientEmailWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons l'adresse email du patient avec la valeur null
        expectedAppointment.setPatientEmail(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.PATIENT_EMAIL_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le numero de telephone du patient est null
    @Test
    void createAppointmentWithNullablePatientPhoneWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons le numero du patient avec la valeur null
        expectedAppointment.setPatientPhone(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.PATIENT_PHONE_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // l'heure effective du rendez-vous est null
    @Test
    void createAppointmentWithNullableOriginalAppointmentHourWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons l'heure effective du rendez-vous avec la valeur null
        expectedAppointment.setOriginalAppointmentHour(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // l'heure du rendez-vous est null
    @Test
    void createAppointmentWithNullableAppointmentHourWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons l'heure du rendez-vous avec la valeur null
        expectedAppointment.setAppointmentHour(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.APPOINTMENT_HOUR_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // la date du rendez-vous est null
    @Test
    void createAppointmentWithNullableAppointmentDateWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons la date du rendez-vous avec la valeur null
        expectedAppointment.setAppointmentDate(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.APPOINTMENT_DATE_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // l'heure effective du rendez-vous est null
    @Test
    void createAppointmentWithNullablePatientMessageWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons l'heure effective du rendez-vous avec la valeur null
        expectedAppointment.setPatientMessage(null);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.ITEM_IS_REQUIRED, e.getCode());
        assertEquals(Constants.PATIENT_MESSAGE_IS_REQUIRED, e.getMessage());
    }

    // Création d'un rendez-vous avec echec
    // le format de l'heure effective du rendez-vou n'est "HH:MM AM | PM"
    @Test
    void createAppointmentWithWrongHourFormatWithError()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // Settons un mauvais format de l'heure effective du rendez-vous
        expectedAppointment.setOriginalAppointmentHour("10:00  AM");
        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.createOrUpdateAppointment(expectedAppointment));
        assertEquals(Constants.INVALID_INPUT, e.getCode());
        assertEquals(Constants.HOUR_PATTERN_NOT_MATCHES, e.getMessage());
    }

    // Renvoyer la liste des rendez-vous actifs
    // ou non actif
    @Test
    void getAllAppointmentWithSucces() throws ClinicException, ParseException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
                false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation de 2 rendez-vous
        Appointment expectedAppointment1 = buildRandomAppointment(false, false,
                false, false, false, "", "", "", "", "");
        Appointment expectedAppointment2 = buildRandomAppointment(true, true,
                false, false, false, "31/10/2022 08:00:18 AM", "31/10/2022 08:00:18 AM", "", "", "");
        // affectation des 2 rendez-vous au specialiste
        expectedAppointment1.setSpecialistId(specialistFound);
        expectedAppointment2.setSpecialistId(specialistFound);
        // creation des rendez-vous
        Appointment appointmentSaved1 = appointmentService.createOrUpdateAppointment(expectedAppointment1);
        Appointment appointmentSaved2 = appointmentService.createOrUpdateAppointment(expectedAppointment2);
        // desativation du second rendez-vous
        appointmentRepository.updateAppointmentStatus(appointmentSaved2.getAppointmentId(),
                Constants.STATE_DEACTIVATED);
        // recherche du second rendez-vous apres modification
        Appointment appointmentUpdated = appointmentService.findAppointmentById(appointmentSaved2.getAppointmentId());
        // appel de la fonction a tester
        List<Appointment> appointments = appointmentService.getAllAppointment();

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(appointmentSaved1);
        assertNotNull(appointmentSaved1.getAppointmentId());
        assertNotNull(appointmentSaved2);
        assertNotNull(appointmentSaved2.getAppointmentId());
        assertNotNull(appointmentUpdated);
        assertNotNull(appointmentUpdated.getAppointmentId());
        assertEquals(Constants.STATE_DEACTIVATED, appointmentUpdated.getStatus());
        assertEquals(appointmentSaved2.getAppointmentId(), appointmentUpdated.getAppointmentId());
        assertNotNull(appointments);
        assertEquals(2, appointments.size());
    }

    // Renvoyer la liste des rendez-vous actifs
    @Test
    void getAllActiveAppointmentWithSucces() throws ClinicException, ParseException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
                false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation de 2 rendez-vous
        Appointment expectedAppointment1 = buildRandomAppointment(false, false,
                false, false, false, "", "", "", "", "");
        Appointment expectedAppointment2 = buildRandomAppointment(true, true,
                false, false, false, "31/10/2022 08:00:18 AM", "31/10/2022 08:00:18 AM", "", "", "");
        // affectation des 2 rendez-vous au specialiste
        expectedAppointment1.setSpecialistId(specialistFound);
        expectedAppointment2.setSpecialistId(specialistFound);
        // creation des rendez-vous
        Appointment appointmentSaved1 = appointmentService.createOrUpdateAppointment(expectedAppointment1);
        Appointment appointmentSaved2 = appointmentService.createOrUpdateAppointment(expectedAppointment2);
        // desativation du second rendez-vous
        appointmentRepository.updateAppointmentStatus(appointmentSaved2.getAppointmentId(),
                Constants.STATE_DEACTIVATED);
        // recherche du second rendez-vous apres modification
        Appointment appointmentUpdated = appointmentService.findAppointmentById(appointmentSaved2.getAppointmentId());
        // appel de la fonction a tester
        List<Appointment> appointments = appointmentService.getActiveAppointment();

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(appointmentSaved1);
        assertNotNull(appointmentSaved1.getAppointmentId());
        assertNotNull(appointmentSaved2);
        assertNotNull(appointmentSaved2.getAppointmentId());
        assertNotNull(appointmentUpdated);
        assertNotNull(appointmentUpdated.getAppointmentId());
        assertEquals(Constants.STATE_DEACTIVATED, appointmentUpdated.getStatus());
        assertEquals(Constants.STATE_ACTIVATED, appointmentSaved1.getStatus());
        assertEquals(appointmentSaved2.getAppointmentId(), appointmentUpdated.getAppointmentId());
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
    }

    // lister les rendez-vous actifs
    // il n'y a pas aucun rendez-vous enregistré
    @Test
    void getAllActiveAppointmentWithEmptyArray() {
        List<Appointment> appointments = appointmentService.getActiveAppointment();
        assertTrue(appointments.isEmpty());
    }

    // supprimer un rendez-vous existant
    @Test
    void deleteAppointmentWithSuccess()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
        assertNotNull(appointmentSaved);
        assertNotNull(appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getAppointmentId(), appointmentSaved.getAppointmentId());
        assertEquals(expectedAppointment.getState(), appointmentSaved.getState());
        assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
        assertEquals(expectedAppointment.getAppointmentDate(), appointmentSaved.getAppointmentDate());
        assertEquals(expectedAppointment.getAppointmentHour(), appointmentSaved.getAppointmentHour());
        assertEquals(expectedAppointment.getPatientName(), appointmentSaved.getPatientName());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getPatientMessage(), appointmentSaved.getPatientMessage());
        assertEquals(expectedAppointment.getStatus(), appointmentSaved.getStatus());
        assertEquals(expectedAppointment.getCreatedOn(), appointmentSaved.getCreatedOn());
        assertEquals(expectedAppointment.getLastUpdateOn(), appointmentSaved.getLastUpdateOn());
        assertEquals(expectedAppointment.getSpecialistId(), appointmentSaved.getSpecialistId());
        assertEquals(specialistFound.getSpecialistId(), appointmentSaved.getSpecialistId().getSpecialistId());
        // suppression
        Appointment appointmentDeleted = appointmentService.deleteAppointment(appointmentSaved.getAppointmentId());
        assertNotNull(appointmentDeleted);
        assertNotNull(appointmentDeleted.getAppointmentId());
        assertEquals(appointmentSaved.getAppointmentId(), appointmentDeleted.getAppointmentId());
        assertEquals(Constants.STATE_DELETED, appointmentDeleted.getStatus());
    }

    // supprimer un rendez-vous inexistant
    @Test
    void deleteNotExistingAppointmentWithError()
            throws ClinicException {
        // id du rendez-vous
        Integer appointmentId = 1;
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.deleteAppointment(appointmentId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.APPOINTMENT_NOT_FOUND, e.getMessage());
    }

    // Rechercher un rendez-vous existing par son Id
    @Test
    void findAppointmentByIdWithSuccess()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);
        // appel de la fonction à tester
        Appointment appointmentFound = appointmentService.findAppointmentById(appointmentSaved.getAppointmentId());
        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
        assertNotNull(appointmentSaved);
        assertNotNull(appointmentSaved.getAppointmentId());
        assertNotNull(appointmentFound);
        assertNotNull(appointmentFound.getAppointmentId());
        assertEquals(appointmentSaved.getAppointmentId(), appointmentFound.getAppointmentId());
        assertEquals(appointmentSaved.getState(), appointmentFound.getState());
        assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
        assertEquals(appointmentSaved.getAppointmentDate(), appointmentFound.getAppointmentDate());
        assertEquals(appointmentSaved.getAppointmentHour(), appointmentFound.getAppointmentHour());
        assertEquals(appointmentSaved.getPatientName(), appointmentFound.getPatientName());
        assertEquals(appointmentSaved.getCreatedOn(), appointmentFound.getCreatedOn());
        assertEquals(appointmentSaved.getPatientMessage(), appointmentFound.getPatientMessage());
        assertEquals(appointmentSaved.getStatus(), appointmentFound.getStatus());
        assertEquals(appointmentSaved.getCreatedOn(), appointmentFound.getCreatedOn());
        assertEquals(appointmentSaved.getLastUpdateOn(), appointmentFound.getLastUpdateOn());
        assertEquals(appointmentSaved.getSpecialistId().getSpecialistId(),
                appointmentFound.getSpecialistId().getSpecialistId());
        assertEquals(specialistFound.getSpecialistId(), appointmentFound.getSpecialistId().getSpecialistId());
    }

    // Rechercher un rendez-vous inexistant par son id
    @Test
    void findNotExistingAppointmentByWithError()
            throws ClinicException {
        // id du rendez-vous
        Integer appointmentId = 1;
        ClinicException e = assertThrows(ClinicException.class,
                () -> appointmentService.findAppointmentById(appointmentId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.APPOINTMENT_NOT_FOUND, e.getMessage());
    }

    // Renvoyer la liste des rendez-vous archives
    @Test
    void getAllArchiveAppointmentWithSucces() throws ClinicException, ParseException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
                false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation de 2 rendez-vous
        Appointment expectedAppointment1 = buildRandomAppointment(false, false,
                false, false, false, "", "", "", "", "");
        Appointment expectedAppointment2 = buildRandomAppointment(true, true,
                false, false, false, "31/10/2022 08:00:18 AM", "31/10/2022 08:00:18 AM", "", "", "");
        // affectation des 2 rendez-vous au specialiste
        expectedAppointment1.setSpecialistId(specialistFound);
        expectedAppointment2.setSpecialistId(specialistFound);
        // creation des rendez-vous
        Appointment appointmentSaved1 = appointmentService.createOrUpdateAppointment(expectedAppointment1);
        Appointment appointmentSaved2 = appointmentService.createOrUpdateAppointment(expectedAppointment2);
        // archivation du second rendez-vous
        appointmentRepository.updateAppointmentStatus(appointmentSaved2.getAppointmentId(),
                Constants.STATE_ARCHIVE);
        // recherche du second rendez-vous apres modification
        Appointment appointmentUpdated = appointmentService.findAppointmentById(appointmentSaved2.getAppointmentId());
        // appel de la fonction a tester
        List<Appointment> appointments = appointmentService.getAllArchivedAppointment();

        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(appointmentSaved1);
        assertNotNull(appointmentSaved1.getAppointmentId());
        assertNotNull(appointmentSaved2);
        assertNotNull(appointmentSaved2.getAppointmentId());
        assertNotNull(appointmentUpdated);
        assertNotNull(appointmentUpdated.getAppointmentId());
        assertEquals(Constants.STATE_ARCHIVE, appointmentUpdated.getStatus());
        assertEquals(Constants.STATE_ACTIVATED, appointmentSaved1.getStatus());
        assertEquals(appointmentSaved2.getAppointmentId(), appointmentUpdated.getAppointmentId());
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
    }

    // lister les rendez-vous archives
    // cas ou il n'y a pas aucun rendez-vous archive
    @Test
    void getAllArchiveAppointmentWithEmptyArray() {
        List<Appointment> appointments = appointmentService.getAllArchivedAppointment();
        assertTrue(appointments.isEmpty());
    }

    // Rechercher un rendez-vous existing par son specialiste
    @Test
    void findAppointmentBySpecialistWithSuccess()
            throws ParseException, ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);
        // appel de la fonction à tester
        Appointment appointmentFound = appointmentService.findBySpecialistId(specialistFound);
        // Verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistFound.getStatus());
        assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
        assertNotNull(appointmentSaved);
        assertNotNull(appointmentSaved.getAppointmentId());
        assertNotNull(appointmentFound);
        assertNotNull(appointmentFound.getAppointmentId());
        assertEquals(appointmentSaved.getAppointmentId(), appointmentFound.getAppointmentId());
        assertEquals(appointmentSaved.getState(), appointmentFound.getState());
        assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
        assertEquals(appointmentSaved.getAppointmentDate(), appointmentFound.getAppointmentDate());
        assertEquals(appointmentSaved.getAppointmentHour(), appointmentFound.getAppointmentHour());
        assertEquals(appointmentSaved.getPatientName(), appointmentFound.getPatientName());
        assertEquals(appointmentSaved.getCreatedOn(), appointmentFound.getCreatedOn());
        assertEquals(appointmentSaved.getPatientMessage(), appointmentFound.getPatientMessage());
        assertEquals(appointmentSaved.getStatus(), appointmentFound.getStatus());
        assertEquals(appointmentSaved.getCreatedOn(), appointmentFound.getCreatedOn());
        assertEquals(appointmentSaved.getLastUpdateOn(), appointmentFound.getLastUpdateOn());
        assertEquals(appointmentSaved.getSpecialistId().getSpecialistId(),
                appointmentFound.getSpecialistId().getSpecialistId());
        assertEquals(specialistFound.getSpecialistId(), appointmentFound.getSpecialistId().getSpecialistId());
    }

    // liste des rendez-vous de la journee
    @Test
    void getAllTodayAppointmentWithSucces() throws ClinicException, ParseException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
                false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // instanciation et creation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false,
                false, false, false, "", "", "", "", "");
        expectedAppointment.setAppointmentDate(new Date());
        expectedAppointment.setAppointmentHour(new Date());
        expectedAppointment.setSpecialistId(specialistFound);
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);
        // recherche des rendez-vous de la journée
        Date date = new Date();
        date.setHours(00);
        date.setMinutes(00);
        date.setSeconds(00);
        Date date1 = new Date();
        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);
        List<Appointment> appointments = appointmentService.getAllTodayAppointment(date,
                appointmentSaved.getSpecialistId(), date1);

        assertNotNull(appointments);
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertEquals(1, appointments.size());
    }

    // lister les rendez-vous d'un specialiste
    @Test
    void getAllAppointmentSpecialistWithSucces() throws ClinicException, ParseException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
                false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());
        // Instanciation du rendez-vous
        Appointment expectedAppointment = buildRandomAppointment(false, false, false, false, false, "", "", "", "", "");
        // affectation du rendez-vous au specialiste
        expectedAppointment.setSpecialistId(specialistFound);
        // creation du rendez-vous
        Appointment appointmentSaved = appointmentService.createOrUpdateAppointment(expectedAppointment);

        List<Appointment> appointment = appointmentService
                .getAllAppointmentSpecialist(appointmentSaved.getSpecialistId());

        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(appointment);
        assertFalse(appointment.isEmpty());
        assertEquals(1, appointment.size());
    }

    // // nombre de docteur, rendez vous sur le dashbord

    // @Test
    // void getCountDoctorDashbordWithSucces() throws ClinicException,
    // ParseException {
    // // creation du role SPECIALIST
    // Role roleSaved =
    // roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
    // // creation d'un utilisateur en lui attribuant le role de SPECIALIST
    // User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false,
    // false, false), false, true);
    // // recherche du specialiste associe a notre nouvel utilisateur
    // Specialist specialistFound =
    // specialistService.findByUserId(userSaved.getId());
    // // Instanciation du rendez-vous
    // Appointment expectedAppointment = buildRandomAppointment(false, false, false,
    // false, false, "", "", "", "", "");
    // // affectation du rendez-vous au specialiste
    // expectedAppointment.setSpecialistId(specialistFound);
    // // creation du rendez-vous
    // Appointment appointmentSaved =
    // appointmentService.createOrUpdateAppointment(expectedAppointment);

    // CountDoctorDashbord countDoctorDashbord = appointmentService

    // assertNotNull(roleSaved);
    // assertNotNull(roleSaved.getRoleId());
    // assertNotNull(userSaved);
    // assertNotNull(userSaved.getId());
    // assertNotNull(specialistFound);
    // assertNotNull(specialistFound.getSpecialistId());
    // assertEquals(userSaved.getId(), specialistFound.getUserId().getId());
    // assertNotNull(appointmentSaved);
    // assertNotNull(appointmentSaved.getAppointmentId());
    // assertEquals(expectedAppointment.getAppointmentId(),
    // appointmentSaved.getAppointmentId());
    // assertEquals(expectedAppointment.getState(), appointmentSaved.getState());
    // assertEquals(EnumStatus.ACCEPTED.getStatus(), appointmentSaved.getState());
    // assertEquals(expectedAppointment.getAppointmentDate(),
    // appointmentSaved.getAppointmentDate());
    // assertEquals(expectedAppointment.getAppointmentHour(),
    // appointmentSaved.getAppointmentHour());
    // assertEquals(expectedAppointment.getPatientName(),
    // appointmentSaved.getPatientName());
    // assertEquals(expectedAppointment.getCreatedOn(),
    // appointmentSaved.getCreatedOn());
    // assertEquals(expectedAppointment.getPatientMessage(),
    // appointmentSaved.getPatientMessage());
    // assertEquals(expectedAppointment.getStatus(), appointmentSaved.getStatus());
    // assertEquals(expectedAppointment.getCreatedOn(),
    // appointmentSaved.getCreatedOn());
    // assertEquals(expectedAppointment.getLastUpdateOn(),
    // appointmentSaved.getLastUpdateOn());
    // assertEquals(expectedAppointment.getSpecialistId(),
    // appointmentSaved.getSpecialistId());
    // }
}