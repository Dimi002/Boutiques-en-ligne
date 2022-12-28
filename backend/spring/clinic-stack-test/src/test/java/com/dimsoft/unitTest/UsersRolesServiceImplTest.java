package com.dimsoft.unitTest;

import java.util.List;
import java.util.Random;

import com.dimsoft.clinicStackProd.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.beans.UserRole;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.RoleRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.response.RolesIds;
import com.dimsoft.clinicStackProd.response.StateResponse;
import com.dimsoft.clinicStackProd.response.UserRoleMin;
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.service.UserService;
import com.dimsoft.clinicStackProd.service.UsersRolesService;
import com.dimsoft.clinicStackProd.util.Constants;

@SpringBootTest(classes = ClinicStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersRolesServiceImplTest {

    @Autowired
    UsersRolesService usersRolesService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        userRepository.deleteAll();
        appointmentRepository.deleteAll();
        specialistSpecialityRepository.deleteAll();
        specialityRepository.deleteAll();
        specialistRepository.deleteAll();
        userRepository.deleteAll();
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

    // instancier un role aleatoire
    public Role buildRandomRole(String roleName) {
        Role expectedRole = new Role();
        expectedRole.setRoleDesc(generatRandomString(20));
        expectedRole.setRoleName(roleName);
        expectedRole.setStatus(Constants.STATE_ACTIVATED);
        return expectedRole;
    }

    // Obtenir une liste vide de toutes les associations user-role
    @Test
    void getUserRoleWithEmptyArray() {
        List<UserRole> userRoles = usersRolesService.getAllUsersRoles();
        Assert.assertTrue(userRoles.isEmpty());
    }

    // Obtenir la liste de toutes les associations user-role
    @Test
    void getAllUserRoleWithSuccess() throws ClinicException {
        // création d'un rôle SPECIALIST
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // création de 2 utilisateurS
        User user1 = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        User user2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
        // instanciation des rôles minimales
        UserRoleMin userRoleMin1 = new UserRoleMin(user1.getId(), specialistRole.getRoleId());
        UserRoleMin userRoleMin2 = new UserRoleMin(user2.getId(), specialistRole.getRoleId());
        // création des associations user-role
        UserRole userRole1 = usersRolesService.createUserRole(userRoleMin1);
        UserRole userRole2 = usersRolesService.createUserRole(userRoleMin2);
        // modification du status de la seconde association et mise à jour
        userRole2.setStatus(Constants.STATE_DEACTIVATED);
        usersRolesService.updateUserRole(userRole2);

        List<UserRole> userRoles = usersRolesService.getAllUsersRoles();

        assertNotNull(userRole1);
        assertNotNull(userRole1.getUserRoleId());
        assertNotNull(userRole2);
        assertNotNull(userRole2.getUserRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRole1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, userRole2.getStatus());
        assertNotNull(userRoles);
        assertFalse(userRoles.isEmpty());
        assertEquals(2, userRoles.size());
    }

    /*
     * Ce test crée avec Succès une nouvelle entrée dans la table d'association
     * user-role. NB: le rôle et l'utilisateur correspondant existe bien en BD
     */
    @Test
    void createUserRoleWithSuccess() throws ClinicException {
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // création de l'association minimale contenant l'id
        // du user et l'id du role
        UserRoleMin userRoleMin = new UserRoleMin(createdUser.getId(), createdRole.getRoleId());
        // création de l'association
        UserRole userRoleSaved = usersRolesService.createUserRole(userRoleMin);
        // Recherche de l'aasociation créée
        UserRole userRoleFound = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // Vérifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(createdRole);
        assertNotNull(createdRole.getRoleId());
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        assertEquals(userRoleMin.getRoleId(), createdRole.getRoleId());
        assertEquals(userRoleMin.getUserId(), createdUser.getId());
        assertNotNull(userRoleSaved);
        assertNotNull(userRoleSaved.getUserRoleId());
        assertNotNull(userRoleSaved.getCreatedOn());
        assertNotNull(userRoleSaved.getLastUpdateOn());
        assertNotNull(userRoleSaved.getStatus());
        assertNotNull(userRoleFound);
        assertNotNull(userRoleFound.getUserRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRoleSaved.getStatus());
        assertEquals(createdUser.getId(), userRoleSaved.getUser().getId());
        assertEquals(createdRole.getRoleId(), userRoleSaved.getRole().getRoleId());
        assertEquals(createdUser.getId(), userRoleFound.getUser().getId());
        assertEquals(createdRole.getRoleId(), userRoleFound.getRole().getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRoleFound.getStatus());
    }

    // Ce test tente d'associer un rôle à un utilisateur inexistant en BD
    @Test
    void createUserRoleWithoutUserIdWithError() throws ClinicException {
        // Création du rôle
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // création d'un id inexistant pour l'utilisateur
        Integer userId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(userId, createdRole.getRoleId());
        // l'utilisateur n'existe pas, une exception sera lévée

        // Vérifications
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Ce test tente d'associer un rôle inexistant à un utilisateur existant
    // en Base de données
    @Test
    void createUserRoleWithoutRoleIdWithError() throws ClinicException {
        // création de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(false, true, true), true, true);
        // création d'un id inexistant pour le rôle
        Integer roleId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(createdUser.getId(), roleId);
        // le rôle n'existe pas, une exception sera lévée

        // Vérifications
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // Ce test tente d'associer un rôle inexistant
    // à un utilisateur inexistant en BD
    @Test
    void createUserRoleWithoutRoleIdAndUserIdWithError() {
        // création des ids inexistants pour l'utilisateur et le rôle
        Integer userId = 1;
        Integer roleId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(userId, roleId);
        // l'utilisateur et le role n'existe pas, une
        // exception sera lévée

        // Vérification
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_AND_ROLE_NOT_FOUND, e.getMessage());
    }

    // Ce test assigne avec succès un rôle existant
    // à un utilisateur existant
    // puis crée l'entrée correspondante
    // dans la table d'association user-role
    @Test
    void assignExistingRoleToExistingUserWithSuccess() throws ClinicException {
        // création du rôle et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // préparation de la liste des rôles à assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // appel de la fonction d'assignation à tester
        StateResponse stateResponse = usersRolesService.assignRolesToUser(createdUser.getId(), roleId);
        // recherche de l'association créée
        UserRole userRoleFound = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // Vérifications
        assertNotNull(userRoleFound);
        assertEquals(createdUser.getId(), userRoleFound.getUser().getId());
        assertEquals(createdRole.getRoleId(), userRoleFound.getRole().getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRoleFound.getStatus());
        assertEquals(1, usersRolesService.getAllUsersRoles().size());
        assertTrue(stateResponse.getState() == "SUCCEEDED");
    }

    // Assigner un role a un utilisateur supprime
    @Test
    void assignExistingRoleToDeletedUserWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // préparation de la liste des rôles à assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // suppression de l'utilisateur
        User deletedUser = userService.deleteUser(createdUser.getId());

        // Vérifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(deletedUser);
        assertNotNull(deletedUser.getId());
        assertEquals(createdUser.getId(), deletedUser.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.assignRolesToUser(deletedUser.getId(), roleId));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // Assigner un role a un utilisateur desactive
    @Test
    void assignExistingRoleToDeactivatedUserWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // préparation de la liste des rôles à assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // desactivation de l'utilisateur
        createdUser.setStatus(Constants.STATE_DEACTIVATED);
        userService.updateUserStatus(createdUser);
        // Recherche de l'utilisateur mis a jour
        User userUpdated = userService.findUserById(createdUser.getId());

        // Vérifications
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(userUpdated);
        assertNotNull(userUpdated.getId());
        assertEquals(createdUser.getId(), userUpdated.getId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.assignRolesToUser(userUpdated.getId(), roleId));
        assertEquals(Constants.ITEM_ALREADY_DEACTIVATED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DEACTIVATED, e.getMessage());
    }

    /*
     * Ce test tente d'assigner un rôle existant à un utilisateur inexistant
     */
    @Test
    void assignExistingRoleToNotFoundUserWithError() throws ClinicException {
        // création du rôle
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // préparation de la liste des rôles à assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // l'utilisateur n'existe pas, une exception sera lévée

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.assignRolesToUser(1, roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Ce test retire avec succès un rôle existant à un utilisateur
    // existant puis supprime l'entrée correspondante
    // dans la table d'association user-role
    @Test
    void removeExistingRoleToExistingUserWithSuccess() throws ClinicException {
        // création du rôle et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // procédure d'attribution du rôle à l'utilisateur
        List<Integer> rolesIdsListToSet = new ArrayList<Integer>();
        rolesIdsListToSet.add(createdRole.getRoleId());
        RolesIds roleIdToSet = new RolesIds();
        roleIdToSet.setRolesIdsList(rolesIdsListToSet);
        // appel de la fonction d'attribution du rôle
        StateResponse stateResponse1 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToSet);
        // recherche de l'association créée
        UserRole userRoleFound1 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // procédure de suppresion du rôle à l'utilisateur
        List<Integer> rolesIdsListToRemove = new ArrayList<Integer>();
        RolesIds roleIdToRemove = new RolesIds();
        // On initialise une liste vide de rôle
        roleIdToRemove.setRolesIdsList(rolesIdsListToRemove);
        // appel de la fonction de retraction du rôle
        StateResponse stateResponse2 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToRemove);
        // on tente de rechercher l'association créée précédement
        UserRole userRoleFound2 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // Vérifications
        // l'association existe pour l'instant
        assertNotNull(userRoleFound1);
        assertEquals(createdUser.getId(), userRoleFound1.getUser().getId());
        assertEquals(createdRole.getRoleId(), userRoleFound1.getRole().getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRoleFound1.getStatus());
        assertTrue(stateResponse1.getState() == "SUCCEEDED");
        // l'association n'existe plus
        assertEquals(0, usersRolesService.getAllUsersRoles().size());
        assertTrue(stateResponse2.getState() == "SUCCEEDED");
        assertNull(userRoleFound2);
    }

    // Ce test tente de retirer un rôle existant
    // à un utilisateur supprime
    @Test
    void removeExistingRoleToNotExistingUserWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // procédure d'attribution du rôle à l'utilisateur
        List<Integer> rolesIdsListToSet = new ArrayList<Integer>();
        rolesIdsListToSet.add(createdRole.getRoleId());
        RolesIds roleIdToSet = new RolesIds();
        roleIdToSet.setRolesIdsList(rolesIdsListToSet);
        // appel de la fonction d'attribution du rôle
        StateResponse stateResponse1 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToSet);
        // recherche de l'association créée
        UserRole userRoleFound1 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // procédure de retraction du rôle à l'utilisateur
        // on supprime d'abord l'utilisateur
        createdUser.setStatus(Constants.STATE_DELETED);
        userService.updateUserStatus(createdUser);
        List<Integer> rolesIdsListToRemove = new ArrayList<Integer>();
        RolesIds roleIdToRemove = new RolesIds();
        // On initialise une liste vide de rôle
        roleIdToRemove.setRolesIdsList(rolesIdsListToRemove);
        // on recherche à nouveau l'utilisateur
        User userFound = userService.findUserById(createdUser.getId());
        // l'utilisateur a le statut DELETED, une exception
        // sera lévée

        // Vérifications

        assertTrue(stateResponse1.getState() == "SUCCEEDED");
        assertNotNull(userRoleFound1);
        assertEquals(createdUser.getId(), userRoleFound1.getUser().getId());
        assertEquals(createdRole.getRoleId(), userRoleFound1.getRole().getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, userRoleFound1.getStatus());
        assertEquals(Constants.STATE_DELETED, userFound.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToRemove));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.USER_ALREADY_DELETED, e.getMessage());
    }

    // rechercher une association user-role
    // cas 1: le rôle et l'utilisateur existent
    @Test
    void findUserRoleByExistingRoleIdAndExistingUserIdWithSuccess() throws ClinicException {
        // création du rôle et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // procédure d'attribution d'un rôle à un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du rôle
        StateResponse stateResponse = usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // recherche de l'association créée
        UserRole userRoleFound = usersRolesService.findUserRoleByUserIdAndRoleId(userSaved.getId(),
                roleSaved.getRoleId());

        // Vérifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(userSaved);
        assertNotNull(userSaved.getId());
        assertNotNull(userRoleFound);
        assertEquals(roleSaved.getRoleId(), userRoleFound.getRole().getRoleId());
        assertEquals(userSaved.getId(), userRoleFound.getUser().getId());
        assertTrue(stateResponse.getState() == "SUCCEEDED");
    }

    // rechercher une association user-role
    // cas 2: le rôle existe et l'utilisateur n'existe pas
    @Test
    void findUserRoleByExistingRoleIdAndNotExistingUserIdWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // procédure d'attribution d'un rôle à un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du rôle
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant de l'utilisateur
        Integer userId = 2;
        // une exception sera lévée car l'utilisateur n'existe pas

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userId,
                        roleSaved.getRoleId()));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // rechercher une association user-role
    // cas 3: le rôle n'existe pas et l'utilisateur existe
    @Test
    void findUserRoleByNotExistingRoleIdAndExistingUserIdWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // procédure d'attribution d'un rôle à un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du rôle
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant du rôle
        Integer roleId = 2;
        // une exception sera lévée car l'utilisateur n'existe pas

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userSaved.getId(),
                        roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // rechercher une association user-role
    // cas 4: le rôle et l'utilisateur n'existent pas
    @Test
    void findUserRoleByNotExistingRoleIdAndNotExistingUserIdWithError() throws ClinicException {
        // création du rôle et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // procédure d'attribution d'un rôle à un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du rôle
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant du rôle et de l'utilisateur
        Integer roleId = 2;
        Integer userId = 2;
        // une exception sera lévée car l'utilisateur n'existe pas

        // Vérifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userId,
                        roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_AND_ROLE_NOT_FOUND, e.getMessage());
    }
}
