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

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.response.RolesIds;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.response.UserRoleMin;
import com.ibrasoft.storeStackProd.service.UsersRolesService;
import com.ibrasoft.storeStackProd.util.Constants;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/users-roles")
public class UsersRolesController {

	@Autowired
	private UsersRolesService usersRoleService;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllUsersRole", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUsersRole() {
		return new ResponseEntity<>(usersRoleService.getAllUsersRoles(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createUserRole", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUserRole(@RequestBody UserRoleMin userRole) throws ClinicException {
		if (usersRoleService.findUserRoleByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USER_ROLE_ALREADY_EXIST);
		}
		UserRole userRoleCreated = usersRoleService.createUserRole(userRole);
		return new ResponseEntity<>(userRoleCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteUserRole/{userId}/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteRole(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId)
			throws ClinicException {
		UserRole userRoleDeleted = usersRoleService.deleteUserRole(userId, roleId);
		if (userRoleDeleted != null) {
			return new ResponseEntity<>(userRoleDeleted, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.USER_ROLE_NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findUserRoleById/{userId}/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findUserRoleById(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId)
			throws ClinicException {
		UserRole userRoleFound = usersRoleService.findUserRoleByUserIdAndRoleId(userId, roleId);
		return new ResponseEntity<>(userRoleFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/assignRoleToUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> assignRoleToUser(@RequestBody RolesIds rolesListWrapper,
			@PathVariable("userId") int userId) throws ClinicException {
		StateResponse state = usersRoleService.assignRolesToUser(userId, rolesListWrapper);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/removeRoleToUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removePermissionsToRole(@PathVariable("userId") int userId,
			@RequestBody RolesIds rolesListWrapper) {
		StateResponse state = usersRoleService.removeRolesToUser(userId, rolesListWrapper);
		return new ResponseEntity<>(state, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllUserRole/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUserRole(@PathVariable("userId") int userId) {
		List<Role> rolesList = usersRoleService.getAllUserRoles(userId);
		return new ResponseEntity<>(rolesList, HttpStatus.OK);
	}
}
