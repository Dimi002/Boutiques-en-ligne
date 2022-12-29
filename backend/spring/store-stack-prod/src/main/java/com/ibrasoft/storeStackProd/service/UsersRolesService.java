package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.RolesIds;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.response.UserRoleMin;

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
