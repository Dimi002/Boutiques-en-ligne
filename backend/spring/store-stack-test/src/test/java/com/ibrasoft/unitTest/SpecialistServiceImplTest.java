package com.ibrasoft.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.SocialMediaLinks;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
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
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.JsonSerializer;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpecialistServiceImplTest {

    @Autowired
    SpecialistService specialistService;

    @Autowired
    private UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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
        // l'ordre ici est crutial!
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

    // cette m??thode cr??e un utilisateur al??atoire
    public User buildRandomUser(Boolean login, Boolean email, Boolean password) {
        User expectedUser = new User();
        String defaultEmail = "dongmo@gmail.com";
        String defaultPassword = "alex";
        String defaultlogin = "alex";
        expectedUser.setBirthDate(new java.util.Date());
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
        expectedUser.setCreatedOn(new java.util.Date());
        expectedUser.setFirstName(generatRandomString(6));
        expectedUser.setLastName(generatRandomString(4));
        expectedUser.setPhone(generatRandomString(9));
        if (login)
            expectedUser.setUsername(defaultlogin);
        else
            expectedUser.setUsername(generatRandomString(4));
        return expectedUser;
    }

    // cette m??thode cr??e un sp??cialiste al??atoire
    public Specialist buildRandomSpecialist() {
        Specialist specialist = new Specialist();

        specialist.setBiography(generatRandomString(20));
        specialist.setCity(generatRandomString(5));
        specialist.setGender(generatRandomString(5));
        specialist.setYearOfExperience(4);

        return specialist;
    }

    // cette m??thode cr??e un r??le al??atoire
    public Role buildRandomRole(String name, Boolean description) {
        Role expectedRole = new Role();
        if (description)
            expectedRole.setRoleDesc("le super administrateur");
        else
            expectedRole.setRoleDesc(generatRandomString(20));
        expectedRole.setRoleName(name);
        expectedRole.setStatus(Constants.STATE_ACTIVATED);
        return expectedRole;
    }

    // cette m??thode cr??e un objet contenant les liens vers les r??seaux sociaux
    // d'un sp??cialiste
    public SocialMediaLinks buildSocialMediaLinks() {
        SocialMediaLinks socialMediaLinks = new SocialMediaLinks(
                "https://facebook.com",
                "https://Instagram.com",
                "https://Twitter.com",
                "https://Linkedin.com",
                "https://Pinterest.com",
                "https://Youtube.com");
        return socialMediaLinks;
    }

    // cr??ation d'un sp??cialiste ?? partir d'un utilisateur ayant
    // le profil SPECIALIST
    @Test
    void createSpecialistWithSpecialistUserWithError() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'un l'utilisateur ayant le profil SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr??e
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);

        // Si un utilisateur nouvellement cr???? ?? le profil SPECIALIST, alors
        // le sp??cialiste associ?? ?? cet utilisateur est automatiquement cr????
        // au m??me moment.
        // c??l?? implique que si on tente de cr??er encore ce sp??cialiste en
        // utilisant l'id de l'utilisateur cr????, alors une exception sera l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_EXIST, e.getMessage());
    }

    // cr??ation d'un sp??cialiste ?? partir utilisateur ayant les
    // profils ADMIN et SPECIALIST
    @Test
    void createSpecialistWithAdminSpecialistUserWithError() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation du r??le ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // cr??ation de l'utilisateur associ?? au sp??cialiste
        // cet utilisateur est ?? la fois ADMIN et SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, true);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr????
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // une exception sera l??v??e puisque il existe d??j?? en BD un sp??cialiste
        // qui a l'id de notre utilisateur.
        // NB: ce sp??cialiste est cr???? dynamiquement lorsqu'on d??cide que
        // l'utilisateur qu'on va enregistrer aura le profil SPECIALIST

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_EXIST, e.getMessage());
    }

    // cr??ation d'un sp??cialiste ?? partir d'un utilisateur ayant le profil admin
    @Test
    void createSpecialistWithAdminUserWithSuccess() throws ClinicException {
        // on cr??e un utilisateur ayant le profil ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, false);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr????
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // on cr??e ce sp??cialiste
        // tout se passe bien ici car il n'existe pas encore en BD de sp??cialiste
        // ayant l'id de notre utlisateur
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);

        // v??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
        assertEquals(expectedSpecialist.getBiography(), specialistSaved.getBiography());
        assertEquals(expectedSpecialist.getCity(), specialistSaved.getCity());
        assertEquals(expectedSpecialist.getGender(), specialistSaved.getGender());
    }

    // cr??ation d'un sp??cialiste ?? partir d'un utilisateur inexistant
    @Test
    void createSpecialistWithNotExistingUserWitherror() {
        // on construit un utilisateur
        User createdUser = buildRandomUser(true, true, true);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr????
        // mais cet id est null puisque l'utilisateur en question n'a pas
        // ??t?? sauvegarder en BD. une exception sera l??v??e
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // cr??ation d'un sp??cialiste sans utilisateur associ??
    @Test
    void createSpecialistWithoutUserWithError() {
        // un sp??cialiste est toujours li?? ?? un utlisateur ayant le r??le SPECIALIST
        // en tentant de cr??er un sp??cialiste qui ne poss??de pas
        // d'utilisateur associ??. une exception sera l??v??e
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(buildRandomSpecialist()));
        assertEquals(Constants.NULL_POINTER, e.getCode());
        assertEquals(Constants.USER_IS_NULL, e.getMessage());
    }

    // Renvoyer une liste vide s'il n'y a pas de sp??cialiste enregistr?? en BD
    @Test
    void getAllSpecialistWithEmptyArray() {
        // On renvoie tous les sp??cialistes de la BD
        // la BD ne contient aucun sp??cialiste pour le moment
        List<Specialist> specialists = specialistService.getAllSpecialist();
        assertNotNull(specialists);
        assertTrue(specialists.isEmpty());
    }

    // Renvoyer la liste de sp??cialiste enregistr?? en bD
    @Test
    void getAllSpecialistWithSuccess() throws ClinicException {
        // cr??ation d'utilisateur pour charger la BD
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr????
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // On cr??e ce sp??cialiste
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);
        // On renvoie tous les sp??cialistes de la BD
        List<Specialist> specialists = specialistService.getAllSpecialist();

        // V??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(specialists);
        assertFalse(specialists.isEmpty());
        assertTrue(specialists.size() == 1);
    }

    // Renvoyer une liste vide des sp??cialistes actifs
    @Test
    void getActiveSpecialistWithEmptyArray() {
        // On renvoie tous les sp??cialistes actifs de la BD
        // ?? ce stade, la BD ne contient aucun sp??cialiste
        List<Specialist> specialists = specialistService.getActiveSpecialist();

        // V??rifications
        assertNotNull(specialists);
        assertTrue(specialists.isEmpty());
    }

    // Renvoyer la liste de sp??cialiste actifs enregistr?? en bD
    @Test
    void getActiveSpecialistWithSuccess() throws ClinicException {
        // cr??ation d'utilisateur pour charger la BD
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // On construit un sp??cialiste en lui affectant l'id de l'utlisateur cr????
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // On cr??e ce sp??cialiste
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);
        // On renvoie tous les sp??cialistes ayant le statut actif de la BD
        List<Specialist> specialists = specialistService.getActiveSpecialist();

        // V??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(specialists);
        assertFalse(specialists.isEmpty());
        assertTrue(specialists.size() == 1);
        for (Specialist specialist : specialists) {
            assertEquals(Constants.STATE_ACTIVATED, specialist.getStatus());
        }
    }

    // supprimer par son id un specialiste inexistant
    @Test
    void deleteNotExistingSpecialistWithError() {
        // cr??er un id non existant
        Integer id = 1;
        // puisque le sp??cialiste n'existe pas, une exception sera l??v??e
        ClinicException e = assertThrows(ClinicException.class, () -> specialistService.deleteSpecialist(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
    }

    // supprimer avec succ??s par son id un specialiste existant
    @Test
    void deleteExistingSpecialistWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistRepository.findByUserIdId(createdUser.getId());
        // Supprimer ce sp??cialiste
        Specialist deletedSpecialist = specialistService.deleteSpecialist(specialistSaved.getSpecialistId());

        // v??rifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistSaved.getStatus());
        assertNotNull(deletedSpecialist);
        assertNotNull(deletedSpecialist.getSpecialistId());
        assertEquals(Constants.STATE_DELETED, deletedSpecialist.getStatus());
    }

    // Rechercher un sp??cialiste existant ?? partir de l'id de
    // l'utilisateur associ??
    @Test
    void findSpecialistByUserIdWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findByUserId(createdUser.getId());

        // V??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
    }

    // Rechercher un sp??cialiste ?? partir de l'id d'un utilisateur
    // qui a le profil ADMIN
    @Test
    void findSpecialistByAdminUserIdWithSuccess() throws ClinicException {
        // cr??ation du r??le ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // cr??ation d'un utilisateur ayant le r??le ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findByUserId(createdUser.getId());

        // V??rifications
        assertNull(specialistSaved);
    }

    // Rechercher un sp??cialiste existant ?? partir de l'utilisateur associ??
    @Test
    void findSpecialistByUserWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findByUserId(createdUser);

        // V??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
    }

    // Rechercher un sp??cialiste ?? partir d'un utilisateur
    // qui a le profil ADMIN
    @Test
    void findSpecialistByAdminUserWithSuccess() throws ClinicException {
        // cr??ation du r??le ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // cr??ation d'un utilisateur ayant le r??le ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findByUserId(createdUser);

        // V??rifications
        assertNull(specialistSaved);
    }

    // Rechercher un sp??cialiste existant par son id
    @Test
    void findExistingSpecialistByIdWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le sp??cialiste cr???? automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);

        // V??rifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
        assertEquals(specialistId, specialistSaved.getSpecialistId());
    }

    // Rechercher un sp??cialiste inexistant par son id
    @Test
    void findNotExistingSpecialistByIdWithSuccess() {
        // Cr??ation d'un id inexistant
        Integer specialistId = 1;
        // Rechecher le sp??cialiste par l'id inexistant
        Specialist specialistFound = specialistService.findBySpecialistId(specialistId);
        assertTrue(specialistFound == null);
    }

    // Mise ?? jour de la liste des liens vers les r??seaux sociaux
    // d'un sp??cialiste
    @Test
    void updateSocialMediaByIdWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le sp??cialiste cr???? automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);
        // cr??ation de l'objet socialMediaLinks
        SocialMediaLinks socialMediaLinks = buildSocialMediaLinks();
        // cr??ation de la chaine de caract??re qui va ??tre stock??e en BD
        String socialMedia = JsonSerializer.toJson(socialMediaLinks, SocialMediaLinks.class);
        // mise ?? des liens vers les r??seaux sociaux
        Specialist updatedSpecialist = specialistService.updateSocialMediaById(socialMediaLinks, specialistSaved);

        // V??rifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(updatedSpecialist);
        assertNotNull(updatedSpecialist.getSpecialistId());
        assertEquals(createdUser.getId(), updatedSpecialist.getUserId().getId());
        assertEquals(specialistSaved.getSpecialistId(), updatedSpecialist.getSpecialistId());
        assertEquals(socialMedia, updatedSpecialist.getSocialMediaLinks());
    }

    // Obtenir la liste des liens vers les r??seaux sociaux
    // d'un sp??cialiste
    @Test
    void getSocialMediaByIdWithSuccess() throws ClinicException {
        // cr??ation du r??le SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // cr??ation d'un utilisateur ayant le r??le SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le sp??cialiste cr???? automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le sp??cialiste associ?? ?? l'utilisateur qui vient d'??tre cr????
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);
        // cr??ation de l'objet socialMediaLinks
        SocialMediaLinks socialMediaLinksToSet = buildSocialMediaLinks();
        // cr??ation de la chaine de caract??re qui va ??tre stock??e en BD
        String socialMedia = JsonSerializer.toJson(socialMediaLinksToSet, SocialMediaLinks.class);
        // mise ?? des liens vers les r??seaux sociaux
        Specialist updatedSpecialist = specialistService.updateSocialMediaById(socialMediaLinksToSet, specialistSaved);
        // r??cup??ration de l'object contenant la liste des liens
        // vers les r??seaux sociaux
        SocialMediaLinks socialMediaLinksToGet = specialistService
                .getSocialMediaById(updatedSpecialist.getSpecialistId());

        // V??rifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(updatedSpecialist);
        assertNotNull(updatedSpecialist.getSpecialistId());
        assertEquals(createdUser.getId(), updatedSpecialist.getUserId().getId());
        assertEquals(specialistSaved.getSpecialistId(), updatedSpecialist.getSpecialistId());
        assertEquals(socialMedia, updatedSpecialist.getSocialMediaLinks());
        // social media check
        assertNotNull(socialMediaLinksToGet);
        assertEquals(socialMediaLinksToSet.getFacebook(), socialMediaLinksToGet.getFacebook());
        assertEquals(socialMediaLinksToSet.getInstagram(), socialMediaLinksToGet.getInstagram());
        assertEquals(socialMediaLinksToSet.getLinkedin(), socialMediaLinksToGet.getLinkedin());
        assertEquals(socialMediaLinksToSet.getPinterest(), socialMediaLinksToGet.getPinterest());
        assertEquals(socialMediaLinksToSet.getTwitter(), socialMediaLinksToGet.getTwitter());
        assertEquals(socialMediaLinksToSet.getYoutube(), socialMediaLinksToGet.getYoutube());
    }
}
