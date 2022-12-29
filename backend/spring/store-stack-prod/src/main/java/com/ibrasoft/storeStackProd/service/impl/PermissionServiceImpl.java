package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.PermissionRepository;
import com.ibrasoft.storeStackProd.service.PermissionService;
import com.ibrasoft.storeStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	PermissionRepository permRepo;

	@Override
	public Permission createPermission(Permission perm) throws ClinicException {
		Permission findPermission = this.findByPermissionName(perm.getPermissionName());
		if (findPermission == null) {
			perm.setCreatedOn(new Date());
			return permRepo.save(perm);
		} else {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.PERMISSION_NAME_ALREADY_EXIST);
		}
	}

	@Override
	public Permission updatePermission(Permission perm) throws ClinicException {
		if (perm.getPermissionId() != null) {
			if (this.findByPermissionName(perm.getPermissionName()) != null
					&& perm.getPermissionId() != this.findByPermissionName(perm.getPermissionName()).getPermissionId())
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.PERMISSION_NAME_ALREADY_EXIST);
			else if (this.findByPermissionName(perm.getPermissionName()) != null
					&& this.findByPermissionName(perm.getPermissionName()).getStatus() == Constants.STATE_DELETED) {
				throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.PERMISSION_ALREADY_DELETED);
			} else if (this.findByPermissionName(perm.getPermissionName()) != null
					&& this.findByPermissionName(perm.getPermissionName()).getStatus() == Constants.STATE_DEACTIVATED) {
				throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.PERMISSION_ALREADY_DEACTIVATED);
			} else {
				perm.setLastUpdateOn(new Date());
			}
		}
		return permRepo.save(perm);
	}

	@Override
	public List<Permission> getAllPermission() {
		List<Permission> permissionList = new ArrayList<Permission>();
		permRepo.getAllPermission(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED).forEach(permissionList::add);
		return permissionList;
	}

	@Override
	public List<Permission> getActivePermission() {
		List<Permission> permissionList = new ArrayList<Permission>();
		permRepo.getActivePermission(Constants.STATE_ACTIVATED).forEach(permissionList::add);
		return permissionList;
	}

	@Override
	public Permission deletePermission(int permId) throws ClinicException {
		Optional<Permission> permToDelete = permRepo.findById(permId);
		if (permToDelete.isPresent() && permToDelete.get().getStatus() == Constants.STATE_ACTIVATED) {
			permToDelete.get().setLastUpdateOn(new Date());
			permToDelete.get().setStatus(Constants.STATE_DELETED);
		} else if (permToDelete.isPresent() && permToDelete.get().getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.PERMISSION_ALREADY_DELETED);
		} else if (!permToDelete.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
		}
		return permRepo.save(permToDelete.get());
	}

	@Override
	public Permission findPermissionById(int permId) throws ClinicException {
		Optional<Permission> permFound = permRepo.findById(permId);
		if (permFound.isPresent()) {
			return permFound.get();
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.PERMISSION_NOT_FOUND);
		}
	}

	@Override
	public Permission findByPermissionName(String permissionName) {
		return permRepo.findByPermissionName(permissionName);
	}

	@Override
	public List<Permission> getAllArchivedPermission() {
		List<Permission> permissionList = new ArrayList<Permission>();
		permRepo.getAllArchivedPermission(Constants.STATE_ARCHIVE).forEach(permissionList::add);
		return permissionList;
	}

}
