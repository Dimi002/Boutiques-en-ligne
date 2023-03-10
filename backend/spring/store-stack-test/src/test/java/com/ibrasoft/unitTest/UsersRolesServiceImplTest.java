package com.ibrasoft.unitTest;

import java.util.List;
import java.util.Random;

import com.ibrasoft.storeStackProd.repository.*;
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

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.RoleRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.response.RolesIds;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.response.UserRoleMin;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.service.UsersRolesService;
import com.ibrasoft.storeStackProd.util.Constants;

@SpringBootTest(classes = StoreStackProdApplication.class)
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
        // cr??ation d'un r??le SPECIALIST
        Role specialistRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // cr??ation de 2 utilisateurS
        User user1 = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), false, true);
        User user2 = userService.createAdminOrSpecialist(buildRandomUser(false, false, true), false, true);
        // instanciation des r??les minimales
        UserRoleMin userRoleMin1 = new UserRoleMin(user1.getId(), specialistRole.getRoleId());
        UserRoleMin userRoleMin2 = new UserRoleMin(user2.getId(), specialistRole.getRoleId());
        // cr??ation des associations user-role
        UserRole userRole1 = usersRolesService.createUserRole(userRoleMin1);
        UserRole userRole2 = usersRolesService.createUserRole(userRoleMin2);
        // modification du status de la seconde association et mise ?? jour
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
     * Ce test cr??e avec Succ??s une nouvelle entr??e dans la table d'association
     * user-role. NB: le r??le et l'utilisateur correspondant existe bien en BD
     */
    @Test
    void createUserRoleWithSuccess() throws ClinicException {
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, true), true, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // cr??ation de l'association minimale contenant l'id
        // du user et l'id du role
        UserRoleMin userRoleMin = new UserRoleMin(createdUser.getId(), createdRole.getRoleId());
        // cr??ation de l'association
        UserRole userRoleSaved = usersRolesService.createUserRole(userRoleMin);
        // Recherche de l'aasociation cr????e
        UserRole userRoleFound = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // V??rifications
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

    // Ce test tente d'associer un r??le ?? un utilisateur inexistant en BD
    @Test
    void createUserRoleWithoutUserIdWithError() throws ClinicException {
        // Cr??ation du r??le
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // cr??ation d'un id inexistant pour l'utilisateur
        Integer userId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(userId, createdRole.getRoleId());
        // l'utilisateur n'existe pas, une exception sera l??v??e

        // V??rifications
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Ce test tente d'associer un r??le inexistant ?? un utilisateur existant
    // en Base de donn??es
    @Test
    void createUserRoleWithoutRoleIdWithError() throws ClinicException {
        // cr??ation de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(false, true, true), true, true);
        // cr??ation d'un id inexistant pour le r??le
        Integer roleId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(createdUser.getId(), roleId);
        // le r??le n'existe pas, une exception sera l??v??e

        // V??rifications
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // Ce test tente d'associer un r??le inexistant
    // ?? un utilisateur inexistant en BD
    @Test
    void createUserRoleWithoutRoleIdAndUserIdWithError() {
        // cr??ation des ids inexistants pour l'utilisateur et le r??le
        Integer userId = 1;
        Integer roleId = 1;
        UserRoleMin userRoleMin = new UserRoleMin(userId, roleId);
        // l'utilisateur et le role n'existe pas, une
        // exception sera l??v??e

        // V??rification
        assertNotNull(userRoleMin);
        assertNotNull(userRoleMin.getRoleId());
        assertNotNull(userRoleMin.getUserId());
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.createUserRole(userRoleMin));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_AND_ROLE_NOT_FOUND, e.getMessage());
    }

    // Ce test assigne avec succ??s un r??le existant
    // ?? un utilisateur existant
    // puis cr??e l'entr??e correspondante
    // dans la table d'association user-role
    @Test
    void assignExistingRoleToExistingUserWithSuccess() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // pr??paration de la liste des r??les ?? assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // appel de la fonction d'assignation ?? tester
        StateResponse stateResponse = usersRolesService.assignRolesToUser(createdUser.getId(), roleId);
        // recherche de l'association cr????e
        UserRole userRoleFound = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // V??rifications
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
        // cr??ation du r??le et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // pr??paration de la liste des r??les ?? assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // suppression de l'utilisateur
        User deletedUser = userService.deleteUser(createdUser.getId());

        // V??rifications
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
        // cr??ation du r??le et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // pr??paration de la liste des r??les ?? assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // desactivation de l'utilisateur
        createdUser.setStatus(Constants.STATE_DEACTIVATED);
        userService.updateUserStatus(createdUser);
        // Recherche de l'utilisateur mis a jour
        User userUpdated = userService.findUserById(createdUser.getId());

        // V??rifications
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
     * Ce test tente d'assigner un r??le existant ?? un utilisateur inexistant
     */
    @Test
    void assignExistingRoleToNotFoundUserWithError() throws ClinicException {
        // cr??ation du r??le
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // pr??paration de la liste des r??les ?? assigner
        List<Integer> rolesIdsList = new ArrayList<Integer>();
        rolesIdsList.add(createdRole.getRoleId());
        RolesIds roleId = new RolesIds();
        roleId.setRolesIdsList(rolesIdsList);
        // l'utilisateur n'existe pas, une exception sera l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class, () -> usersRolesService.assignRolesToUser(1, roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // Ce test retire avec succ??s un r??le existant ?? un utilisateur
    // existant puis supprime l'entr??e correspondante
    // dans la table d'association user-role
    @Test
    void removeExistingRoleToExistingUserWithSuccess() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // proc??dure d'attribution du r??le ?? l'utilisateur
        List<Integer> rolesIdsListToSet = new ArrayList<Integer>();
        rolesIdsListToSet.add(createdRole.getRoleId());
        RolesIds roleIdToSet = new RolesIds();
        roleIdToSet.setRolesIdsList(rolesIdsListToSet);
        // appel de la fonction d'attribution du r??le
        StateResponse stateResponse1 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToSet);
        // recherche de l'association cr????e
        UserRole userRoleFound1 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // proc??dure de suppresion du r??le ?? l'utilisateur
        List<Integer> rolesIdsListToRemove = new ArrayList<Integer>();
        RolesIds roleIdToRemove = new RolesIds();
        // On initialise une liste vide de r??le
        roleIdToRemove.setRolesIdsList(rolesIdsListToRemove);
        // appel de la fonction de retraction du r??le
        StateResponse stateResponse2 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToRemove);
        // on tente de rechercher l'association cr????e pr??c??dement
        UserRole userRoleFound2 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // V??rifications
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

    // Ce test tente de retirer un r??le existant
    // ?? un utilisateur supprime
    @Test
    void removeExistingRoleToNotExistingUserWithError() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        User createdUser = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        // proc??dure d'attribution du r??le ?? l'utilisateur
        List<Integer> rolesIdsListToSet = new ArrayList<Integer>();
        rolesIdsListToSet.add(createdRole.getRoleId());
        RolesIds roleIdToSet = new RolesIds();
        roleIdToSet.setRolesIdsList(rolesIdsListToSet);
        // appel de la fonction d'attribution du r??le
        StateResponse stateResponse1 = usersRolesService.assignRolesToUser(createdUser.getId(), roleIdToSet);
        // recherche de l'association cr????e
        UserRole userRoleFound1 = userRoleRepository.findByUserRoleIdUserAndUserRoleIdRole(createdUser, createdRole);

        // proc??dure de retraction du r??le ?? l'utilisateur
        // on supprime d'abord l'utilisateur
        createdUser.setStatus(Constants.STATE_DELETED);
        userService.updateUserStatus(createdUser);
        List<Integer> rolesIdsListToRemove = new ArrayList<Integer>();
        RolesIds roleIdToRemove = new RolesIds();
        // On initialise une liste vide de r??le
        roleIdToRemove.setRolesIdsList(rolesIdsListToRemove);
        // on recherche ?? nouveau l'utilisateur
        User userFound = userService.findUserById(createdUser.getId());
        // l'utilisateur a le statut DELETED, une exception
        // sera l??v??e

        // V??rifications

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
    // cas 1: le r??le et l'utilisateur existent
    @Test
    void findUserRoleByExistingRoleIdAndExistingUserIdWithSuccess() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // proc??dure d'attribution d'un r??le ?? un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du r??le
        StateResponse stateResponse = usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // recherche de l'association cr????e
        UserRole userRoleFound = usersRolesService.findUserRoleByUserIdAndRoleId(userSaved.getId(),
                roleSaved.getRoleId());

        // V??rifications
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
    // cas 2: le r??le existe et l'utilisateur n'existe pas
    @Test
    void findUserRoleByExistingRoleIdAndNotExistingUserIdWithError() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // proc??dure d'attribution d'un r??le ?? un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du r??le
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant de l'utilisateur
        Integer userId = 2;
        // une exception sera l??v??e car l'utilisateur n'existe pas

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userId,
                        roleSaved.getRoleId()));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_NOT_FOUND, e.getMessage());
    }

    // rechercher une association user-role
    // cas 3: le r??le n'existe pas et l'utilisateur existe
    @Test
    void findUserRoleByNotExistingRoleIdAndExistingUserIdWithError() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // proc??dure d'attribution d'un r??le ?? un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du r??le
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant du r??le
        Integer roleId = 2;
        // une exception sera l??v??e car l'utilisateur n'existe pas

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userSaved.getId(),
                        roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // rechercher une association user-role
    // cas 4: le r??le et l'utilisateur n'existent pas
    @Test
    void findUserRoleByNotExistingRoleIdAndNotExistingUserIdWithError() throws ClinicException {
        // cr??ation du r??le et de l'utilisateur
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.ADMIN));
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(true, true, false), false, true);
        // proc??dure d'attribution d'un r??le ?? un utilisateur
        List<Integer> roleIdsListToSet = new ArrayList<Integer>();
        roleIdsListToSet.add(roleSaved.getRoleId());
        RolesIds roleIdsToSet = new RolesIds();
        roleIdsToSet.setRolesIdsList(roleIdsListToSet);
        // appel de la fonction d'attribution du r??le
        usersRolesService.assignRolesToUser(userSaved.getId(),
                roleIdsToSet);
        // id non existant du r??le et de l'utilisateur
        Integer roleId = 2;
        Integer userId = 2;
        // une exception sera l??v??e car l'utilisateur n'existe pas

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> usersRolesService.findUserRoleByUserIdAndRoleId(userId,
                        roleId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.USER_AND_ROLE_NOT_FOUND, e.getMessage());
    }
}
