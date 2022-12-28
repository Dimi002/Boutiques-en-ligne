package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;

public interface RoleService {

	public Role createOrUpdateRole(Role role) throws ClinicException;

	public List<Role> getAllRole();

	public List<Role> getActiveRoles();

	public Role deleteRole(int roleId) throws ClinicException;

	public Role findRoleById(int roleId) throws ClinicException;

	public Role findByRoleName(String roleName);

	public List<Role> getAllArchivedRole();
}
