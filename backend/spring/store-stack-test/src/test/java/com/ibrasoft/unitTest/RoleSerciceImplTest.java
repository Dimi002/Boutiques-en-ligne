package com.ibrasoft.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;

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

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.ibrasoft.storeStackProd.StoreStackProdApplication;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.util.Constants;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoleSerciceImplTest {

    @Autowired
    RoleService roleService;

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

    // cette méthode crée un rôle aléatoire
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

    // cette méthode génère une chaine de caractère aléatoire
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

    @BeforeEach
    public void deleteAllUsers() {
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

    // Obtenir la liste vide des rôles
    @Test
    void getRoleWithEmptyArray() {
        List<Role> roles = roleService.getAllRole();
        Assert.assertTrue(roles.isEmpty());
    }

    // Création d'un nouveau rôle
    @Test
    void createRoleWithSuccess() throws ClinicException {
        Role expectedRole = buildRandomRole(false, false);

        Role savedRole = roleService.createOrUpdateRole(expectedRole);

        assertNotNull(savedRole);
        assertNotNull(savedRole.getRoleId());
        assertNotNull(savedRole.getCreatedOn());
        assertNotNull(savedRole.getLastUpdateOn());
        assertEquals(expectedRole.getRoleDesc(), savedRole.getRoleDesc());
        assertEquals(expectedRole.getRoleName(), savedRole.getRoleName());
        assertEquals(expectedRole.getStatus(), savedRole.getStatus());
    }

    // Création d'un nouveau rôle avec un nom existant
    @Test
    void createRoleWithExistingRoleNameWithError() throws ClinicException {
        Role expectedRole1 = buildRandomRole(true, true);
        Role expectedRole2 = buildRandomRole(true, true);
        roleService.createOrUpdateRole(expectedRole1);
        ClinicException e = assertThrows(ClinicException.class,
                () -> roleService.createOrUpdateRole(expectedRole2));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.ROLE_NAME_ALREADY_EXIST, e.getMessage());
    }

    // Mise à jour d'un rôle avec un nom existant
    @Test
    void updateRoleWithExistingRoleNameWithError() throws ClinicException {
        Role createdRole1 = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Role createdRole2 = roleService.createOrUpdateRole(buildRandomRole(false, true));
        createdRole2.setRoleName(createdRole1.getRoleName());

        ClinicException e = assertThrows(ClinicException.class, () -> roleService.createOrUpdateRole(createdRole2));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.ROLE_NAME_ALREADY_EXIST, e.getMessage());
    }

    // Mise à jour d'un rôle avec un nom inexistant
    @Test
    void updateRoleWithNotExistingRoleNameWithSuccess() throws ClinicException {
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(true, true));

        String newRoleName = "CHEF_PROJET";
        createdRole.setRoleName(newRoleName);

        Role updatedRole = roleService.createOrUpdateRole(createdRole);
        assertNotNull(updatedRole);
        assertNotNull(updatedRole.getRoleId());
        assertNotNull(updatedRole.getLastUpdateOn());
        assertEquals(createdRole.getRoleId(), updatedRole.getRoleId());
        assertEquals(createdRole.getRoleName(), updatedRole.getRoleName());
        assertEquals(createdRole.getRoleDesc(), updatedRole.getRoleDesc());
    }

    // Mise à jour d'un role supprime
    @Test
    void updateDeletedRoleWithError() throws ClinicException {
        // creation du role
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(true, true));
        // suppression du role
        Role roleDeleted = roleService.deleteRole(roleSaved.getRoleId());
        // mise a jour
        roleDeleted.setRoleDesc("new description");

        // verifications
        assertNotNull(roleSaved);
        assertNotNull(roleSaved.getRoleId());
        assertNotNull(roleDeleted.getLastUpdateOn());
        assertEquals(roleSaved.getRoleId(), roleDeleted.getRoleId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> roleService.createOrUpdateRole(roleDeleted));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.ROLE_ALREADY_DELETED, e.getMessage());
    }

    // Mise à jour d'un rôle avec les mêmes valeurs de nom et de description
    @Test
    void updateRoleWithSameValuesWithSuccess() throws ClinicException {
        Role createdRole1 = roleService.createOrUpdateRole(buildRandomRole(true, true));

        createdRole1.setRoleDesc(createdRole1.getRoleDesc());
        createdRole1.setRoleName(createdRole1.getRoleName());

        Role updatedRole = roleService.createOrUpdateRole(createdRole1);

        assertNotNull(updatedRole);
        assertNotNull(updatedRole.getRoleId());
        assertNotNull(updatedRole.getLastUpdateOn());
        assertNotNull(updatedRole.getCreatedOn());
        assertEquals(createdRole1.getRoleId(), updatedRole.getRoleId());
        assertEquals(createdRole1.getRoleDesc(), updatedRole.getRoleDesc());
        assertEquals(createdRole1.getRoleName(), updatedRole.getRoleName());
    }

    // Obtenir la liste des rôle de la BD sans distinction
    @Test
    void getAllRolesWithSuccess() throws ClinicException {
        Role createdRole1 = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Role createdRole2 = roleService.createOrUpdateRole(buildRandomRole(false, true));

        List<Role> roles = roleService.getAllRole();

        assertNotNull(roles);
        assertNotNull(createdRole1);
        assertNotNull(createdRole2);
        assertFalse(roles.isEmpty());
        assertNotEquals(0, roles.size());
        assertEquals(2, roles.size());
    }

    // Obtenir la liste des rôle actifs de la BD
    @Test
    void getAllActiveRolesWithSuccess() throws ClinicException {
        Role createdRole1 = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Role createdRole2 = roleService.createOrUpdateRole(buildRandomRole(false, true));

        createdRole2.setStatus(Constants.STATE_DEACTIVATED);
        roleService.createOrUpdateRole(createdRole2);

        List<Role> roles = roleService.getActiveRoles();

        assertNotNull(createdRole1);
        assertNotNull(createdRole2);
        assertNotNull(createdRole1.getRoleId());
        assertNotNull(createdRole2.getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, createdRole1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, createdRole2.getStatus());
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        assertNotEquals(0, roles.size());
        assertEquals(1, roles.size());
    }

    // Obtenir la liste des rôle actifs de la BD s'il y en a aucun
    @Test
    void getAllActiveRolesWithEmptyArrayWithSuccess() throws ClinicException {
        Role createdRole1 = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Role createdRole2 = roleService.createOrUpdateRole(buildRandomRole(false, true));

        createdRole1.setStatus(Constants.STATE_DEACTIVATED);
        createdRole2.setStatus(Constants.STATE_DEACTIVATED);
        roleService.createOrUpdateRole(createdRole1);
        roleService.createOrUpdateRole(createdRole2);

        List<Role> roles = roleService.getActiveRoles();

        assertNotNull(createdRole1);
        assertNotNull(createdRole2);
        assertEquals(Constants.STATE_DEACTIVATED, createdRole1.getStatus());
        assertEquals(Constants.STATE_DEACTIVATED, createdRole2.getStatus());
        assertNotNull(roles);
        assertTrue(roles.isEmpty());
        assertEquals(0, roles.size());
    }

    // Supprimer un rôle existant de la BD
    @Test
    void deleteExistingRoleWithSuccess() throws ClinicException {
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(true, true));

        Role deletedRole = roleService.deleteRole(createdRole.getRoleId());

        assertNotNull(deletedRole);
        assertNotNull(deletedRole.getRoleId());
        assertEquals(createdRole.getRoleId(), deletedRole.getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, createdRole.getStatus());
        assertEquals(Constants.STATE_DELETED, deletedRole.getStatus());
    }

    // Supprimer un rôle existant déjà supprimé
    @Test
    void deleteAlreadyDeletedRoleWithError() throws ClinicException {
        // création
        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(true, true));
        // suppression
        Role deletedRole = roleService.deleteRole(createdRole.getRoleId());

        // Vérifications
        assertNotNull(deletedRole);
        assertNotNull(deletedRole.getRoleId());
        assertEquals(createdRole.getRoleId(), deletedRole.getRoleId());
        assertEquals(Constants.STATE_ACTIVATED, createdRole.getStatus());
        assertEquals(Constants.STATE_DELETED, deletedRole.getStatus());
        // en tentant de supprimer un rôle déjà supprimé,
        // une rôle exception sera levée
        ClinicException e = assertThrows(ClinicException.class, () -> roleService.deleteRole(deletedRole.getRoleId()));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.ROLE_ALREADY_DELETED, e.getMessage());
    }

    // Supprimer un rôle inexistant de la BD
    @Test
    void deleteNotExistingRoleWithError() {
        ClinicException e = assertThrows(ClinicException.class, () -> roleService.deleteRole(1));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // Rechercher un rôle existant par son id
    @Test
    void findExistingRoleByIdWithSuccess() throws ClinicException {
        Role expectedRole = roleService.createOrUpdateRole(buildRandomRole(true, true));

        Role searchRole = roleService.findRoleById(expectedRole.getRoleId());

        assertNotNull(searchRole);
        assertNotNull(searchRole.getRoleId());
        assertEquals(expectedRole.getRoleId(), searchRole.getRoleId());
        assertEquals(expectedRole.getRoleDesc(), searchRole.getRoleDesc());
        assertEquals(expectedRole.getRoleName(), searchRole.getRoleName());
    }

    // Rechercher un rôle inexistant par son id
    @Test
    void findNotExistingRoleByIdWithError() {
        ClinicException e = assertThrows(ClinicException.class, () -> roleService.findRoleById(1));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.ROLE_NOT_FOUND, e.getMessage());
    }

    // Rechercher un rôle existant par son nom
    @Test
    void findExistingRoleByRoleNameWithSuccess() throws ClinicException {

        Role createdRole = roleService.createOrUpdateRole(buildRandomRole(true, true));
        Role searchRole = roleService.findByRoleName(createdRole.getRoleName());

        assertNotNull(searchRole);
        assertNotNull(searchRole.getRoleId());
        assertEquals(createdRole.getRoleId(), searchRole.getRoleId());
        assertEquals(createdRole.getRoleDesc(), searchRole.getRoleDesc());
        assertEquals(createdRole.getRoleName(), searchRole.getRoleName());
    }

    // Rechercher un rôle inexistant par son nom
    @Test
    void findNotExistingRoleByRoleNameWithSuccess() {
        // construire un rôle inexistant
        String roleName = "SUPER_ADMIN";
        Role searchRole = roleService.findByRoleName(roleName);
        assertTrue(searchRole == null);
    }
}
