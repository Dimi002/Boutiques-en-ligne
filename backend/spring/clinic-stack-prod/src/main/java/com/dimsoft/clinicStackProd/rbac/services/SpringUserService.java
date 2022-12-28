package com.dimsoft.clinicStackProd.rbac.services;

import java.util.List;

import javax.transaction.Transactional;

import com.dimsoft.clinicStackProd.beans.RolePermission;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.beans.UserRole;
import com.dimsoft.clinicStackProd.rbac.models.SpringUser;
import com.dimsoft.clinicStackProd.repository.RolePermissionRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

@Service
public class SpringUserService implements UserDetailsService {
    private UserRepository userDataRepository;
    private UserRoleRepository userRoleRepository;
    private RolePermissionRepository rolePermissionRepository;

    public SpringUserService(UserRepository userDataRepository, UserRoleRepository userRoleRepository,
            RolePermissionRepository rolePermissionRepository) {
        this.userDataRepository = userDataRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        if (EmailValidator.getInstance().isValid(login)) {
            user = this.userDataRepository.findByEmail(login);
        }
        if (user == null)
            user = this.userDataRepository.findByUsername(login);
        if (user != null) {
            List<UserRole> userRoleList = userRoleRepository.findByUserRoleIdUser(user);
            user.setUsersRolesList(userRoleList);
            userRoleList.forEach(userRole -> {
                List<RolePermission> rolePermissionList = rolePermissionRepository
                        .findByRolePermissionIdRole(userRole.getRole());
                userRole.getRole().setRolePermissionList(rolePermissionList);
            });
            SpringUser userPrincipal = new SpringUser(user);
            return userPrincipal;
        }
        throw new UsernameNotFoundException("The user with the specific login those not exist");
    }
}
