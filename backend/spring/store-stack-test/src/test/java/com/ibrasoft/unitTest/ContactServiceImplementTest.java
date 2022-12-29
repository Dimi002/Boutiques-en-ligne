package com.ibrasoft.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Assert;

import com.ibrasoft.storeStackProd.beans.Contact;
import com.ibrasoft.storeStackProd.beans.Setting;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.repository.*;
import com.ibrasoft.storeStackProd.response.ContactDTO;
import com.ibrasoft.storeStackProd.service.ContactService;
import com.ibrasoft.storeStackProd.service.SettingService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ContactServiceImplementTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactService contactService;

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

    @Autowired
    SettingService settingService;

    @Value("${app.clinic_logo_location}")
    String clinicLogoLocation;
    @Value("${app.clinic_opening_hour}")
    Integer clinicOpeningHour;
    @Value("${app.clinic_closing_hour}")
    Integer clinicClosingHour;
    @Value("${app.default_clinic_email}")
    String defaultClinicEmail;
    @Value("${app.clinic_secondary_email}")
    String clinicSecondaryEmail;
    @Value("${app.default_clinic_phone}")
    String defaultClinicPhone;
    @Value("${app.clinic_secondary_phone}")
    String clinicSecondaryPhone;
    @Value("${app.default_clinic_address}")
    String defaultClinicAddress;
    @Value("${app.clinic_fb_link}")
    String clinicFbLink;
    @Value("${app.clinic_lk_link}")
    String clinicLinkedInLink;
    @Value("${app.clinic_tt_link}")
    String clinicTwitterLink;
    @Value("${app.clinic_insta_link}")
    String clinicInstaLink;
    @Value("${app.clinic_video_link}")
    String clinicVideoLink;
    @Value("${app.clinic_video_cover_location}")
    String clinicVideoCoverLocation;

    @BeforeEach
    public void deleteAll() {

        // l'ordre ici est crutial!

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

    /**
     * Recuperer les contacts avec initialisation de la table settings.
     * dans le but de favoriser l'envoi de mail avec les donnes en BD.
     * 
     * @throws ClinicException
     */
    @Test
    void getAllContactsWithInitSettings() throws ClinicException {

        // initialisation de la table settings

        Setting stettings;
        try {
            stettings = settingService.getSetting();
            if (stettings == null) {
                Setting newSettings = new Setting(clinicLogoLocation, clinicOpeningHour, clinicClosingHour,
                        defaultClinicEmail, clinicSecondaryEmail, defaultClinicAddress, defaultClinicPhone,
                        clinicSecondaryPhone, clinicLinkedInLink, clinicFbLink, clinicTwitterLink, clinicInstaLink,
                        clinicVideoLink, clinicVideoCoverLocation);
                settingService.createOrUpdate(newSettings);
            }
        } catch (ClinicException e) {
            e.printStackTrace();
        }

        // on cree un nouveau contact et on envoie un email juste apres
        // grace a la premiere entree de la table settings

        try {
            contactService.create(
                    new ContactDTO(
                            Long.valueOf("1"),
                            "maestros@gmail.com",
                            "Maestros",
                            "Prise de rendez-vous",
                            "Bonjour Je souhaite faire un rendez-vous avec vous",
                            new Date()));
            List<Contact> contactsList = contactService.recods();
            contactsList.forEach(contact -> System.out.println(contact));

            assertNotNull(contactsList);
            Assert.assertTrue(contactsList.size() == 1);
        } catch (NumberFormatException | ClinicException e) {
            e.printStackTrace();
        }

    }

    /**
     * Recuperer les contacts sans l'initialisation de la table settings.
     * dans le but de favoriser l'envoi de mail avec les donnees par defaut.
     * 
     * @throws ClinicException
     */
    @Test
    void getAllContactsWithoutInitSettings() throws ClinicException {

        // on cree un nouveau contact et on envoie un email juste apres
        // grace a la premiere entree de la table settings

        try {
            contactService.create(
                    new ContactDTO(
                            Long.valueOf("1"),
                            "maestros@gmail.com",
                            "Maestros",
                            "Prise de rendez-vous",
                            "Bonjour Je souhaite faire un rendez-vous avec vous",
                            new Date()));
            List<Contact> contactsList = contactService.recods();
            contactsList.forEach(contact -> System.out.println(contact));

            assertNotNull(contactsList);
            Assert.assertTrue(contactsList.size() == 1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ClinicException e) {
            e.printStackTrace();
        }

    }

    /**
     * Cette focntion fait de test de record des contact et capture l'exection qui
     * pourait etre
     * remontee si jamais le tableau renvoye est null.
     * 
     * @throws ClinicException
     */
    @Test
    void recordSettingWithSuccess() throws ClinicException {
        try {
            List<Contact> contacts = contactService.recods();
            assertNotNull(contacts);
        } catch (ClinicException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette focntion fait de test de la suppression d'un contact et capture
     * l'exection qui pourait etre
     * remontee si jamais l'item n'est pas trouve dans la BD.
     * 
     * @throws ClinicException
     * @throws NumberFormatException
     */
    @Test
    void deleteSettingWithSuccess() throws NumberFormatException, ClinicException {
        try {
            Contact contacts = contactService.delete(Long.parseLong("1"));
            assertNotNull(contacts);
        } catch (ClinicException e) {
            e.printStackTrace();
        }
    }

}
