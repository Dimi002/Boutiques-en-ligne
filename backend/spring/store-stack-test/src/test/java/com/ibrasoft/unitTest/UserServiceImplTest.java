package com.ibrasoft.unitTest;

import com.ibrasoft.storeStackProd.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.junit.Assert;

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.util.Constants;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SpecialistService specialistService;

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

    // generation d'une chaine de caractere aleatoire
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

    // cette methode cree un utilisateur aleatoire
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

    // cette methode cree un role aleatoire
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

    // Obtenir la liste de tous les utilisateurs s'il y en a aucun
    @Test
    void getUserWithEmptyArray() {
        List<User> users = userService.getAllUser();
        Assert.assertTrue(users.isEmpty());
    }

    // Obtenir la liste de tous les utilisateurs
    @Test
    void getAllUserWithSuccess() throws ClinicException {
        User user1 = buildRandomUser(false, false, false);
        User user2 = buildRandomUser(false, false, false);
        User user3 = buildRandomUser(true, false, false);
        User user4 = buildRandomUser(false, true, false);

        userService.createAdminOrSpecialist(user1, true, false);
        userService.createAdminOrSpecialist(user2, false, true);
        userService.createAdminOrSpecialist(user3, false, true);
        userService.createAdminOrSpecialist(user4, false, true);

        List<User> users = new ArrayList<User>();
        users = userService.getAllUser();

        assertNotNull(user1);
        assertNotNull(user2);
        assertNotNull(user3);
        assertNotNull(user4);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        assertNotNull(user3.getId());
        assertNotNull(user4.getId());
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertNotEquals(0, users.size());
        assertEquals(4, users.size());
    }

    // Creer un utilisateur avec un username existant
    @Test
    void createUserWithExistingUsernameWithError() throws ClinicException {
        User expectedUser1 = buildRandomUser(true, false, false);
        User expectedUser2 = buildRandomUser(true, false, false);

        User specialistToSave1 = userService.createAdminOrSpecialist(expectedUser1, false, true);
        assertNotNull(specialistToSave1);
        assertNotNull(specialistToSave1.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.createAdminOrSpecialist(expectedUser2, false, true));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.USERNAME_ALREADY_EXIST, e.getMessage());
    }

    // Creer un utilisateur avec une adresse email existant
    @Test
    void createUserWithExistingEmailWithError() throws ClinicException {
        User expectedUser1 = buildRandomUser(false, true, false);
        User expectedUser2 = buildRandomUser(false, true, false);

        User specialistToSave1 = userService.createAdminOrSpecialist(expectedUser1, false, true);
        assertNotNull(specialistToSave1);
        assertNotNull(specialistToSave1.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.createAdminOrSpecialist(expectedUser2, false, true));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.EMAIL_ALREADY_EXIST, e.getMessage());
    }

    // Creer un utilisateur avec une adresse email existant et
    // un username existant
    @Test
    void createUserWithExistingUsernameAndEmailWithError() throws ClinicException {
        User expectedUser1 = buildRandomUser(true, true, false);
        User expectedUser2 = buildRandomUser(true, true, false);

        User specialistToSave1 = userService.createAdminOrSpecialist(expectedUser1, false, true);
        assertNotNull(specialistToSave1);
        assertNotNull(specialistToSave1.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.createAdminOrSpecialist(expectedUser2, false, true));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.USERNAME_AND_EMAIL_ALREADY_EXIST, e.getMessage());
    }

    // Creer un utilisateur ayant le role ADMIN avec une adresse email
    // inexistant un username inexistant
    @Test
    void createAdminWithSuccess() throws ClinicException {
        Role adminRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        User expectedUser = buildRandomUser(false, false, false);

        User savedUser = userService.createAdminOrSpecialist(expectedUser, true, false);

        assertNotNull(adminRole);
        assertNotNull(adminRole.getRoleId());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        assertEquals(expectedUser.getBirthDate(), savedUser.getBirthDate());
        assertEquals(expectedUser.getClearPassword(), savedUser.getClearPassword());
        assertEquals(expectedUser.getComment(), savedUser.getComment());
        assertEquals(expectedUser.getEmail(), savedUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), savedUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), savedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(expectedUser.getPhone(), savedUser.getPhone());
        assertEquals(expectedUser.getUsername(), savedUser.getUsername());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(Constants.STATE_ACTIVATED, savedUser.getStatus());
        assertEquals(expectedUser.getPassword(), savedUser.getPassword());
    }

    // Creer un utilisateur ayant le role SPECIALIST avec
    // une adresse email inexistant et un username inexistant
    @Test
    void createSpecialistWithSuccess() throws ClinicException {
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User expectedUser = buildRandomUser(false, false, false);

        User savedUser = userService.createAdminOrSpecialist(expectedUser, false, true);

        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());

        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());

        assertEquals(expectedUser.getBirthDate(), savedUser.getBirthDate());
        assertEquals(expectedUser.getClearPassword(), savedUser.getClearPassword());
        assertEquals(expectedUser.getComment(), savedUser.getComment());
        assertEquals(expectedUser.getEmail(), savedUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), savedUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), savedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(expectedUser.getPhone(), savedUser.getPhone());
        assertEquals(expectedUser.getUsername(), savedUser.getUsername());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(Constants.STATE_ACTIVATED, savedUser.getStatus());
        assertEquals(expectedUser.getPassword(), savedUser.getPassword());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
    }

    // Creer un utilisateur ayant les 2 roles ADMIN et SPECIALIST
    // avec une adresse email inexistant et un username inexistant
    @Test
    void createAdminAndSpecialistUserWithSuccess() throws ClinicException {
        Role adminRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User expectedUser = buildRandomUser(false, false, false);

        User savedUser = userService.createAdminOrSpecialist(expectedUser, true, true);

        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());

        assertNotNull(adminRole);
        assertNotNull(adminRole.getRoleId());
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());

        assertEquals(expectedUser.getBirthDate(), savedUser.getBirthDate());
        assertEquals(expectedUser.getClearPassword(), savedUser.getClearPassword());
        assertEquals(expectedUser.getComment(), savedUser.getComment());
        assertEquals(expectedUser.getEmail(), savedUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), savedUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), savedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(expectedUser.getPhone(), savedUser.getPhone());
        assertEquals(expectedUser.getUsername(), savedUser.getUsername());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(Constants.STATE_ACTIVATED, savedUser.getStatus());
        assertEquals(expectedUser.getPassword(), savedUser.getPassword());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
    }

    // Mettre a  jour un utilisateur existant
    @Test
    void updateExistingUserWithSuccess() throws ClinicException {
        User userToUpdate = buildRandomUser(true, true, false);
        User expectedUser = userService.createAdminOrSpecialist(userToUpdate,
                false, true);

        expectedUser.setComment("modification");
        expectedUser.setEmail("alex59@gmail.com");
        expectedUser.setFirstName("Kougoum");
        expectedUser.setLastName("Lionel");
        expectedUser.setPhone("691190361");
        expectedUser.setUsername("lionel");

        userService.updateAdminOrSpecialistByAdmin(expectedUser);
        User updatedUser = userService.updateAdminOrSpecialistByAdmin(expectedUser);

        assertNotNull(updatedUser);
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertEquals(expectedUser.getClearPassword(), updatedUser.getClearPassword());
        assertEquals(expectedUser.getComment(), updatedUser.getComment());
        assertEquals(expectedUser.getEmail(), updatedUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), updatedUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(expectedUser.getLastName(), updatedUser.getLastName());
        assertEquals(expectedUser.getPhone(), updatedUser.getPhone());
        assertEquals(expectedUser.getUsername(), updatedUser.getUsername());
        assertEquals(expectedUser.getStatus(), updatedUser.getStatus());
        assertEquals(expectedUser.getPassword(), updatedUser.getPassword());
    }

    // Mette a  jour un utilisateur inexistant
    @Test
    void updateNotExistingUserWithError() throws ClinicException {
        User userToUpdate = buildRandomUser(true, true, false);
        User expectedUser = userService.createAdminOrSpecialist(userToUpdate,
                false, true);

        expectedUser.setComment("mise a  jour");
        expectedUser.setEmail("alex59@gmail.com");
        expectedUser.setFirstName("Kougoum");
        expectedUser.setLastName("Lionel");
        expectedUser.setPhone("691190361");
        expectedUser.setUsername("lionel");
        // we try to set not existing id
        expectedUser.setId(2);

        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.updateAdminOrSpecialistByAdmin(expectedUser));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Mette a  jour un utilisateur supprimé
    @Test
    void updateDeletedUserWithError() throws ClinicException {
        User userToUpdate = buildRandomUser(true, true, false);
        User expectedUser = userService.createAdminOrSpecialist(userToUpdate,
                false, true);
        // On supprime l'utilisateur qui vient d'etre cree
        User userDeleted = userService.deleteUser(expectedUser.getId());
        // On set les attributs à modifier
        userDeleted.setComment("mise a  jour");
        userDeleted.setEmail("alex59@gmail.com");
        userDeleted.setFirstName("Kougoum");
        userDeleted.setLastName("Lionel");
        userDeleted.setPhone("691190361");
        userDeleted.setUsername("lionel");
        // l'utilisateur a ete supprimer, une exception sera levee
        // lors de la modification

        // Vérifications
        assertNotNull(expectedUser);
        assertNotNull(expectedUser.getId());
        assertEquals(Constants.STATE_ACTIVATED, expectedUser.getStatus());
        assertNotNull(userDeleted);
        assertNotNull(userDeleted.getId());
        assertEquals(Constants.STATE_DELETED, userDeleted.getStatus());
        assertEquals(expectedUser.getId(), userDeleted.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.updateAdminOrSpecialistByAdmin(userDeleted));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // Mette a jour un utilisateur desactive
    @Test
    void updateDeactivatedUserWithError() throws ClinicException {
        User userToUpdate = buildRandomUser(true, true, false);
        User expectedUser = userService.createAdminOrSpecialist(userToUpdate,
                false, true);
        // On desactive l'utilisateur qui vient d'etre cree
        expectedUser.setStatus(Constants.STATE_DEACTIVATED);
        Boolean userDeactivated = userService.updateUserStatus(expectedUser);
        // recherche de l'utilisateur desactive
        User userUpdated = userService.findUserById(expectedUser.getId());
        // On set les attributs à modifier
        userUpdated.setComment("mise a jour");
        userUpdated.setEmail("alex59@gmail.com");
        userUpdated.setFirstName("Kougoum");
        userUpdated.setLastName("Lionel");
        userUpdated.setPhone("691190361");
        userUpdated.setUsername("lionel");
        // l'utilisateur a ete desactive, une exception sera levee
        // lors de la modification

        // Verifications
        assertNotNull(expectedUser);
        assertNotNull(expectedUser.getId());
        assertNotNull(userUpdated);
        assertNotNull(userUpdated.getId());
        assertEquals(expectedUser.getId(), userToUpdate.getId());
        assertEquals(Constants.STATE_DEACTIVATED, userUpdated.getStatus());
        assertTrue(true == userDeactivated);
        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.updateAdminOrSpecialistByAdmin(userUpdated));
        assertEquals(Constants.ITEM_ALREADY_DEACTIVATED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DEACTIVATED, e.getMessage());
    }

    // Supprimer un utilisateur existant
    @Test
    void deleteExistingUserWithSuccess() throws ClinicException {
        // creation de l'utilisateur et du role
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), true, true);
        // On supprime l'utilisateur qui vient d'etre cree
        User deletedUser = userService.deleteUser(expectedUser.getId());
        // recherche du spécialiste associé
        Specialist specialistFound = specialistService.findByUserId(expectedUser.getId());

        // Verifications
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(expectedUser);
        assertNotNull(expectedUser.getId());
        assertNotNull(deletedUser);
        assertNotNull(deletedUser.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(expectedUser.getId(), deletedUser.getId());
        assertEquals(Constants.STATE_ACTIVATED, expectedUser.getStatus());
        assertEquals(Constants.STATE_DELETED, deletedUser.getStatus());
        assertEquals(Constants.STATE_DELETED, specialistFound.getStatus());
        assertNotEquals(expectedUser.getLastUpdateOn(), deletedUser.getLastUpdateOn());
    }

    // Supprimer un utilisateur existant deja  supprime
    @Test
    void deleteAlreadyDeletedUserWithError() throws ClinicException {
        // creation de l'utilisateur
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), true, true);
        // Suppression
        User deletedUser = userService.deleteUser(expectedUser.getId());
        // recherche du spécialiste associé
        Specialist specialistFound = specialistService.findByUserId(expectedUser.getId());

        // Verifications
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(expectedUser);
        assertNotNull(expectedUser.getId());
        assertNotNull(deletedUser);
        assertNotNull(deletedUser.getId());
        assertNotNull(specialistFound);
        assertNotNull(specialistFound.getSpecialistId());
        assertEquals(expectedUser.getId(), deletedUser.getId());
        assertEquals(Constants.STATE_ACTIVATED, expectedUser.getStatus());
        assertEquals(Constants.STATE_DELETED, deletedUser.getStatus());
        assertTrue(Constants.STATE_DELETED == specialistFound.getStatus());
        assertNotEquals(expectedUser.getLastUpdateOn(), deletedUser.getLastUpdateOn());
        // l'utilisateur est deja  supprime, une exception sera levee
        ClinicException e = assertThrows(ClinicException.class, () -> userService.deleteUser(deletedUser.getId()));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // Supprimer un utilisateur inexistant
    @Test
    void deleteNotExistingUserWithError() {
        // build not existing id
        Integer id = 1;
        ClinicException e = assertThrows(ClinicException.class, () -> userService.deleteUser(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Rechercher un utilisateur existant par son nom
    @Test
    void findExistingUserByUserNameWithSuccess() throws ClinicException {
        // creation de l'utilisateur
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), true, false);
        // appel de la fonction de recherche
        User findUser = userService.findByUserName(expectedUser.getUsername());

        // verifications
        assertNotNull(findUser);
        assertNotNull(findUser.getId());
        assertEquals(expectedUser.getId(), findUser.getId());
        assertEquals(expectedUser.getBirthDate(), findUser.getBirthDate());
        assertEquals(expectedUser.getClearPassword(), findUser.getClearPassword());
        assertEquals(expectedUser.getComment(), findUser.getComment());
        assertEquals(expectedUser.getEmail(), findUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), findUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), findUser.getFirstName());
        assertEquals(expectedUser.getLastName(), findUser.getLastName());
        assertEquals(expectedUser.getPhone(), findUser.getPhone());
        assertEquals(expectedUser.getUsername(), findUser.getUsername());
        assertEquals(expectedUser.getStatus(), findUser.getStatus());
        assertEquals(expectedUser.getPassword(), findUser.getPassword());
    }

    // Rechercher un utilisateur inexistant par son nom
    @Test
    void findNotExistingUserByUserNameWithSuccess() {
        String notExistUsername = "lionel";
        User foundUser = userService.findByUserName(notExistUsername);
        assertNull(foundUser);
    }

    // Rechercher un utilisateur existant par son id
    @Test
    void findExistingUserByIdWithSuccess() throws ClinicException {
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), true, false);

        User findUser = userService.findUserById(expectedUser.getId());

        assertNotNull(findUser);
        assertNotNull(findUser.getId());
        assertEquals(expectedUser.getId(), findUser.getId());
        assertEquals(expectedUser.getBirthDate(), findUser.getBirthDate());
        assertEquals(expectedUser.getClearPassword(), findUser.getClearPassword());
        assertEquals(expectedUser.getComment(), findUser.getComment());
        assertEquals(expectedUser.getEmail(), findUser.getEmail());
        assertEquals(expectedUser.getCreatedOn(), findUser.getCreatedOn());
        assertEquals(expectedUser.getFirstName(), findUser.getFirstName());
        assertEquals(expectedUser.getLastName(), findUser.getLastName());
        assertEquals(expectedUser.getPhone(), findUser.getPhone());
        assertEquals(expectedUser.getUsername(), findUser.getUsername());
        assertEquals(expectedUser.getStatus(), findUser.getStatus());
        assertEquals(expectedUser.getPassword(), findUser.getPassword());
    }

    // Rechercher un utilisateur inexistant par son id
    @Test
    void findNotExistingUserByIdWithError() {
        Integer notExistId = 2;
        ClinicException e = assertThrows(ClinicException.class, () -> userService.findUserById(notExistId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Mettre a jour le mot de passe d'un utilisateur en reutilisant
    // l'ancien mot de passe
    @Test
    void updateUserPasswordWithSamePasswordWithSuccess() throws ClinicException {
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);

        User updatedUser = userService.updateUserPassword(expectedUser.getId(),
                expectedUser.getClearPassword(),
                expectedUser.getClearPassword());

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getClearPassword());
        assertNotNull(updatedUser.getPassword());
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertEquals(expectedUser.getClearPassword(), updatedUser.getClearPassword());
        assertEquals(expectedUser.getPassword(), updatedUser.getPassword());
    }

    // Mettre a jour le mot de passe d'un utilisateur avec
    // un nouveau mot de passe
    @Test
    void updateUserPasswordWithNewPasswordWithSuccess() throws ClinicException {
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);

        String newPassword = "coder";
        User updatedUser = userService.updateUserPassword(expectedUser.getId(),
                expectedUser.getClearPassword(),
                newPassword);

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getClearPassword());
        assertNotNull(updatedUser.getPassword());
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertNotEquals(expectedUser.getClearPassword(), updatedUser.getClearPassword());
        assertNotEquals(expectedUser.getPassword(), updatedUser.getPassword());
    }

    // Mise a jour du mot de passe si l'utilisateur a oublier
    // son ancien mot de passe
    @Test
    void updateUserPasswordWithWrongOldPasswordWithError() throws ClinicException {
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);

        String fakeOldPassword = "oldPassword";

        ClinicException e = assertThrows(ClinicException.class,
                () -> userService.updateUserPassword(expectedUser.getId(),
                        fakeOldPassword,
                        expectedUser.getClearPassword()));
        assertEquals(Constants.INVALID_INPUT, e.getCode());
        assertEquals(Constants.OLD_PASSWORD_NOT_MATCH, e.getMessage());
    }

    // Initialiser la photo de profil d'un utilisateur
    @Test
    void setNewUserImageWithSuccess() throws ClinicException {
        User expectedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);

        String imagePath = expectedUser.getUserImagePath();
        String newPath = "Storage/Images/1665534625692-1665534623326.png";
        Integer id = expectedUser.getId();

        Boolean result = userService.updateImage(id, newPath);

        User updatedUser = userService.findUserById(id);

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertNull(imagePath);
        assertNotNull(updatedUser.getUserImagePath());
        assertTrue(newPath == updatedUser.getUserImagePath());
        assertTrue(result == true);
        assertFalse(imagePath == updatedUser.getUserImagePath());
    }

    // Mettre a jour une photo de profil existante
    @Test
    void setExistingUserImageWithSuccess() throws ClinicException {

        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);

        String newPath = "Storage/Images/1665534625692-1665534623326.png";
        Integer id = createdUser.getId();

        Boolean result = userService.updateImage(id, newPath);

        User expectedUser = userService.findUserById(id);

        String newPathToSet = "Storage/Images/alex.png";

        Boolean secondResult = userService.updateImage(expectedUser.getId(),
                newPathToSet);

        User updatedUser = userService.findUserById(expectedUser.getId());

        assertNull(createdUser.getUserImagePath());
        assertNotNull(expectedUser);
        assertNotNull(expectedUser.getId());
        assertTrue(true == result);
        assertTrue(true == secondResult);
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertEquals(createdUser.getId(), expectedUser.getId());
        assertEquals(expectedUser.getId(), updatedUser.getId());
        assertEquals(newPath, expectedUser.getUserImagePath());
        assertEquals(newPathToSet, updatedUser.getUserImagePath());
        assertNotEquals(expectedUser.getUserImagePath(), updatedUser.getUserImagePath());
    }

    // Desactiver un utilisateur ayant le role de SPECIALIST
    @Test
    void deactivateSpecialistWithSuccess() throws ClinicException {
        // creation du role et de l'utilisateur
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // desactivation
        savedUser.setStatus(Constants.STATE_DEACTIVATED);
        Boolean result = userService.updateUserStatus(savedUser);
        // on recherche le spécialiste associé à l'utilisateur
        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());
        // on recherche l'utilisateur mis a jour
        User updatedUser = userService.findUserById(savedUser.getId());

        // Vérifications
        assertTrue(true == result);
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
        assertEquals(Constants.STATE_DEACTIVATED, updatedUser.getStatus());
        // on s'assure que le spécialiste associé à l'utilisateur a aussi
        // été désactivé
        assertEquals(Constants.STATE_DEACTIVATED, specialist.getStatus());
    }

    // Desactiver un utilisateur supprimé
    @Test
    void deactivateDeletedUserWithError() throws ClinicException {
        // creation du role et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // suppression
        User userDeleted = userService.deleteUser(savedUser.getId());
        // desactivation
        savedUser.setStatus(Constants.STATE_DEACTIVATED);
        // une exception sera levee puisque l'utilisateur
        // a ete supprime

        // Vérifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userDeleted);
        assertNotNull(userDeleted.getId());
        assertNotNull(userDeleted.getStatus());
        assertEquals(savedUser.getId(), savedUser.getId());
        assertEquals(Constants.STATE_DELETED, userDeleted.getStatus());
        ClinicException e = assertThrows(ClinicException.class, () -> userService.updateUserStatus(userDeleted));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // Desactiver un utilisateur inexistant en BD
    @Test
    void deactivateNotExistingUserWithError() {
        // instancier l'utilisateur et l'id inexistants
        User expectedUser = buildRandomUser(true, true, true);
        Integer id = 1;
        expectedUser.setId(id);
        // desactivation
        expectedUser.setStatus(Constants.STATE_DEACTIVATED);
        // une exception sera levee puisque l'utilisateur
        // n'existe pas en BD

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class, () -> userService.updateUserStatus(expectedUser));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Activer un utilisateur ayant le rôle de SPECIALIST
    @Test
    void activateSpecialistWithSuccess() throws ClinicException {
        // creation de l'utilisateur et du role
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // activation
        savedUser.setStatus(Constants.STATE_ACTIVATED);
        Boolean result = userService.updateUserStatus(savedUser);
        // on recherche le spécialiste associé à l'utilisateur
        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());
        // on recherche aussi l'utilisateur mis a jour
        User updatedUser = userService.findUserById(savedUser.getId());

        // Vérifications
        assertTrue(true == result);
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
        assertEquals(Constants.STATE_ACTIVATED, updatedUser.getStatus());
        // on s'assure que le spécialiste associé à l'utilisateur a aussi été
        // activé
        assertEquals(Constants.STATE_ACTIVATED, specialist.getStatus());
    }

    // désactiver un utilisateur ayant le rôle d'ADMIN
    @Test
    void deactivateAdminWithSuccess() throws ClinicException {
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);

        savedUser.setStatus(Constants.STATE_DEACTIVATED);

        Boolean result = userService.updateUserStatus(savedUser);

        User updatedUser = userService.findUserById(savedUser.getId());

        assertTrue(true == result);
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, updatedUser.getStatus());
    }

    // activer un utilisateur ayant le rôle d'ADMIN
    @Test
    void activateAdminWithSuccess() throws ClinicException {
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);

        savedUser.setStatus(Constants.STATE_ACTIVATED);

        Boolean result = userService.updateUserStatus(savedUser);

        User updatedUser = userService.findUserById(savedUser.getId());

        assertTrue(true == result);
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(Constants.STATE_ACTIVATED, updatedUser.getStatus());
    }

    // Activer un utilisateur supprimé
    @Test
    void activateDeletedUserWithError() throws ClinicException {
        // creation du role et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // suppression
        User userDeleted = userService.deleteUser(savedUser.getId());
        // desactivation
        savedUser.setStatus(Constants.STATE_ACTIVATED);
        // une exception sera levee puisque l'utilisateur
        // a ete supprime

        // Vérifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userDeleted);
        assertNotNull(userDeleted.getId());
        assertNotNull(userDeleted.getStatus());
        assertEquals(savedUser.getId(), savedUser.getId());
        assertEquals(Constants.STATE_DELETED, userDeleted.getStatus());
        ClinicException e = assertThrows(ClinicException.class, () -> userService.updateUserStatus(userDeleted));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // Activer un utilisateur inexistant en BD
    @Test
    void activateNotExistingUserWithError() {
        // instancier l'utilisateur et l'id inexistants
        User expectedUser = buildRandomUser(true, true, true);
        Integer id = 1;
        expectedUser.setId(id);
        // desactivation
        expectedUser.setStatus(Constants.STATE_ACTIVATED);
        // une exception sera levee puisque l'utilisateur
        // n'existe pas en BD

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class, () -> userService.updateUserStatus(expectedUser));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Désactiver un utilisateur ayant les rôles ADMIN et SPECIALIST
    @Test
    void deactivateAdminSpecialistWithSuccess() throws ClinicException {
        // creation des roles et de l'utilisateur
        Role adminRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // desactivation
        savedUser.setStatus(Constants.STATE_DEACTIVATED);
        Boolean result = userService.updateUserStatus(savedUser);
        // on recherche le spécialiste associé à l'utilisateur
        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());
        // on recherche l'utilisateur mis a jour
        User updatedUser = userService.findUserById(savedUser.getId());

        // Assertions et Verifications
        assertTrue(true == result);
        assertNotNull(adminRole);
        assertNotNull(adminRole.getRoleId());
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
        assertEquals(Constants.STATE_DEACTIVATED, updatedUser.getStatus());
        // on s'assure que le spécialiste associé à l'utilisateur a aussi
        // été désactivé
        assertEquals(Constants.STATE_DEACTIVATED, specialist.getStatus());
    }

    // activer un utilisateur ayant les rôles ADMIN et SPECIALIST
    @Test
    void activateAdminSpecialistWithSuccess() throws ClinicException {
        // creation des roles et de l'utilisateur
        Role adminRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN, false));
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST, false));
        User savedUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        // activation
        savedUser.setStatus(Constants.STATE_ACTIVATED);
        Boolean result = userService.updateUserStatus(savedUser);
        // on recherche le spécialiste associé à l'utilisateur
        Specialist specialist = specialistRepository.findByUserIdId(savedUser.getId());
        // on recherche l'utilisateur mis a jour
        User updatedUser = userService.findUserById(savedUser.getId());

        // Verifications
        assertTrue(true == result);
        assertNotNull(adminRole);
        assertNotNull(adminRole.getRoleId());
        assertNotNull(specialistRole);
        assertNotNull(specialistRole.getRoleId());
        assertNotNull(specialist);
        assertNotNull(specialist.getSpecialistId());
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getStatus());
        assertEquals(specialist.getUserId().getId(), savedUser.getId());
        assertEquals(Constants.STATE_ACTIVATED, updatedUser.getStatus());
        // on s'assure que le spécialiste associé à l'utilisateur a aussi
        // été activé
        assertEquals(Constants.STATE_ACTIVATED, specialist.getStatus());
    }
}
