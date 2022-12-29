package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.beans.RolePermissionId;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.PermissionRepository;
import com.ibrasoft.storeStackProd.repository.RolePermissionRepository;
import com.ibrasoft.storeStackProd.repository.RoleRepository;
import com.ibrasoft.storeStackProd.response.PermissionsIds;
import com.ibrasoft.storeStackProd.response.RolePermissionMin;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.service.RolesPermissionsService;
import com.ibrasoft.storeStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolesPermissionsServiceImpl implements RolesPermissionsService {

	@Autowired
	RolePermissionRepository rolePermissionRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	PermissionRepository permissionRepo;

	@Override
	public RolePermission createRolePermission(RolePermissionMin rolePermissionMin) throws ClinicException {
		Optional<Role> role = roleRepo.findById(rolePermissionMin.getRoleId());
		Optional<Permission> permission = permissionRepo.findById(rolePermissionMin.getPermissionId());
		RolePermission rolePermission = new RolePermission();

		if (role.isPresent() && permission.isPresent()) {
			RolePermissionId rolePermissionId = new RolePermissionId(role.get(), permission.get());
			rolePermission = new RolePermission(rolePermissionId);
			rolePermission.setCreatedOn(new Date());
		} else if (role.isPresent() && !permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
		} else if (!role.isPresent() && permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (!role.isPresent() && !permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_AND_PERMISSION_NOT_FOUND);
		}

		return rolePermissionRepo.save(rolePermission);
	}

	@Override
	public RolePermission updateRolePermission(RolePermission rolePermission) {
		rolePermission.setLastUpdateOn(new Date());
		return rolePermissionRepo.save(rolePermission);
	}

	@Override
	public List<RolePermission> getAllRolesPermissions() {
		List<RolePermission> rolePermission = new ArrayList<RolePermission>();
		rolePermissionRepo.getAllRolePermissions(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED)
				.forEach(rolePermission::add);
		return rolePermission;
	}

	@Override
	public RolePermission deleteRolePermission(int roleId, int permissionId) {
		RolePermissionId rolePermissionId = new RolePermissionId();
		Role role = roleRepo.findById(roleId).get();
		Permission permission = permissionRepo.findById(permissionId).get();
		rolePermissionId.setRole(role);
		rolePermissionId.setPermission(permission);
		RolePermission rolePermissionToDelete = rolePermissionRepo.findByRolePermissionId(rolePermissionId);
		if (rolePermissionToDelete != null) {
			rolePermissionRepo.delete(rolePermissionToDelete);
			return rolePermissionToDelete;
		}
		return null;
	}

	@Override
	public RolePermission findRolePermissionByRoleIdAndPermissionId(int roleId, int permissionId)
			throws ClinicException {
		RolePermissionId rolePermissionId = new RolePermissionId();
		Optional<Role> role = roleRepo.findById(roleId);
		Optional<Permission> permission = permissionRepo.findById(permissionId);
		RolePermission rolePermissionFound = new RolePermission();
		if (role.isPresent() && permission.isPresent()) {
			rolePermissionId.setRole(role.get());
			rolePermissionId.setPermission(permission.get());
			rolePermissionFound = rolePermissionRepo.findByRolePermissionId(rolePermissionId);
		} else if (role.isPresent() && !permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
		} else if (!role.isPresent() && permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (!role.isPresent() && !permission.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_AND_PERMISSION_NOT_FOUND);
		}

		return rolePermissionFound;
	}

	@Override
	public StateResponse assignPermissionsToRole(int roleId, PermissionsIds permissionsListWrapper)
			throws ClinicException {
		Optional<Role> role = roleRepo.findById(roleId);
		// Liste des id des permissions envoyées dépuis le client
		List<Integer> permissionIdsList = permissionsListWrapper.getPermissionsIdsList();
		// Liste des permissions envoyées dépuis le client
		List<Permission> permissionsToAssingList = new ArrayList<Permission>();
		// Liste des permissions du role
		List<Permission> rolePermissionList = new ArrayList<Permission>();
		// Liste des permissions du role qui ont encore été envoyées
		List<Permission> permissionsToMaintainList = new ArrayList<Permission>();

		if (role.isPresent() && role.get().getStatus() == Constants.STATE_ACTIVATED) {
			// Initialiser le tableau de permission envoyées dépuis le client
			permissionIdsList.forEach(permissionId -> {
				Optional<Permission> permission = permissionRepo.findById(permissionId);
				if (permission.isPresent() && permission.get().getStatus() == Constants.STATE_ACTIVATED) {
					permissionsToAssingList.add(permission.get());
				} else {
					try {
						throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
					} catch (ClinicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			// Initialiser la liste des permissions du role
			rolePermissionList = this.getAllRolePermissions(role.get().getRoleId());

			// On parcours la liste des permissions à assigner au role, si le role ne
			// possède pas la permission à assigner, on lui assigne la permission. Sinon si
			// le role possède déjà la permission à assigner, on la sauvegerde dans une
			// liste
			for (int i = 0; i < permissionsToAssingList.size(); i++) {
				if (!rolePermissionList.contains(permissionsToAssingList.get(i))) {
					RolePermissionId rolePermissionId = new RolePermissionId(role.get(),
							permissionsToAssingList.get(i));
					RolePermission rolePermission = new RolePermission(rolePermissionId);
					rolePermissionRepo.save(rolePermission);
				} else {
					permissionsToMaintainList.add(permissionsToAssingList.get(i));
				}
			}
			// On enlève toutes les permissions maintenues pour ne rester qu'avec les
			// permissions à supprimer
			rolePermissionList.removeAll(permissionsToMaintainList);

			// On supprime les permissions qui ne sont plus assignées
			rolePermissionList.forEach(permissionToDelete -> {
				RolePermissionId rolePermissionId = new RolePermissionId(role.get(), permissionToDelete);
				RolePermission rolePermission = new RolePermission(rolePermissionId);
				rolePermissionRepo.delete(rolePermission);
			});
		} else if (role.isPresent() && role.get().getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.ROLE_ALREADY_DELETED);
		} else if (role.isPresent() && role.get().getStatus() == Constants.STATE_DEACTIVATED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.ROLE_ALREADY_DEACTIVATED);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		}
		return new StateResponse("SUCCEEDED");
	}

	@Override
	public StateResponse removePermissionsToRole(int roleId, PermissionsIds permissionsListWrapper) {
		Role role = roleRepo.findById(roleId).get();
		List<Integer> permissionIdsList = permissionsListWrapper.getPermissionsIdsList();
		if (role != null) {
			permissionIdsList.forEach(permissionId -> {
				Permission permission = permissionRepo.findById(permissionId).get();
				if (permission != null) {
					RolePermissionId rolePermissionId = new RolePermissionId(role, permission);
					RolePermission rolePermission = rolePermissionRepo.findByRolePermissionId(rolePermissionId);
					if (rolePermission != null) {
						rolePermissionRepo.delete(rolePermission);
					}
				}
			});
			return new StateResponse("SUCCEEDED");
		}
		return new StateResponse("FAILED");
	}

	@Override
	public List<Permission> getAllRolePermissions(int roleId) {
		List<Permission> permissionsList = new ArrayList<Permission>();
		Role role = roleRepo.findById(roleId).get();
		if (role != null) {
			List<RolePermission> rolePermissionList = rolePermissionRepo.findByRolePermissionIdRole(role);
			rolePermissionList.forEach(rolePermission -> {
				permissionsList.add(rolePermission.getPermission());
			});
		}
		return permissionsList;
	}
}
