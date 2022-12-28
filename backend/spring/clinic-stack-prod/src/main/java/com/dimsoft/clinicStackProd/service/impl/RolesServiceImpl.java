package com.dimsoft.clinicStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.RoleRepository;
import com.dimsoft.clinicStackProd.service.RoleService;
import com.dimsoft.clinicStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolesServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepo;

	@Override
	public Role createOrUpdateRole(Role role) throws ClinicException {
		if (role.getRoleId() != null) {
			if (roleRepo.findByRoleName(role.getRoleName()) != null
					&& role.getRoleId() != roleRepo.findByRoleName(role.getRoleName()).getRoleId()) {
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.ROLE_NAME_ALREADY_EXIST);
			} else if (roleRepo.findByRoleName(role.getRoleName()) != null
					&& roleRepo.findByRoleName(role.getRoleName()).getStatus() == Constants.STATE_DELETED) {
				throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.ROLE_ALREADY_DELETED);
			} else if (roleRepo.findByRoleName(role.getRoleName()) != null
					&& roleRepo.findByRoleName(role.getRoleName()).getStatus() == Constants.STATE_DEACTIVATED) {
				throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.ROLE_ALREADY_DEACTIVATED);
			} else {
				role.setLastUpdateOn(new Date());
				return roleRepo.save(role);
			}
		} else {
			if (roleRepo.findByRoleName(role.getRoleName()) != null)
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.ROLE_NAME_ALREADY_EXIST);
			else {
				role.setLastUpdateOn(new Date());
				role.setCreatedOn(new Date());
				return roleRepo.save(role);
			}
		}
	}

	@Override
	public List<Role> getAllRole() {
		List<Role> roles = new ArrayList<Role>();
		roleRepo.getAllRole(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED).forEach(roles::add);
		return roles;
	}

	@Override
	public List<Role> getActiveRoles() {
		List<Role> roles = new ArrayList<Role>();
		roleRepo.getActiveRoles(Constants.STATE_ACTIVATED).forEach(roles::add);
		return roles;
	}

	@Override
	public Role deleteRole(int roleId) throws ClinicException {
		Optional<Role> roleToDelete = roleRepo.findById(roleId);
		if (!roleToDelete.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (roleToDelete.isPresent() && roleToDelete.get().getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.ROLE_ALREADY_DELETED);
		} else if (roleToDelete.isPresent() && roleToDelete.get().getStatus() == Constants.STATE_ACTIVATED) {
			roleToDelete.get().setLastUpdateOn(new Date());
			roleToDelete.get().setStatus(Constants.STATE_DELETED);
		}
		return roleRepo.save(roleToDelete.get());
	}

	@Override
	public Role findRoleById(int roleId) throws ClinicException {
		Optional<Role> roleFound = roleRepo.findById(roleId);
		if (!roleFound.isPresent())
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		return roleFound.get();
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleRepo.findByRoleName(roleName);
	}

	@Override
	public List<Role> getAllArchivedRole() {
		List<Role> roles = new ArrayList<Role>();
		roleRepo.getAllArchivedRole(Constants.STATE_ARCHIVE).forEach(roles::add);
		return roles;
	}

}
