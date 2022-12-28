package com.dimsoft.clinicStackProd.rbac.config;

import com.dimsoft.clinicStackProd.rbac.services.SpringUserService;
import com.dimsoft.clinicStackProd.repository.RolePermissionRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.repository.UserRoleRepository;
import com.dimsoft.clinicStackProd.service.SpecialistService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private SpringUserService springUserService;
    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RolePermissionRepository rolePermissionRepository;
    private SpecialistService specialistService;

    public SecurityConfiguration(SpringUserService springUserService,
            UserRepository userRepository, UserRoleRepository userRoleRepository,
            RolePermissionRepository rolePermissionRepository, SpecialistService specialistService) {
        this.springUserService = springUserService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.specialistService = specialistService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors()
                .and().addFilter(new JwtAuthenticationFilter(authenticationManager(), this.specialistService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository,
                        this.userRoleRepository, this.rolePermissionRepository))
                .authorizeRequests()
                // configure access rules
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                // Swagger UI route protection
                // .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                // .access("hasRole('USER') or hasRole('ADMIN') or hasRole('SPECIALIST')")

                // Users routes protection
                .antMatchers("/users/getAllUser").hasRole("GET_ALL_USERS")
                .antMatchers("/users/updateUser").hasRole("UPDATE_USER_INFOS")
                .antMatchers("/users/deleteUser/*").hasRole("DELETE_USER")
                .antMatchers("/users/findUserById/*").hasRole("FIND_USER")
                .antMatchers("/users/updateUserPassword").hasRole("UPDATE_PASSWORD")
                .antMatchers("/users/rememberUserPassword/*").permitAll()
                .antMatchers("/users/rememberUserPassword/**").permitAll()
                .antMatchers("/users/createUser").hasRole("CREATE_USER")
                .antMatchers("/users/updateAdminOrSpecialist").hasRole("UPDATE_ADMIN_OR_SPECIALIST")
                .antMatchers("/users/createAdminOrSpecialist").hasRole("CREATE_ADMIN_OR_SPECIALIST")
                .antMatchers("/users/updateImage/*").hasRole("UPDATE_IMAGE")
                .antMatchers("/users/updateUserStatus").hasRole("UPDATE_USER_STATUS")

                // Roles routes protection
                .antMatchers("/roles/getAllRole").hasRole("GET_ALL_ROLES")
                .antMatchers("/roles/getActiveRoles").hasRole("GET_ACTIVE_ROLES")
                .antMatchers("/roles/createRole").hasRole("CREATE_ROLE")
                .antMatchers("/roles/updateRole").hasAnyRole("UPDATE_ROLE_INFOS", "UPDATE_ROLE_STATUS")
                .antMatchers("/roles/deleteRole/*").hasRole("DELETE_ROLE")
                .antMatchers("/roles/findRoleById/*").hasRole("FIND_ROLE")

                // Permissions routes protection
                .antMatchers("/permissions/getAllPermission").hasRole("GET_ALL_PERMISSIONS")
                .antMatchers("/permissions/getActivePermission").hasRole("GET_ACTIVE_PERMISSIONS")
                .antMatchers("/permissions/createPermission").hasRole("CREATE_PERMISSION")
                .antMatchers("/permissions/updatePermission")
                .hasAnyRole("UPDATE_PERMISSION_INFOS", "UPDATE_PERMISSION_STATUS")
                .antMatchers("/permissions/deletePermission/*").hasRole("DELETE_PERMISSION")
                .antMatchers("/permissions/findPermissionById/*").hasRole("FIND_PERMISSIONS_BY_ID")
                .antMatchers("/permissions/getAllArchivedPermission").hasRole("GET_ALL_ARCHIVED_PERMISSIONS")

                // Users-Roles routes protection
                .antMatchers("/users-roles/getAllUsersRole").hasRole("GET_ALL_USERS_ROLES")
                .antMatchers("/users-roles/createUserRole").hasRole("CREATE_USER_ROLE")
                .antMatchers("/users-roles/deleteUserRole/**").hasRole("DELETE_USER_ROLE")
                .antMatchers("/users-roles/findUserRoleById/**").hasRole("FIND_USER_ROLE")
                .antMatchers("/users-roles/assignRoleToUser/*").hasRole("ASSIGN_ROLES_TO_USER")
                .antMatchers("/users-roles/removeRoleToUser/*").hasRole("REMOVE_ROLES_TO_USER")
                .antMatchers("/users-roles/getAllUserRole/*").hasRole("GET_ALL_USER_ROLES")

                // roles-permissions routes protection
                .antMatchers("/roles-permissions/getAllRolesPermissions").hasRole("VIEW_ROLE_PERMISSIONS")
                .antMatchers("/roles-permissions/createRolePermission").hasRole("CREATE_ROLE_PERMISSION")
                .antMatchers("/roles-permissions/deleteRolePermission/**").hasRole("DELETE_ROLE_PERMISSION")
                .antMatchers("/roles-permissions/findRolePermissionById/**").hasRole("FIND_ROLE_PERMISSION")
                .antMatchers("/roles-permissions/assignPermissionsToRole/*").hasRole("ASSIGN_PERMISSIONS_TO_ROLE")
                .antMatchers("/roles-permissions/removePermissionsToRole/*").hasRole("REMOVE_PERMISSIONS_TO_ROLE")
                .antMatchers("/roles-permissions/getAllRolePermissions/*").hasRole("GET_ALL_ROLE_PERMISSIONS")

                // Upload images route protection
                .antMatchers("/file/uploadimage").permitAll()
                .antMatchers("/file/download").permitAll()
                .antMatchers("/file/deletefile").permitAll()
                .antMatchers("/file/*").permitAll()

                // Appointment route protection
                .antMatchers("/appointments/getAllAppointment").hasRole("GET_ALL_APPOINTMENT")
                .antMatchers("/appointments/getAllAppointments").hasRole("GET_ALL_APPOINTMENTS")
                .antMatchers("/appointments/getAllDistinctPatients").hasRole("GET_ALL_DISTINCTS_PATIENTS")
                .antMatchers("/appointments/getCountAllAppointment/*").hasRole("GET_COUNT_ALL_APPOINTMENT")
                .antMatchers("/appointments/getAllAppointmentSpecialist/*").hasRole("GET_AllAPPOINTMENT_SPECIALIST")
                .antMatchers("/appointments/updateSpecialistState/**").hasRole("UPDATE_SPECIALIST_STATE")
                .antMatchers("/appointments/getAllTodayAppointment/*").hasRole("GET_ALL_TODAY_APPOINTMENT")
                .antMatchers("/appointments/getAllTodayAppointment/*").hasRole("GET_ALL_SUP_TODAY_APPOINTMENT")
                .antMatchers("/appointments/createAppointment").permitAll()
                .antMatchers("/appointments/getAllActivedAppoitment").hasRole("GET_ALL_ACTIVED_APPOINTMENT")
                .antMatchers("/appointments/getAllArchivedAppoitment").hasRole("GET_ALL_ARCHIVED_APPOINTMENT")
                .antMatchers("/appointments/deleteAppoitment").hasRole("DELETE_APPOINTMENT")
                .antMatchers("/appointments/getAllByIdAppoitment").hasRole("GET_ALL_BY_ID_APPOINTMENT")
                .antMatchers("/appointments/getAllById").hasRole("GET_ALL_BY_ID")
                .antMatchers("/appointments/getByspecialistIdAppoitment").hasRole("GET_BY_SPECIALIST_ID_APPOINTMENT")

                // Planning route protection
                .antMatchers("/planing/records").hasRole("GET_ALL_SPECIALIST_PLANNING")
                .antMatchers("/planing/create").hasRole("CREATE_PLANNING")
                .antMatchers("/planing/update").hasRole("UPDATE_PLANNING")
                .antMatchers("/planing/deleteOne/*").hasRole("DELETE_ONE_PLANNING")
                .antMatchers("/planing/deleteMany/*").hasRole("DELETE_MANY_PLANNING")

                // Settings route protection
                .antMatchers("/setting/records").permitAll()
                .antMatchers("/setting/createOrUpdate").hasRole("CREATE_OR_UPDATE_SETTING")
                .antMatchers("/setting/delete/*").hasRole("DELETE_SETTING")

                // Specialist route protection 
                .antMatchers("/specialists/getAllSpecialist").permitAll()
                .antMatchers("/specialists/getAllActiveSpecialist").permitAll()
                .antMatchers("/specialists/createSpecialist").hasRole("CREATE_SPECIALIST")
                .antMatchers("/specialists/updateSpecialist").hasRole("UPDATE_SPECIALIST")
                .antMatchers("/specialists/deleteSpecialist/*").hasRole("DELETE_SPECIALIST")
                .antMatchers("/specialists/findSpecialistById/*").permitAll()
                .antMatchers("/specialists/updateSocialMediaById/*").hasRole("UPDATE_SOCIAL_MEDIA")
                .antMatchers("/specialists/getSocialMediaById/*").hasRole("GET_SOCIAL_MEDIA")

                // specialities
                .antMatchers("/specialities/createSpeciality")
                .hasRole("CREATE_SPECIALITY")
                .antMatchers("/specialities/updateSpeciality")
                .hasRole("UPDATE_SPECIALITY")
                .antMatchers("/specialities/getAllSpecialities").permitAll()
                .antMatchers("/specialities/getAllActivatedSpecialities").permitAll()
                .antMatchers("/specialities/deleteSpeciality/*")
                .hasRole("DELETE_SPECIALITY")
                .antMatchers("/specialities/findSpeciality/*")
                .hasRole("FIND_SPECIALITY")
                .antMatchers("/specialities/findAllSpecialitiesMin").permitAll()

                // specialist-specialities route protection
                .antMatchers("/specialist-specialities/createSpecialistSpeciality")
                .hasRole("CREATE_SPECIALIST_SPECIALISTY")
                .antMatchers("/specialist-specialities/getAllSpecialistSpecialities")
                .hasRole("GET_ALL_SPECIALIST_SPECIALISTY")
                .antMatchers("/specialist-specialities/getAllSpecialistSpecialityById")
                .hasRole("GET_ALL_SPECIALITY_BY_SPECIALIST_ID")
                .antMatchers("/specialist-specialities/getAllSpecialitySpecialistsById").permitAll()
                .antMatchers("/specialist-specialities/updateSpecialistSpeciality")
                .hasRole("UPDATE_SPECIALIST_SPECIALISTY")
                .antMatchers("/specialist-specialities/deleteSpecialistSpecialityById")
                .hasRole("DELETE_SPECIALIST_SPECIALISTY_BY_ID")
                .antMatchers("/specialist-specialities/getBySpecialistIdAndSpecialityId")
                .hasRole("GET_BY_SPECIALIST_ID_AND_SPECIALISTY_ID")
                .antMatchers("/specialist-specialities/assignSpecialitiesToSpecialist")
                .hasRole("ASSIGN_SPECIALISTY_TO_SPECIALIST")
                .antMatchers("/specialist-specialities/removeSpecialityFromList")
                .hasRole("REMOVE_SPECIALISTY_FROM_LIST")
                .antMatchers("/specialist-specialities/specialityList")
                .hasRole("FIND_SPECIALISTY_BY_NAME")

                // Contact
                .antMatchers("/contact/create").permitAll()
                .antMatchers("/contact/records").hasRole("GET_ALL_CONTACTS")

                .antMatchers("/search/records/*").permitAll()
                .anyRequest().authenticated();
                // .anyRequest().permitAll();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.springUserService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
