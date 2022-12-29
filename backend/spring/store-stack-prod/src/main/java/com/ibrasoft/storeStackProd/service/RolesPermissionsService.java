package com.ibrasoft.storeStackProd.service;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.PermissionsIds;
import com.ibrasoft.storeStackProd.response.RolePermissionMin;
import com.ibrasoft.storeStackProd.response.StateResponse;

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
