package com.ibrasoft.unitTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Setting;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.*;
import com.ibrasoft.storeStackProd.response.SettingDTO;
import com.ibrasoft.storeStackProd.service.ContactService;
import com.ibrasoft.storeStackProd.service.SettingService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SettingServiceImplementTest {

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
    SettingRepository settingRepository;

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
    PlaningRepository planingRepository;

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
        settingRepository.deleteAll();
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

    @Test
    void createSettingWithSuccess() throws ClinicException {

        Setting newSettings = new Setting(clinicLogoLocation, clinicOpeningHour, clinicClosingHour, defaultClinicEmail,
                clinicSecondaryEmail, defaultClinicAddress, defaultClinicPhone, clinicSecondaryPhone,
                clinicLinkedInLink, clinicFbLink, clinicTwitterLink, clinicInstaLink, clinicVideoLink,
                clinicVideoCoverLocation);
        Setting settingCreated;
        try {
            settingCreated = settingService.createOrUpdate(newSettings);
            assertNotNull(settingCreated);
            assertNotNull(settingCreated.getAdresse());
            assertNotNull(settingCreated.getEmail());
            assertNotNull(settingCreated.getEmail2());
            assertNotNull(settingCreated.getTel());
            assertNotNull(settingCreated.getTel2());
            assertNotNull(settingCreated.getId());
            assertNotNull(settingCreated.getFb());
            assertNotNull(settingCreated.getInsta());
            assertNotNull(settingCreated.getPlaningEndAt());
            assertNotNull(settingCreated.getPlaningStartAt());
            assertNotNull(settingCreated.getLogo());
            assertNotNull(settingCreated.getLn());
            assertNotNull(settingCreated.getTwitter());
            assertNotNull(settingCreated.getVideo());
            assertNotNull(settingCreated.getVideoCover());
        } catch (ClinicException e) {
            e.printStackTrace();
        }

    }

    @Test
    void createSettingFromDTOWithSuccess() throws ClinicException {
        Setting newSettings = new Setting(new SettingDTO(null, clinicLogoLocation, defaultClinicEmail,
                clinicSecondaryEmail, defaultClinicAddress, clinicTwitterLink, clinicLogoLocation, clinicLinkedInLink,
                clinicFbLink, clinicTwitterLink, clinicInstaLink, clinicOpeningHour, clinicClosingHour, clinicVideoLink,
                clinicVideoCoverLocation));
        Setting settingCreated;
        try {
            settingCreated = settingService.createOrUpdate(newSettings);
            assertNotNull(settingCreated);
            assertNotNull(settingCreated.getAdresse());
            assertNotNull(settingCreated.getEmail());
            assertNotNull(settingCreated.getEmail2());
            assertNotNull(settingCreated.getTel());
            assertNotNull(settingCreated.getTel2());
            assertNotNull(settingCreated.getId());
            assertNotNull(settingCreated.getFb());
            assertNotNull(settingCreated.getInsta());
            assertNotNull(settingCreated.getPlaningEndAt());
            assertNotNull(settingCreated.getPlaningStartAt());
            assertNotNull(settingCreated.getLogo());
            assertNotNull(settingCreated.getLn());
            assertNotNull(settingCreated.getTwitter());
            assertNotNull(settingCreated.getVideo());
            assertNotNull(settingCreated.getVideoCover());
        } catch (ClinicException e) {
            e.printStackTrace();
        }

    }

    /**
     * Cette focntion fait de test de record des setting et capture l'exection qui
     * pourait etre
     * remontee si jamais l'item renvoye est null.
     * 
     * @throws ClinicException
     */
    @Test
    void recordSettingWithSuccess() throws ClinicException {
        try {
            settingService.getSetting();
        } catch (ClinicException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette focntion fait de test de la suppression d'un setting et capture
     * l'exection qui pourait etre
     * remontee si jamais l'item n'est pas trouve dans la BD.
     * 
     * @throws ClinicException
     * @throws NumberFormatException
     */
    @Test
    void deleteSettingWithSuccess() throws NumberFormatException, ClinicException {
        try {
            Setting setting = settingService.delete(Long.parseLong("1"));
            assertNotNull(setting);
        } catch (ClinicException e) {
            e.printStackTrace();
        }
    }

}
