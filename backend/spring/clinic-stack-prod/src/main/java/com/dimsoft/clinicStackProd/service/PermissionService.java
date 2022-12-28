package com.dimsoft.clinicStackProd.service;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Permission;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;

public interface PermissionService {
	public Permission createPermission(Permission perm) throws ClinicException;

	public Permission updatePermission(Permission perm) throws ClinicException;

	public List<Permission> getAllPermission();

	public List<Permission> getActivePermission();

	public Permission deletePermission(int permId) throws ClinicException;

	public Permission findPermissionById(int permId) throws ClinicException;

	public Permission findByPermissionName(String permissionName);

	public List<Permission> getAllArchivedPermission();
}
