package com.dimsoft.clinicStackProd.controller;

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

import com.dimsoft.clinicStackProd.beans.Permission;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.service.PermissionService;
import com.dimsoft.clinicStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/permissions")
public class PermissionController {

	@Autowired
	private PermissionService permService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllPermission", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllPermission() {
		return new ResponseEntity<>(permService.getAllPermission(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getActivePermission", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getActivePermission() {
		return new ResponseEntity<>(permService.getActivePermission(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createPermission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPermission(@RequestBody Permission perm) throws ClinicException {
		if (permService.findByPermissionName(perm.getPermissionName()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.PERMISSION_NAME_ALREADY_EXIST);
		}
		Permission permCreated = permService.createPermission(perm);
		return new ResponseEntity<>(permCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updatePermission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePermission(@RequestBody Permission perm) throws ClinicException {
		Permission permissionByName = permService.findByPermissionName(perm.getPermissionName());
		if (permissionByName != null) {
			if (!permissionByName.getPermissionId().equals(perm.getPermissionId()))
				throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.PERMISSION_NAME_ALREADY_EXIST);
		}
		Permission permUpdated = permService.updatePermission(perm);
		return new ResponseEntity<>(permUpdated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deletePermission/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deletePermission(@PathVariable("permissionId") int permissionId) throws ClinicException {
		Permission permDeleted = permService.deletePermission(permissionId);
		if (permDeleted != null) {
			return new ResponseEntity<>(permDeleted, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
		}
	}

	/**
	 * @param permissionId
	 * @return
	 * @throws ClinicException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/findPermissionById/{permissionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findPermissionById(@PathVariable("permissionId") int permissionId) throws ClinicException {
		Permission permFound = permService.findPermissionById(permissionId);
		return new ResponseEntity<>(permFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllArchivedPermission", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllArchivedPermission() {
		return new ResponseEntity<>(permService.getAllArchivedPermission(), HttpStatus.OK);
	}
}
