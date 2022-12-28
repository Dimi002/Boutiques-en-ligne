package com.dimsoft.unitTest;

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

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.SocialMediaLinks;
import com.dimsoft.clinicStackProd.beans.Specialist;
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
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.service.SpecialistService;
import com.dimsoft.clinicStackProd.service.UserService;
import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.JsonSerializer;

@SpringBootTest(classes = ClinicStackProdApplication.class)
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

    // cette méthode crée un utilisateur aléatoire
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

    // cette méthode crée un spécialiste aléatoire
    public Specialist buildRandomSpecialist() {
        Specialist specialist = new Specialist();

        specialist.setBiography(generatRandomString(20));
        specialist.setCity(generatRandomString(5));
        specialist.setGender(generatRandomString(5));
        specialist.setYearOfExperience(4);

        return specialist;
    }

    // cette méthode crée un rôle aléatoire
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

    // cette méthode crée un objet contenant les liens vers les réseaux sociaux
    // d'un spécialiste
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

    // création d'un spécialiste à partir d'un utilisateur ayant
    // le profil SPECIALIST
    @Test
    void createSpecialistWithSpecialistUserWithError() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'un l'utilisateur ayant le profil SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur crée
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);

        // Si un utilisateur nouvellement créé à le profil SPECIALIST, alors
        // le spécialiste associé à cet utilisateur est automatiquement créé
        // au même moment.
        // célà implique que si on tente de créer encore ce spécialiste en
        // utilisant l'id de l'utilisateur créé, alors une exception sera lévée

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_EXIST, e.getMessage());
    }

    // création d'un spécialiste à partir utilisateur ayant les
    // profils ADMIN et SPECIALIST
    @Test
    void createSpecialistWithAdminSpecialistUserWithError() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création du rôle ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // création de l'utilisateur associé au spécialiste
        // cet utilisateur est à la fois ADMIN et SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, true);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur créé
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // une exception sera lévée puisque il existe déjà en BD un spécialiste
        // qui a l'id de notre utilisateur.
        // NB: ce spécialiste est créé dynamiquement lorsqu'on décide que
        // l'utilisateur qu'on va enregistrer aura le profil SPECIALIST

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.SPECIALIST_ALREADY_EXIST, e.getMessage());
    }

    // création d'un spécialiste à partir d'un utilisateur ayant le profil admin
    @Test
    void createSpecialistWithAdminUserWithSuccess() throws ClinicException {
        // on crée un utilisateur ayant le profil ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, false);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur créé
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // on crée ce spécialiste
        // tout se passe bien ici car il n'existe pas encore en BD de spécialiste
        // ayant l'id de notre utlisateur
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);

        // vérifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
        assertEquals(expectedSpecialist.getBiography(), specialistSaved.getBiography());
        assertEquals(expectedSpecialist.getCity(), specialistSaved.getCity());
        assertEquals(expectedSpecialist.getGender(), specialistSaved.getGender());
    }

    // création d'un spécialiste à partir d'un utilisateur inexistant
    @Test
    void createSpecialistWithNotExistingUserWitherror() {
        // on construit un utilisateur
        User createdUser = buildRandomUser(true, true, true);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur créé
        // mais cet id est null puisque l'utilisateur en question n'a pas
        // été sauvegarder en BD. une exception sera lévée
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(expectedSpecialist));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // création d'un spécialiste sans utilisateur associé
    @Test
    void createSpecialistWithoutUserWithError() {
        // un spécialiste est toujours lié à un utlisateur ayant le rôle SPECIALIST
        // en tentant de créer un spécialiste qui ne possède pas
        // d'utilisateur associé. une exception sera lévée
        ClinicException e = assertThrows(ClinicException.class,
                () -> specialistService.createOrUpdateSpecialist(buildRandomSpecialist()));
        assertEquals(Constants.NULL_POINTER, e.getCode());
        assertEquals(Constants.USER_IS_NULL, e.getMessage());
    }

    // Renvoyer une liste vide s'il n'y a pas de spécialiste enregistré en BD
    @Test
    void getAllSpecialistWithEmptyArray() {
        // On renvoie tous les spécialistes de la BD
        // la BD ne contient aucun spécialiste pour le moment
        List<Specialist> specialists = specialistService.getAllSpecialist();
        assertNotNull(specialists);
        assertTrue(specialists.isEmpty());
    }

    // Renvoyer la liste de spécialiste enregistré en bD
    @Test
    void getAllSpecialistWithSuccess() throws ClinicException {
        // création d'utilisateur pour charger la BD
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur créé
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // On crée ce spécialiste
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);
        // On renvoie tous les spécialistes de la BD
        List<Specialist> specialists = specialistService.getAllSpecialist();

        // Vérifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(specialists);
        assertFalse(specialists.isEmpty());
        assertTrue(specialists.size() == 1);
    }

    // Renvoyer une liste vide des spécialistes actifs
    @Test
    void getActiveSpecialistWithEmptyArray() {
        // On renvoie tous les spécialistes actifs de la BD
        // à ce stade, la BD ne contient aucun spécialiste
        List<Specialist> specialists = specialistService.getActiveSpecialist();

        // Vérifications
        assertNotNull(specialists);
        assertTrue(specialists.isEmpty());
    }

    // Renvoyer la liste de spécialiste actifs enregistré en bD
    @Test
    void getActiveSpecialistWithSuccess() throws ClinicException {
        // création d'utilisateur pour charger la BD
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // On construit un spécialiste en lui affectant l'id de l'utlisateur créé
        Specialist expectedSpecialist = buildRandomSpecialist();
        expectedSpecialist.setUserId(createdUser);
        // On crée ce spécialiste
        Specialist specialistSaved = specialistService.createOrUpdateSpecialist(expectedSpecialist);
        // On renvoie tous les spécialistes ayant le statut actif de la BD
        List<Specialist> specialists = specialistService.getActiveSpecialist();

        // Vérifications
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
        // créer un id non existant
        Integer id = 1;
        // puisque le spécialiste n'existe pas, une exception sera lévée
        ClinicException e = assertThrows(ClinicException.class, () -> specialistService.deleteSpecialist(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.SPECIALIST_NOT_FOUND, e.getMessage());
    }

    // supprimer avec succès par son id un specialiste existant
    @Test
    void deleteExistingSpecialistWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistRepository.findByUserIdId(createdUser.getId());
        // Supprimer ce spécialiste
        Specialist deletedSpecialist = specialistService.deleteSpecialist(specialistSaved.getSpecialistId());

        // vérifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertEquals(Constants.STATE_ACTIVATED, specialistSaved.getStatus());
        assertNotNull(deletedSpecialist);
        assertNotNull(deletedSpecialist.getSpecialistId());
        assertEquals(Constants.STATE_DELETED, deletedSpecialist.getStatus());
    }

    // Rechercher un spécialiste existant à partir de l'id de
    // l'utilisateur associé
    @Test
    void findSpecialistByUserIdWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findByUserId(createdUser.getId());

        // Vérifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
    }

    // Rechercher un spécialiste à partir de l'id d'un utilisateur
    // qui a le profil ADMIN
    @Test
    void findSpecialistByAdminUserIdWithSuccess() throws ClinicException {
        // création du rôle ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // création d'un utilisateur ayant le rôle ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findByUserId(createdUser.getId());

        // Vérifications
        assertNull(specialistSaved);
    }

    // Rechercher un spécialiste existant à partir de l'utilisateur associé
    @Test
    void findSpecialistByUserWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findByUserId(createdUser);

        // Vérifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
    }

    // Rechercher un spécialiste à partir d'un utilisateur
    // qui a le profil ADMIN
    @Test
    void findSpecialistByAdminUserWithSuccess() throws ClinicException {
        // création du rôle ADMIN
        roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        // création d'un utilisateur ayant le rôle ADMIN
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), true, false);
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findByUserId(createdUser);

        // Vérifications
        assertNull(specialistSaved);
    }

    // Rechercher un spécialiste existant par son id
    @Test
    void findExistingSpecialistByIdWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le spécialiste créé automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);

        // Vérifications
        assertNotNull(specialistSaved);
        assertNotNull(specialistSaved.getSpecialistId());
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(createdUser.getId(), specialistSaved.getUserId().getId());
        assertEquals(specialistId, specialistSaved.getSpecialistId());
    }

    // Rechercher un spécialiste inexistant par son id
    @Test
    void findNotExistingSpecialistByIdWithSuccess() {
        // Création d'un id inexistant
        Integer specialistId = 1;
        // Rechecher le spécialiste par l'id inexistant
        Specialist specialistFound = specialistService.findBySpecialistId(specialistId);
        assertTrue(specialistFound == null);
    }

    // Mise à jour de la liste des liens vers les réseaux sociaux
    // d'un spécialiste
    @Test
    void updateSocialMediaByIdWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le spécialiste créé automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);
        // création de l'objet socialMediaLinks
        SocialMediaLinks socialMediaLinks = buildSocialMediaLinks();
        // création de la chaine de caractère qui va être stockée en BD
        String socialMedia = JsonSerializer.toJson(socialMediaLinks, SocialMediaLinks.class);
        // mise à des liens vers les réseaux sociaux
        Specialist updatedSpecialist = specialistService.updateSocialMediaById(socialMediaLinks, specialistSaved);

        // Vérifications
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

    // Obtenir la liste des liens vers les réseaux sociaux
    // d'un spécialiste
    @Test
    void getSocialMediaByIdWithSuccess() throws ClinicException {
        // création du rôle SPECIALIST
        roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        // création d'un utilisateur ayant le rôle SPECIALIST
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true,
                true, true), false, true);
        // le spécialiste créé automatiquement aura pour id la valeur 1
        Integer specialistId = 1;
        // Rechecher le spécialiste associé à l'utilisateur qui vient d'être créé
        Specialist specialistSaved = specialistService.findBySpecialistId(specialistId);
        // création de l'objet socialMediaLinks
        SocialMediaLinks socialMediaLinksToSet = buildSocialMediaLinks();
        // création de la chaine de caractère qui va être stockée en BD
        String socialMedia = JsonSerializer.toJson(socialMediaLinksToSet, SocialMediaLinks.class);
        // mise à des liens vers les réseaux sociaux
        Specialist updatedSpecialist = specialistService.updateSocialMediaById(socialMediaLinksToSet, specialistSaved);
        // récupération de l'object contenant la liste des liens
        // vers les réseaux sociaux
        SocialMediaLinks socialMediaLinksToGet = specialistService
                .getSocialMediaById(updatedSpecialist.getSpecialistId());

        // Vérifications
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
