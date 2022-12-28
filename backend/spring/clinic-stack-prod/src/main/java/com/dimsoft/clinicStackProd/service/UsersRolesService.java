package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.UserRole;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.response.RolesIds;
import com.dimsoft.clinicStackProd.response.StateResponse;
import com.dimsoft.clinicStackProd.response.UserRoleMin;

public interface UsersRolesService {
	public UserRole createUserRole(UserRoleMin userRole) throws ClinicException;

	public UserRole updateUserRole(UserRole userRole);

	public List<UserRole> getAllUsersRoles();

	public UserRole deleteUserRole(int userId, int roleId) throws ClinicException;

	public UserRole findUserRoleByUserIdAndRoleId(int userId, int roleId) throws ClinicException;

	public StateResponse assignRolesToUser(int userId, RolesIds rolesListWrapper) throws ClinicException;

	public StateResponse removeRolesToUser(int userId, RolesIds rolesListWrapper);

	List<Role> getAllUserRoles(int userId);

	public boolean isAdmin(List<UserRole> userRoles);
}
