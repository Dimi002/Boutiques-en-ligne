package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Permission;
import com.dimsoft.clinicStackProd.beans.RolePermission;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.response.PermissionsIds;
import com.dimsoft.clinicStackProd.response.RolePermissionMin;
import com.dimsoft.clinicStackProd.response.StateResponse;

public interface RolesPermissionsService {
	public RolePermission createRolePermission(RolePermissionMin rolePermission) throws ClinicException;

	public RolePermission updateRolePermission(RolePermission rolePermission);

	public List<RolePermission> getAllRolesPermissions();

	public RolePermission deleteRolePermission(int roleId, int permissionId);

	public RolePermission findRolePermissionByRoleIdAndPermissionId(int roleId, int permissionId)
			throws ClinicException;

	public StateResponse assignPermissionsToRole(int roleId, PermissionsIds permissionsListWrapper)
			throws ClinicException;

	public StateResponse removePermissionsToRole(int roleId, PermissionsIds permissionsListWrapper);

	List<Permission> getAllRolePermissions(int roleId);
}
