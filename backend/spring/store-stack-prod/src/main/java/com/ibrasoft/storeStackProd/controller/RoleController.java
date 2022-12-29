package com.ibrasoft.storeStackProd.controller;

import java.util.List;

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

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.service.RoleService;
import com.ibrasoft.storeStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/roles")
public class RoleController {

	@Autowired
	private RoleService rolesService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllRole", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllRole() {
		return new ResponseEntity<>(rolesService.getAllRole(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getActiveRoles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Role>> getActivesRoles() {
		return new ResponseEntity<List<Role>>(rolesService.getActiveRoles(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createRole", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRole(@RequestBody Role role) throws ClinicException {
		if (rolesService.findByRoleName(role.getRoleName()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.ROLE_NAME_ALREADY_EXIST);
		}
		Role roleCreated = rolesService.createOrUpdateRole(role);
		return new ResponseEntity<>(roleCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateRole", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRole(@RequestBody Role role) throws ClinicException {
		Role roleByName = rolesService.findByRoleName(role.getRoleName());
		if (roleByName != null) {
			if (!roleByName.getRoleId().equals(role.getRoleId()))
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.ROLE_NAME_ALREADY_EXIST);
		}
		Role roleUpdated = rolesService.createOrUpdateRole(role);
		return new ResponseEntity<>(roleUpdated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteRole/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteRole(@PathVariable("roleId") int roleId) throws ClinicException {
		Role roleDeleted = rolesService.deleteRole(roleId);
		if (roleDeleted != null) {
			return new ResponseEntity<>(roleDeleted, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findRoleById/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findRoleById(@PathVariable("roleId") int roleId) throws ClinicException {
		Role roleFound = rolesService.findRoleById(roleId);
		return new ResponseEntity<>(roleFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllArchivedRole", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllArchivedRole() {
		return new ResponseEntity<>(rolesService.getAllArchivedRole(), HttpStatus.OK);
	}

}
