package com.ibrasoft.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.beans.RolePermissionId;
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
import com.ibrasoft.storeStackProd.response.PermissionsIds;
import com.ibrasoft.storeStackProd.response.RolePermissionMin;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.service.PermissionService;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.service.RolesPermissionsService;
import com.ibrasoft.storeStackProd.util.Constants;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RolesPermissionsServiceImplTest {

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @Autowired
    RolesPermissionsService rolesPermissionsService;

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

    // cette m??thode g??n??re une chaine de caract??re al??atoire
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

    // cette m??thode cr??e une permission al??atoire
    public Permission buildRandomPermission(Boolean name, Boolean description) {
        Permission expectedPermission = new Permission();

        if (description)
            expectedPermission.setPermissionDesc("Permission de cr??er l'administrateur");
        else
            expectedPermission.setPermissionDesc(generatRandomString(20));
        if (name)
            expectedPermission.setPermissionName("CREATE_ADMIN");
        else
            expectedPermission.setPermissionName(generatRandomString(8));
        expectedPermission.setStatus(Constants.STATE_ACTIVATED);
        return expectedPermission;
    }

    // cette m??thode cr??e un r??le al??atoire
    public Role buildRandomRole(Boolean name, Boolean description) {
        Role expectedRole = new Role();
        if (description)
            expectedRole.setRoleDesc("le super administrateur");
        else
            expectedRole.setRoleDesc(generatRandomString(20));
        if (name)
            expectedRole.setRoleName("SUPER_ADMIN");
        else
            expectedRole.setRoleName(generatRandomString(8));
        expectedRole.setStatus(Constants.STATE_ACTIVATED);
        return expectedRole;
    }

    // Cr??ation d'une association r??le-permission
    // avec r??le existant et une permission existante
    @Test
    void createRolePermissionWithSuccess() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // cr??ation de l'association minimale role-permission
        RolePermissionMin rolePermissionstoSave = new RolePermissionMin(permissiontoSaved.getPermissionId(),
                roleSaved.getRoleId());
        // appel de la fonction de cr??ation de l'association
        RolePermission rolePermissionSaved = rolesPermissionsService.createRolePermission(rolePermissionstoSave);

        // V??rifications
        assertNotNull(roleSaved);
        assertNotNull(permissiontoSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(permissiontoSaved.getPermissionId());
        assertNotNull(rolePermissionstoSave);
        assertNotNull(rolePermissionSaved);
        assertNotNull(rolePermissionSaved.getRolePermissionId());
        assertNotNull(rolePermissionSaved.getRole().getRoleId());
        assertNotNull(rolePermissionSaved.getPermission().getPermissionId());
        assertEquals(roleSaved.getRoleId(), rolePermissionstoSave.getRoleId());
        assertEquals(permissiontoSaved.getPermissionId(), rolePermissionstoSave.getPermissionId());
        assertEquals(roleSaved.getRoleId(), rolePermissionSaved.getRole().getRoleId());
        assertEquals(permissiontoSaved.getPermissionId(), rolePermissionSaved.getPermission().getPermissionId());
        assertEquals(rolePermissionstoSave.getRoleId(), rolePermissionSaved.getRole().getRoleId());
        assertEquals(permissiontoSaved.getPermissionId(), rolePermissionSaved.getPermission().getPermissionId());
    }

    // Cr??ation d'une association r??le-permission avec r??le existant
    // et une pemission inexistante
    @Test
    void createRolePermissionWithExistingRoleAndNotExistingPermissionWithError() throws ClinicException {
        // cr??ation du r??le
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        // id de permission non existant
        Integer permissionId = 1;
        RolePermissionMin rolePermissionstoSave = new RolePermissionMin(permissionId, roleSaved.getRoleId());
        // la permission n'existe pas, une exception sera l??v??e

        // v??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.createRolePermission(rolePermissionstoSave));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.PERMISSION_NOT_FOUND, e.getMessage());
    }

    // Cr??ation d'une association r??le-permission avec r??le inexistant
    // et une pemission existante
    @Test
    void createRolePermissionWithNotExistingRoleAndExistingPermissionWithError() throws ClinicException {
        // cr??ation de la permission
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // id de role non existant
        Integer roleId = 1;
        RolePermissionMin rolePermissionstoSave = new RolePermissionMin(permissiontoSaved.getPermissionId(), roleId);
        // le role n'existe pas, une exception sera l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.createRolePermission(rolePermissionstoSave));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // Cr??ation d'une association r??le-permission avec r??le inexistant
    // et une pemission inexistante
    @Test
    void createRolePermissionWithNotExistingRoleAndNotExistingPermissionWithError() {
        // ids non existant de r??le et de permission
        Integer roleId = 1;
        Integer permissionId = 1;
        RolePermissionMin rolePermissionstoSave = new RolePermissionMin(permissionId, roleId);
        // le r??le et la permission n'existe pas, une exception sera l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.createRolePermission(rolePermissionstoSave));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_AND_PERMISSION_NOT_FOUND, e.getMessage());
    }

    // test d'obtention de la liste vide des associations r??le-permission
    @Test
    void getRolesPermissionsWithEmptyArray() {
        // il n'existe pas d'association
        List<RolePermission> rolespermissions = rolesPermissionsService.getAllRolesPermissions();
        Assert.assertTrue(rolespermissions.isEmpty());
    }

    // test d'obtention de la liste non vide des associations r??le-permission
    @Test
    void getRolesPermissionsWithSuccess() throws ClinicException {
        // cr??ation des r??les et des permissions
        Role roleSaved1 = roleService.createOrUpdateRole(buildRandomRole(false, true));
        Permission permissiontoSaved1 = permissionService.createPermission(buildRandomPermission(false, true));
        Role roleSaved2 = roleService.createOrUpdateRole(buildRandomRole(false, true));
        Permission permissiontoSaved2 = permissionService.createPermission(buildRandomPermission(false, true));
        // cr??ation des associations minimales role-permission
        RolePermissionMin rolesPermissionstoSave1 = new RolePermissionMin(permissiontoSaved1.getPermissionId(),
                roleSaved1.getRoleId());
        RolePermissionMin rolesPermissionstoSave2 = new RolePermissionMin(permissiontoSaved2.getPermissionId(),
                roleSaved2.getRoleId());
        // sauvegarde des associations
        RolePermission rolesPermissionSaved1 = rolesPermissionsService.createRolePermission(rolesPermissionstoSave1);
        RolePermission rolesPermissionSaved2 = rolesPermissionsService.createRolePermission(rolesPermissionstoSave2);
        // d??sactivation de la seconde association et mise ?? jour
        rolesPermissionSaved2.setStatus(Constants.STATE_DEACTIVATED);
        RolePermission rolesPermissionSaved2Updated = rolesPermissionsService
                .updateRolePermission(rolesPermissionSaved2);
        // cr??tion de la liste des associations role-Permission
        List<RolePermission> rolespermissions = rolesPermissionsService.getAllRolesPermissions();

        // V??rifications
        assertNotNull(roleSaved1);
        assertNotNull(roleSaved1.getRoleId());
        assertNotNull(permissiontoSaved1);
        assertNotNull(permissiontoSaved1.getPermissionId());
        assertNotNull(rolesPermissionSaved1);
        assertNotNull(rolesPermissionSaved2);
        assertEquals(Constants.STATE_ACTIVATED, rolesPermissionSaved1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, rolesPermissionSaved2Updated.getStatus());
        assertNotNull(rolespermissions);
        assertFalse(rolespermissions.isEmpty());
        assertEquals(2, rolespermissions.size());
    }

    /*
     * Ce test assigne avec succ??s une permission existante
     * ?? un r??le existant puis cr??e l'entr??e correspondante
     * dans la table d'association role-permission
     */
    @Test
    void assignPermissioToRoleWithSuccess() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // pr??paration de la liste des permissions ?? assigner
        List<Integer> permissionsIdsList = new ArrayList<Integer>();
        permissionsIdsList.add(permissiontoSaved.getPermissionId());
        PermissionsIds permissionsIds = new PermissionsIds();
        permissionsIds.setPermissionsIdsList(permissionsIdsList);
        // appel de la fonction d'assignation d'une permission
        // ?? un r??le
        StateResponse stateResponse = rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIds);
        // recherche de l'association cr????e
        RolePermissionId rolePermissionId = new RolePermissionId(roleSaved, permissiontoSaved);
        RolePermission rolePermissionFound = rolePermissionRepository.findByRolePermissionId(rolePermissionId);

        // V??rifications
        assertNotNull(rolePermissionFound);
        assertNotNull(rolePermissionFound.getRole());
        assertNotNull(rolePermissionFound.getPermission());
        assertNotNull(rolePermissionFound.getRolePermissionId());
        assertEquals(roleSaved.getRoleId(), rolePermissionFound.getRole().getRoleId());
        assertEquals(permissiontoSaved.getPermissionId(), rolePermissionFound.getPermission().getPermissionId());
        assertEquals(rolePermissionId.getRole().getRoleId(), rolePermissionFound.getRole().getRoleId());
        assertEquals(rolePermissionId.getPermission().getPermissionId(),
                rolePermissionFound.getPermission().getPermissionId());
        assertEquals(1, rolesPermissionsService.getAllRolesPermissions().size());
        assertTrue(stateResponse.getState() == "SUCCEEDED");
    }

    // Assigner une permission ?? un r??le supprime
    @Test
    void assignPermissioToDeletedRoleWithError() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // pr??paration de la liste des permissions ?? assigner
        List<Integer> permissionsIdsList = new ArrayList<Integer>();
        permissionsIdsList.add(permissiontoSaved.getPermissionId());
        PermissionsIds permissionsIds = new PermissionsIds();
        permissionsIds.setPermissionsIdsList(permissionsIdsList);
        // suppression du role
        Role roleDeleted = roleService.deleteRole(roleSaved.getRoleId());
        // Verifications

        // V??rifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(permissiontoSaved);
        assertNotNull(permissiontoSaved.getPermissionId());
        assertEquals(roleSaved.getRoleId(), roleDeleted.getRoleId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                        permissionsIds));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.ROLE_ALREADY_DELETED, e.getMessage());
    }

    // Assigner une permission ?? un r??le desactive
    @Test
    void assignPermissioToDeactivatedRoleWithError() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // pr??paration de la liste des permissions ?? assigner
        List<Integer> permissionsIdsList = new ArrayList<Integer>();
        permissionsIdsList.add(permissiontoSaved.getPermissionId());
        PermissionsIds permissionsIds = new PermissionsIds();
        permissionsIds.setPermissionsIdsList(permissionsIdsList);
        // desactivation du role
        roleSaved.setStatus(Constants.STATE_DEACTIVATED);
        Role roleDeactivated = roleService.createOrUpdateRole(roleSaved);

        // V??rifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(permissiontoSaved);
        assertNotNull(permissiontoSaved.getPermissionId());
        assertEquals(roleSaved.getRoleId(), roleDeactivated.getRoleId());
        assertEquals(Constants.STATE_DEACTIVATED, roleDeactivated.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.assignPermissionsToRole(roleDeactivated.getRoleId(),
                        permissionsIds));
        assertEquals(Constants.ITEM_ALREADY_DEACTIVATED, e.getCode());
        assertEquals(Constants.ROLE_ALREADY_DEACTIVATED, e.getMessage());
    }

    /*
     * Ce test tente d'assigner une permission existante ?? un r??le inexistant
     */
    @Test
    void assignExistingPermissionToNotExistingRoleWithError() throws ClinicException {
        // Cr??ation de la permission
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // id non existing pour un role
        Integer roleId = 1;
        // pr??paration de la liste des permissions ?? assigner
        List<Integer> permissionsIdsList = new ArrayList<Integer>();
        permissionsIdsList.add(permissiontoSaved.getPermissionId());
        PermissionsIds permissionsIds = new PermissionsIds();
        permissionsIds.setPermissionsIdsList(permissionsIdsList);

        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.assignPermissionsToRole(roleId, permissionsIds));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    /*
     * Ce test r??tire avec succ??s une permission existante
     * ?? un r??le existant puis supprime l'entr??e correspondante
     * dans la table d'association role-permission
     */
    @Test
    void removePermissioToRoleWithSuccess() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissiontoSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // processus d'assignation de la permission au r??le
        // pr??paration de la liste des permissions ?? assigner
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissiontoSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'assignation d'une permission
        // ?? un r??le
        StateResponse stateResponse1 = rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // recherche de l'association cr????e
        RolePermissionId rolePermissionId = new RolePermissionId(roleSaved, permissiontoSaved);
        RolePermission rolePermissionFound1 = rolePermissionRepository.findByRolePermissionId(rolePermissionId);

        // processus d'assignation de la permission au r??le
        // pr??paration de la liste des permissions ?? r??tirer
        List<Integer> permissionsIdsListToRemove = new ArrayList<Integer>();
        PermissionsIds permissionsIdsToRemove = new PermissionsIds();
        permissionsIdsToRemove.setPermissionsIdsList(permissionsIdsListToRemove);
        // appel de la fonction d'assignation d'une permission
        // ?? un r??le
        StateResponse stateResponse2 = rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToRemove);
        // On recherche ?? nouveau l'association cr????e
        RolePermission rolePermissionFound2 = rolePermissionRepository.findByRolePermissionId(rolePermissionId);

        // V??rifications
        assertNotNull(rolePermissionFound1);
        assertNotNull(rolePermissionFound1.getRole());
        assertNotNull(rolePermissionFound1.getPermission());
        assertNotNull(rolePermissionFound1.getRolePermissionId());
        assertEquals(roleSaved.getRoleId(), rolePermissionFound1.getRole().getRoleId());
        assertEquals(permissiontoSaved.getPermissionId(), rolePermissionFound1.getPermission().getPermissionId());
        assertEquals(rolePermissionId.getRole().getRoleId(), rolePermissionFound1.getRole().getRoleId());
        assertEquals(rolePermissionId.getPermission().getPermissionId(),
                rolePermissionFound1.getPermission().getPermissionId());
        assertEquals(0, rolesPermissionsService.getAllRolesPermissions().size());
        assertTrue(stateResponse1.getState() == "SUCCEEDED");
        assertTrue(stateResponse2.getState() == "SUCCEEDED");
        assertNull(rolePermissionFound2);
    }

    // Ce test tente de retirer une permission existante
    // ?? un r??le inexistant
    @Test
    void removeExistingPermissionToNotExistingRoleWithError() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissionSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // proc??dure d'attribution de la permission au r??le
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissionSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'attribution de la permission
        StateResponse stateResponse1 = rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // recherche de l'association cr????e
        RolePermission rolePermissionFound1 = rolesPermissionsService
                .findRolePermissionByRoleIdAndPermissionId(roleSaved.getRoleId(), permissionSaved.getPermissionId());

        // proc??dure de retraction de la permission au r??le
        // on supprime d'abord le r??le
        roleSaved.setStatus(Constants.STATE_DELETED);
        roleService.createOrUpdateRole(roleSaved);
        List<Integer> permissionIdsListToRemove = new ArrayList<Integer>();
        PermissionsIds permissionsIdsToRemove = new PermissionsIds();
        // On initialise une liste vide de permission
        permissionsIdsToRemove.setPermissionsIdsList(permissionIdsListToRemove);
        // on recherche ?? nouveau le r??le
        Role roleFound = roleService.findRoleById(roleSaved.getRoleId());
        // le r??le a le statut DELETED, une exception
        // sera l??v??e

        // V??rifications

        assertTrue(stateResponse1.getState() == "SUCCEEDED");
        assertNotNull(rolePermissionFound1);
        assertEquals(roleSaved.getRoleId(), rolePermissionFound1.getRole().getRoleId());
        assertEquals(permissionSaved.getPermissionId(), rolePermissionFound1.getPermission().getPermissionId());
        assertEquals(Constants.STATE_ACTIVATED, rolePermissionFound1.getStatus());
        assertEquals(Constants.STATE_DELETED, roleFound.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(), permissionsIdsToRemove));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.ROLE_ALREADY_DELETED, e.getMessage());
    }

    // rechercher une association role-permission
    // cas 1: le r??le et la permission existent
    @Test
    void findRolePermissionByExistingRoleIdAndExistingPermissionIdWithSuccess() throws ClinicException {
        // cr??ation du r??le et de la permission
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissionSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // proc??dure d'attribution de la permission au r??le
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissionSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'attribution de la permission
        StateResponse stateResponse = rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // recherche de l'association cr????e
        RolePermission rolePermissionFound = rolesPermissionsService
                .findRolePermissionByRoleIdAndPermissionId(roleSaved.getRoleId(), permissionSaved.getPermissionId());

        // V??rifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(permissionSaved);
        assertNotNull(permissionSaved.getPermissionId());
        assertNotNull(rolePermissionFound);
        assertEquals(roleSaved.getRoleId(), rolePermissionFound.getRole().getRoleId());
        assertEquals(permissionSaved.getPermissionId(), rolePermissionFound.getPermission().getPermissionId());
        assertTrue(stateResponse.getState() == "SUCCEEDED");
    }

    // rechercher une association role-permission
    // cas 2: le r??le existe et la permission n'existent pas
    @Test
    void findRolePermissionByExistingRoleIdAndNotExistingPermissionIdWithError() throws ClinicException {
        // cr??ation du r??le
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissionSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // proc??dure d'attribution de la permission au r??le
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissionSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'attribution de la permission
        rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // id de permission non existant
        Integer permissionId = 2;
        // la permission n'existe pas, une exception est l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.findRolePermissionByRoleIdAndPermissionId(roleSaved.getRoleId(),
                        permissionId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.PERMISSION_NOT_FOUND, e.getMessage());

    }

    // rechercher une association role-permission
    // cas 3: le r??le n'existent pas et la permission existent
    @Test
    void findRolePermissionByNotExistingRoleIdAndExistingPermissionIdWithError() throws ClinicException {
        // cr??ation du r??le
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissionSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // proc??dure d'attribution de la permission au r??le
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissionSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'attribution de la permission
        rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // id de r??le non existant
        Integer roleId = 2;
        // le r??le n'existe pas, une exception est l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.findRolePermissionByRoleIdAndPermissionId(roleId,
                        permissionSaved.getPermissionId()));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // rechercher une association role-permission
    // cas 4: le role et la permission n'existent pas
    @Test
    void findRolePermissionByNotExistingRoleIdAndNotExistingPermissionIdWithError() throws ClinicException {
        // cr??ation du r??le
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Permission permissionSaved = permissionService.createPermission(buildRandomPermission(true, true));
        // proc??dure d'attribution de la permission au r??le
        List<Integer> permissionsIdsListToSet = new ArrayList<Integer>();
        permissionsIdsListToSet.add(permissionSaved.getPermissionId());
        PermissionsIds permissionsIdsToSet = new PermissionsIds();
        permissionsIdsToSet.setPermissionsIdsList(permissionsIdsListToSet);
        // appel de la fonction d'attribution de la permission
        rolesPermissionsService.assignPermissionsToRole(roleSaved.getRoleId(),
                permissionsIdsToSet);
        // id de r??le et de permission non existant
        Integer roleId = 2;
        Integer permissionId = 2;
        // le r??le et la permission n'existent pas, une exception est l??v??e

        // V??rifications
        ClinicException e = assertThrows(ClinicException.class,
                () -> rolesPermissionsService.findRolePermissionByRoleIdAndPermissionId(roleId, permissionId));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_AND_PERMISSION_NOT_FOUND, e.getMessage());
    }
}
