package com.ibrasoft.unitTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.ibrasoft.storeStackProd.service.PermissionService;
import com.ibrasoft.storeStackProd.util.Constants;

@SpringBootTest(classes = StoreStackProdApplication.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PermissionServiceImplTest {

    @Autowired
    PermissionService permissionService;

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

    // cette methode genere une chaine de caractere aleatoire
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

    // cette mÃ©thode cree une permission aleatoire
    public Permission buildRandomPermission(Boolean name, Boolean description) {
        Permission expectedPermission = new Permission();

        if (description)
            expectedPermission.setPermissionDesc("Permission de crÃ©er l'administrateur");
        else
            expectedPermission.setPermissionDesc(generatRandomString(20));
        if (name)
            expectedPermission.setPermissionName("CREATE_ADMIN");
        else
            expectedPermission.setPermissionName(generatRandomString(8));
        expectedPermission.setStatus(Constants.STATE_ACTIVATED);
        return expectedPermission;
    }

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

    // Obtenir une liste vide de permissions
    @Test
    void getPermissionWithEmptyArray() {
        List<Permission> permissions = permissionService.getAllPermission();
        Assert.assertTrue(permissions.isEmpty());
    }

    // get all Permission from DB
    @Test
    void getAllPermissionsWithSuccess() throws ClinicException {
        permissionService.createPermission(buildRandomPermission(true, true));

        List<Permission> permissions = permissionService.getAllPermission();

        assertNotNull(permissions);
        assertFalse(permissions.isEmpty());
        assertEquals(1, permissions.size());
    }

    // get all active permissions from DB
    @Test
    void getAllActivePermissionsWithSuccess() throws ClinicException {
        Permission permissionCreated1 = permissionService.createPermission(buildRandomPermission(true, true));
        permissionService.createPermission(buildRandomPermission(false, true));

        permissionCreated1.setStatus(Constants.STATE_DEACTIVATED);
        permissionService.updatePermission(permissionCreated1);

        List<Permission> permissions = permissionService.getActivePermission();

        assertNotNull(permissions);
        assertFalse(permissions.isEmpty());
        assertEquals(1, permissions.size());
    }

    // get all active permissions with emptyArray from DB
    @Test
    void getAllActivePermissionsWithEmptyArrayWithSuccess() throws ClinicException {
        Permission permissionCreated = permissionService.createPermission(buildRandomPermission(true, true));

        permissionCreated.setStatus(Constants.STATE_DEACTIVATED);
        permissionService.updatePermission(permissionCreated);

        List<Permission> permissions = permissionService.getActivePermission();

        assertNotNull(permissions);
        assertTrue(permissions.isEmpty());
        assertEquals(0, permissions.size());
    }

    // create new permission with success
    @Test
    void createPermissionWithSuccess() throws ClinicException {
        Permission expectedPermission = buildRandomPermission(true, true);
        Permission permissionCreated = permissionService.createPermission(expectedPermission);
        assertNotNull(permissionCreated);
        assertNotNull(permissionCreated.getPermissionId());
        assertNotNull(permissionCreated.getCreatedOn());
        assertEquals(expectedPermission.getPermissionDesc(), permissionCreated.getPermissionDesc());
        assertEquals(expectedPermission.getPermissionName(), permissionCreated.getPermissionName());
        assertEquals(Constants.STATE_ACTIVATED, permissionCreated.getStatus());
    }

    // creation d'une permission avec un nom existant
    @Test
    void createPermissionWithExistingPermissionNameWithError() throws ClinicException {
        permissionService.createPermission(buildRandomPermission(true, true));
        ClinicException e = assertThrows(ClinicException.class,
                () -> permissionService.createPermission(buildRandomPermission(true, true)));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.PERMISSION_NAME_ALREADY_EXIST, e.getMessage());
    }

    // mise a jour d'une permission avec une nouvelle description
    @Test
    void updatePermissionWithSuccess() throws ClinicException {
        // creation
        Permission expectedPermission = permissionService.createPermission(buildRandomPermission(true, true));
        // mise a jour de la description de la permission
        expectedPermission.setPermissionDesc("permission de creer le super administrateur");
        // appel de la fonction de mise a jour
        Permission updatedPermission = permissionService.updatePermission(expectedPermission);

        // Assertions
        assertNotNull(expectedPermission);
        assertNotNull(expectedPermission.getPermissionId());
        assertNotNull(updatedPermission);
        assertNotNull(updatedPermission.getPermissionId());
        assertNotNull(updatedPermission.getLastUpdateOn());
        assertEquals(expectedPermission.getPermissionId(), updatedPermission.getPermissionId());
        assertEquals(expectedPermission.getPermissionDesc(), updatedPermission.getPermissionDesc());
        assertEquals(expectedPermission.getPermissionName(), updatedPermission.getPermissionName());
        assertEquals(expectedPermission.getStatus(), updatedPermission.getStatus());
    }

    // mise a jour d'une permission avec un nom existant
    @Test
    void updatePermissionWithExistingPermissionNameWithError() throws ClinicException {
        // creation de 2 permissions differentes
        Permission expectedPermission1 = permissionService.createPermission(buildRandomPermission(true, true));
        Permission expectedPermission2 = permissionService.createPermission(buildRandomPermission(false, true));
        // mise a jour de la permission 2 en utilisant le nom de la permision 1
        expectedPermission2.setPermissionName(expectedPermission1.getPermissionName());

        // verifications
        assertNotNull(expectedPermission1);
        assertNotNull(expectedPermission1.getPermissionId());
        assertNotNull(expectedPermission2);
        assertNotNull(expectedPermission2.getPermissionId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> permissionService.updatePermission(expectedPermission2));
        assertEquals(Constants.ITEM_ALREADY_EXIST, e.getCode());
        assertEquals(Constants.PERMISSION_NAME_ALREADY_EXIST, e.getMessage());
    }

    // mise a our d'une permission avec l'ancienne description
    @Test
    void updatePermissionWithSameValuesWithSuccess() throws ClinicException {
        // creation de la permission
        Permission expectedPermission = permissionService.createPermission(buildRandomPermission(true, true));
        // mise a jour de la description de la permission
        expectedPermission.setPermissionDesc(expectedPermission.getPermissionDesc());
        // appel de la fonction de mise a jour
        Permission updatedPermission = permissionService.updatePermission(expectedPermission);

        // verifications
        assertNotNull(updatedPermission);
        assertNotNull(updatedPermission.getPermissionId());
        assertNotNull(updatedPermission.getLastUpdateOn());
        assertEquals(expectedPermission.getPermissionId(), updatedPermission.getPermissionId());
        assertEquals(expectedPermission.getPermissionDesc(), updatedPermission.getPermissionDesc());
        assertEquals(expectedPermission.getPermissionName(), updatedPermission.getPermissionName());
    }

    // mise a jour d'une permission supprimee
    @Test
    void updateDeletedPermissionWithError() throws ClinicException {
        // creation
        Permission expectedPermission = permissionService.createPermission(buildRandomPermission(true, true));
        // suppression
        Permission permissiondeleted = permissionService.deletePermission(expectedPermission.getPermissionId());
        // mise a jour de la description de la permission
        permissiondeleted.setPermissionDesc("permission de créer le super administrateur");

        // Assertions
        assertNotNull(expectedPermission);
        assertNotNull(expectedPermission.getPermissionId());
        assertNotNull(permissiondeleted);
        assertNotNull(permissiondeleted.getPermissionId());
        ClinicException e = assertThrows(ClinicException.class,
                () -> permissionService.updatePermission(permissiondeleted));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.PERMISSION_ALREADY_DELETED, e.getMessage());
    }

    // mise a jour d'une permission desactive
    @Test
    void updateDeactivatedPermissionWithError() throws ClinicException {
        // creation
        Permission expectedPermission = permissionService.createPermission(buildRandomPermission(true, true));
        // desactivation
        expectedPermission.setStatus(Constants.STATE_DEACTIVATED);
        Permission updatedPermission = permissionService.updatePermission(expectedPermission);
        // mise a jour de la description de la permission
        expectedPermission.setPermissionDesc("permission de créer le super administrateur");

        // Assertions
        assertNotNull(expectedPermission);
        assertNotNull(expectedPermission.getPermissionId());
        assertNotNull(updatedPermission);
        assertNotNull(updatedPermission.getPermissionId());
        assertEquals(expectedPermission.getPermissionId(), updatedPermission.getPermissionId());
        assertEquals(Constants.STATE_DEACTIVATED, updatedPermission.getStatus());
        ClinicException e = assertThrows(ClinicException.class,
                () -> permissionService.updatePermission(updatedPermission));
        assertEquals(Constants.ITEM_ALREADY_DEACTIVATED, e.getCode());
        assertEquals(Constants.PERMISSION_ALREADY_DEACTIVATED, e.getMessage());
    }

    // suppresion d'une permission existante par son id
    @Test
    void deletePermissionWithSuccess() throws ClinicException {
        // creation de la permission
        Permission permissionCreated = permissionService.createPermission(buildRandomPermission(true, true));
        // appel de la fonction de suppresion
        Permission permissionDeleted = permissionService.deletePermission(permissionCreated.getPermissionId());

        // verifications
        assertNotNull(permissionDeleted);
        assertNotNull(permissionDeleted.getPermissionId());
        assertNotNull(permissionDeleted.getStatus());
        assertEquals(permissionCreated.getPermissionId(), permissionDeleted.getPermissionId());
        assertEquals(Constants.STATE_ACTIVATED, permissionCreated.getStatus());
        assertEquals(Constants.STATE_DELETED, permissionDeleted.getStatus());
    }

    // suppresion d'une permission existante deja supprimee
    @Test
    void deleteAlreadyDeletedPermissionWithError() throws ClinicException {
        // création
        Permission permissionCreated = permissionService.createPermission(buildRandomPermission(true, true));
        // suppession
        Permission permissionDeleted = permissionService.deletePermission(permissionCreated.getPermissionId());

        // Vérifications
        assertNotNull(permissionDeleted);
        assertNotNull(permissionDeleted.getPermissionId());
        assertNotNull(permissionDeleted.getStatus());
        assertEquals(permissionCreated.getPermissionId(), permissionDeleted.getPermissionId());
        assertEquals(Constants.STATE_ACTIVATED, permissionCreated.getStatus());
        assertEquals(Constants.STATE_DELETED, permissionDeleted.getStatus());
        // on tente de supprimer une permission déjà supprimée
        // une exception sera levée
        ClinicException e = assertThrows(ClinicException.class,
                () -> permissionService.deletePermission(permissionDeleted.getPermissionId()));
        assertEquals(Constants.ITEM_ALREADY_DELETED, e.getCode());
        assertEquals(Constants.PERMISSION_ALREADY_DELETED, e.getMessage());
    }

    // suppresion d'une permission inexistante par son id
    @Test
    void deleteNotExistingPermissionWithError() {
        // construire un id qui n'existe pas
        Integer id = 1;
        ClinicException e = assertThrows(ClinicException.class, () -> permissionService.deletePermission(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.PERMISSION_NOT_FOUND, e.getMessage());
    }

    // Recherche d'une permission existante par son id
    @Test
    void findExistingPermissionByIdWithSuccess() throws ClinicException {
        Permission permissionCreated = permissionService.createPermission(buildRandomPermission(true, true));

        Permission permissionFound = permissionService.findPermissionById(permissionCreated.getPermissionId());

        assertNotNull(permissionFound);
        assertNotNull(permissionFound.getPermissionId());
        assertEquals(permissionCreated.getPermissionId(), permissionFound.getPermissionId());
        assertEquals(permissionCreated.getPermissionDesc(), permissionFound.getPermissionDesc());
        assertEquals(permissionCreated.getPermissionName(), permissionFound.getPermissionName());
        assertEquals(permissionCreated.getCreatedOn(), permissionFound.getCreatedOn());
        assertEquals(permissionCreated.getStatus(), permissionFound.getStatus());
    }

    // Recherche d'une permission inexistante par son id
    @Test
    void findNotExistingPermissionByIdWithError() {
        // construire un id qui n'existe pas
        Integer id = 1;
        ClinicException e = assertThrows(ClinicException.class, () -> permissionService.deletePermission(id));
        assertEquals(Constants.ITEM_NOT_FOUND, e.getCode());
        assertEquals(Constants.PERMISSION_NOT_FOUND, e.getMessage());
    }

    // Recherche d'une permission existante par son nom
    @Test
    void findExistingPermissionByPermissionNameWithSuccess() throws ClinicException {
        Permission permissionCreated = permissionService.createPermission(buildRandomPermission(true, true));
        Permission permissionFound = permissionService.findByPermissionName(permissionCreated.getPermissionName());

        assertNotNull(permissionFound);
        assertNotNull(permissionFound.getPermissionId());
        assertEquals(permissionCreated.getPermissionId(), permissionFound.getPermissionId());
        assertEquals(permissionCreated.getPermissionDesc(), permissionFound.getPermissionDesc());
        assertEquals(permissionCreated.getPermissionName(), permissionFound.getPermissionName());
        assertEquals(permissionCreated.getCreatedOn(), permissionFound.getCreatedOn());
        assertEquals(permissionCreated.getStatus(), permissionFound.getStatus());
    }

    // Recherche d'une permission inexistante par son nom
    @Test
    void findNotExistingPermissionByPermissionNameWithSuccess() {
        // construire un nom de permission qui n'existe pas
        String permissionName = "DELETE_USER";
        Permission permissionFound = permissionService.findByPermissionName(permissionName);

        assertNull(permissionFound);
    }

}
