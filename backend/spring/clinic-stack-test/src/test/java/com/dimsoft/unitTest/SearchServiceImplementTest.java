package com.dimsoft.unitTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.dimsoft.clinicStackProd.ClinicStackProdApplication;
import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.SpecialistSpeciality;
import com.dimsoft.clinicStackProd.beans.Speciality;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.AppointmentRepository;
import com.dimsoft.clinicStackProd.repository.PermissionRepository;
import com.dimsoft.clinicStackProd.repository.RolePermissionRepository;
import com.dimsoft.clinicStackProd.repository.RoleRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistSpecialityRepository;
import com.dimsoft.clinicStackProd.repository.SpecialityRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.repository.UserRoleRepository;
import com.dimsoft.clinicStackProd.service.AppointmentService;
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.service.SearchService;
import com.dimsoft.clinicStackProd.service.SpecialistService;
import com.dimsoft.clinicStackProd.service.SpecialityService;
import com.dimsoft.clinicStackProd.service.UserService;
import com.dimsoft.clinicStackProd.util.Constants;
import com.dimsoft.clinicStackProd.util.SearchResponse;

@SpringBootTest(classes = ClinicStackProdApplication.class)
@ActiveProfiles("test")
public class SearchServiceImplementTest {

    @Autowired
    SearchService SearchService;
    @Autowired
    SpecialityService specialityService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    SpecialistService specialistService;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolePermissionRepository rolePermissionRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    SpecialistSpecialityRepository specialistSpecialityRepository;
    @Autowired
    SpecialityRepository specialityRepository;
    @Autowired
    SpecialistRepository specialistRepository;

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

    Speciality initialiseSpeciality() {
        Speciality speciality = new Speciality();
        speciality.setCreatedOn(new Date());
        speciality.setLastUpdateOn(new Date());
        speciality.setLongDescription("description");
        speciality.setSpecialistCommonName("pediatre");
        speciality.setSpecialityName("pediatrie");
        speciality.setStatus((short) 1);
        speciality.setSpecialityDesc("description");
        speciality.setSpecialityImagePath(
                "https://res.cloudinary.com/void-elsan/image/upload/v1652909149/inline-images/endocrino-diabete-%28personnalise%29.jpg");
        List<SpecialistSpeciality> specialistSpecialityList = new ArrayList<SpecialistSpeciality>();
        speciality.setSpecialistSpecialityList(specialistSpecialityList);

        return speciality;
    }

    // instancier un role aleatoire
    public Role buildRandomRole(String roleName) {
        Role expectedRole = new Role();
        expectedRole.setRoleDesc(generatRandomString(20));
        expectedRole.setRoleName(roleName);
        expectedRole.setStatus(Constants.STATE_ACTIVATED);
        return expectedRole;
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
        String defaultEmail = "dongmoalex59@gmail.com";
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

    @Test
    void searchWithSucces() throws ClinicException {
        // creation du role SPECIALIST
        Role roleSaved = roleService.createOrUpdateRole(buildRandomRole(Constants.SPECIALIST));
        // creation d'un utilisateur en lui attribuant le role de SPECIALIST
        User userSaved = userService.createAdminOrSpecialist(buildRandomUser(false, false, false), false, true);
        // recherche du specialiste associe a notre nouvel utilisateur
        Specialist specialistFound = specialistService.findByUserId(userSaved.getId());

        Speciality expectedSpeciality = initialiseSpeciality();

        Speciality specialitySaved = specialityService.createOrUpdateSpeciality(expectedSpeciality);

        assertNotNull(specialitySaved);

        List<SearchResponse> searchResponses = SearchService.search("pediatrie");

        assertEquals(1, searchResponses.size());
    }

}
