package com.ibrasoft.storeStackProd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.PermissionsIds;
import com.ibrasoft.storeStackProd.response.RolePermissionMin;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.service.RolesPermissionsService;
import com.ibrasoft.storeStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/roles-permissions")
public class RolesPermissionsController {

	@Autowired
	private RolesPermissionsService rolesPermissionsService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllRolesPermissions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllRolesPermissions() {
		return new ResponseEntity<>(rolesPermissionsService.getAllRolesPermissions(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createRolePermission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRolePermission(@RequestBody RolePermissionMin rolePermission)
			throws ClinicException {
		if (rolesPermissionsService.findRolePermissionByRoleIdAndPermissionId(rolePermission.getRoleId(),
				rolePermission.getPermissionId()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.ROLE_PERMISSION_ALREADY_EXIST);
		}
		RolePermission rolePermissionCreated = rolesPermissionsService.createRolePermission(rolePermission);
		return new ResponseEntity<>(rolePermissionCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteRolePermission/{roleId}/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteRole(@PathVariable("roleId") int roleId,
			@PathVariable("permissionId") int permissionId) throws ClinicException {
		RolePermission rolePermissionDeleted = rolesPermissionsService.deleteRolePermission(roleId, permissionId);
		if (rolePermissionDeleted != null) {
			return new ResponseEntity<>(rolePermissionDeleted, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_PERMISSION_NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findRolePermissionById/{roleId}/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findRoleById(@PathVariable("roleId") int roleId,
			@PathVariable("permissionId") int permissionId) throws ClinicException {
		RolePermission rolePermissionFound = rolesPermissionsService.findRolePermissionByRoleIdAndPermissionId(roleId,
				permissionId);
		return new ResponseEntity<>(rolePermissionFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/assignPermissionsToRole/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> assignPermissionsToRole(@RequestBody PermissionsIds permissionsListWrapper,
			@PathVariable("roleId") int roleId) throws ClinicException {
		StateResponse state = rolesPermissionsService.assignPermissionsToRole(roleId, permissionsListWrapper);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/removePermissionsToRole/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removePermissionsToRole(@PathVariable("roleId") int roleId,
			@RequestBody PermissionsIds permissionsListWrapper) {
		StateResponse state = rolesPermissionsService.removePermissionsToRole(roleId, permissionsListWrapper);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllRolePermissions/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllRolePermissions(@PathVariable("roleId") int roleId) {
		List<Permission> permissionsList = rolesPermissionsService.getAllRolePermissions(roleId);
		return new ResponseEntity<>(permissionsList, HttpStatus.OK);
	}
}
