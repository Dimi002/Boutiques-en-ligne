package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;

public interface RoleService {

	public Role createOrUpdateRole(Role role) throws ClinicException;

	public List<Role> getAllRole();

	public List<Role> getActiveRoles();

	public Role deleteRole(int roleId) throws ClinicException;

	public Role findRoleById(int roleId) throws ClinicException;

	public Role findByRoleName(String roleName);

	public List<Role> getAllArchivedRole();
}
