package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.beans.UserRoleId;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.repository.RoleRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.repository.UserRoleRepository;
import com.ibrasoft.storeStackProd.response.RolesIds;
import com.ibrasoft.storeStackProd.response.StateResponse;
import com.ibrasoft.storeStackProd.response.UserRoleMin;
import com.ibrasoft.storeStackProd.service.UsersRolesService;
import com.ibrasoft.storeStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsersRolesServiceImpl implements UsersRolesService {

	@Autowired
	UserRoleRepository userRoleRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SpecialistRepository specialistRepository;

	@Override
	public UserRole createUserRole(UserRoleMin userRoleMin) throws ClinicException {
		Optional<User> user = userRepo.findById(userRoleMin.getUserId());
		Optional<Role> role = roleRepo.findById(userRoleMin.getRoleId());
		UserRole userRole = new UserRole();

		if (user.isPresent() && role.isPresent()) {
			UserRoleId userRoleId = new UserRoleId(user.get(), role.get());
			userRole.setLastUpdateOn(new Date());
			userRole.setCreatedOn(new Date());
			userRole.setUserRoleId(userRoleId);
		} else if (user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (!user.isPresent() && role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		} else if (!user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_AND_ROLE_NOT_FOUND);
		}
		return userRoleRepo.save(userRole);
	}

	@Override
	public UserRole updateUserRole(UserRole userRole) {
		userRole.setLastUpdateOn(new Date());
		return userRoleRepo.save(userRole);
	}

	@Override
	public List<UserRole> getAllUsersRoles() {
		List<UserRole> userRole = new ArrayList<UserRole>();
		userRoleRepo.getAllUserRoles(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED).forEach(userRole::add);
		return userRole;
	}

	@Override
	public UserRole deleteUserRole(int userId, int roleId) throws ClinicException {
		UserRoleId userRoleId = new UserRoleId();
		Optional<User> user = userRepo.findById(userId);
		Optional<Role> role = roleRepo.findById(roleId);
		UserRole userRoleToDelete = new UserRole();

		if (role.isPresent() && user.isPresent()) {
			userRoleId.setUser(user.get());
			userRoleId.setRole(role.get());
			userRoleToDelete = userRoleRepo.findByUserRoleId(userRoleId);
			if (userRoleToDelete != null) {
				userRoleRepo.delete(userRoleToDelete);
			}
		} else if (user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (!user.isPresent() && role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		} else if (!user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_AND_ROLE_NOT_FOUND);
		}
		return userRoleToDelete;
	}

	@Override
	public UserRole findUserRoleByUserIdAndRoleId(int userId, int roleId) throws ClinicException {
		UserRoleId userRoleId = new UserRoleId();
		Optional<User> user = userRepo.findById(userId);
		Optional<Role> role = roleRepo.findById(roleId);
		UserRole userRoleFound = new UserRole();
		if (role.isPresent() && user.isPresent()) {
			userRoleId.setUser(user.get());
			userRoleId.setRole(role.get());
			userRoleFound = userRoleRepo.findByUserRoleId(userRoleId);
		} else if (user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
		} else if (!user.isPresent() && role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		} else if (!user.isPresent() && !role.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_AND_ROLE_NOT_FOUND);
		}

		return userRoleFound;
	}

	@Override
	public StateResponse assignRolesToUser(int userId, RolesIds rolesListWrapper) throws ClinicException {
		Optional<User> user = userRepo.findById(userId);
		// Liste des id des roles envoy??s d??puis le client
		List<Integer> roleIdsList = rolesListWrapper.getRolesIdsList();
		// Liste des roles envoy??es d??puis le client
		List<Role> rolesToAssingList = new ArrayList<Role>();
		// Liste des roles de l'utilisateur
		List<Role> userRoleList = new ArrayList<Role>();
		// Liste des roles de l'utilisateur qui ont encore ??t?? envoy??es
		List<Role> rolesToMaintainList = new ArrayList<Role>();
		// v??rifie l'existance d'un r??le SPECIALIST associ?? ?? un user
		Boolean exist = false;

		if (user.isPresent() && user.get().getStatus() == Constants.STATE_ACTIVATED) {
			// Initialiser le tableau de roles envoy??s d??puis le client
			roleIdsList.forEach(roleId -> {
				Optional<Role> role = roleRepo.findById(roleId);
				if (role.isPresent() && role.get().getStatus() == Constants.STATE_ACTIVATED) {
					rolesToAssingList.add(role.get());
				} else {
					try {
						throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.ROLE_NOT_FOUND);
					} catch (ClinicException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			// Initialiser la liste des roles de l'utilisateur
			userRoleList = this.getAllUserRoles(user.get().getId());

			// On parcours la liste des r??les ?? assigner ?? l'utilisateur, si l'utilisateur
			// ne poss??de pas le r??le ?? assigner, on lui assigne le r??le. Sinon si
			// l'utilisateur poss??de d??j?? le r??le ?? assigner, on le sauvegerde dans une
			// liste
			for (int i = 0; i < rolesToAssingList.size(); i++) {
				if (!userRoleList.contains(rolesToAssingList.get(i))) {
					UserRoleId userRoleId = new UserRoleId(user.get(), rolesToAssingList.get(i));
					UserRole userRole = new UserRole(userRoleId);
					userRoleRepo.save(userRole);
				} else {
					rolesToMaintainList.add(rolesToAssingList.get(i));
				}
			}
			// On enl??ve tout le r??les maintenus pour ne rester qu'avec les roles ??
			// supprimer
			userRoleList.removeAll(rolesToMaintainList);

			// On supprime les roles qui ne sont plus assign??s
			userRoleList.forEach(roleToDelete -> {
				UserRoleId userRoleId = new UserRoleId(user.get(), roleToDelete);
				UserRole userRole = new UserRole(userRoleId);
				userRoleRepo.delete(userRole);
			});

			// on v??rifie si l'utlisateur poss??de le r??le SPECIALIST et un sp??cialiste
			// associ??, sinon on le cr??e
			List<Role> newUserRoleList = this.getAllUserRoles(user.get().getId());
			Specialist specialistToFound = specialistRepository.findByUserIdId(userId);
			for (Role role : newUserRoleList) {
				if (role.getRoleName().equals(Constants.SPECIALIST)) {
					exist = true;
				}
			}

			if (specialistToFound == null && exist) {
				Specialist specialistToCreate = new Specialist();
				specialistToCreate.setUserId(user.get());
				specialistToCreate.setCreatedOn(new Date());
				specialistRepository.save(specialistToCreate);
			} else if (specialistToFound != null && !exist
					&& specialistToFound.getStatus() == Constants.STATE_ACTIVATED) {
				specialistToFound.setStatus(Constants.STATE_DELETED);
				specialistToFound.setLastUpdateOn(new Date());
				specialistRepository.save(specialistToFound);
			} else if (specialistToFound != null && exist
					&& specialistToFound.getStatus() == Constants.STATE_ACTIVATED) {
				specialistToFound.setStatus(Constants.STATE_ACTIVATED);
				specialistToFound.setLastUpdateOn(new Date());
				specialistRepository.save(specialistToFound);
			} else if (specialistToFound != null && exist && specialistToFound.getStatus() == Constants.STATE_DELETED) {
				specialistToFound.setStatus(Constants.STATE_ACTIVATED);
				specialistRepository.save(specialistToFound);
			}
		} else if (user.isPresent() && user.get().getStatus() == Constants.STATE_DEACTIVATED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.USER_ALREADY_DEACTIVATED);
		} else if (user.isPresent() && user.get().getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.USER_ALREADY_DELETED);
		} else if (!user.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		}
		return new StateResponse("SUCCEEDED");
	}

	@Override
	public StateResponse removeRolesToUser(int userId, RolesIds rolesListWrapper) {
		User user = userRepo.findById(userId).get();
		List<Integer> roleIdsList = rolesListWrapper.getRolesIdsList();
		if (user != null) {
			roleIdsList.forEach(roleId -> {
				Role role = roleRepo.findById(roleId).get();
				if (role != null) {
					UserRoleId userRoleId = new UserRoleId(user, role);
					UserRole userRole = userRoleRepo.findByUserRoleId(userRoleId);
					if (userRole != null) {
						userRoleRepo.delete(userRole);
					}
				}
			});
			return new StateResponse("SUCCEEDED");
		}
		return new StateResponse("FAILED");
	}

	@Override
	public List<Role> getAllUserRoles(int userId) {
		List<Role> rolesList = new ArrayList<Role>();
		User user = userRepo.findById(userId).get();
		if (user != null) {
			List<UserRole> userRoleList = userRoleRepo.findByUserRoleIdUser(user);
			userRoleList.forEach(userRole -> {
				rolesList.add(userRole.getRole());
			});
		}
		return rolesList;
	}

	public boolean isAdmin(List<UserRole> userRoles) {
		boolean isAdmin = false;
		for (UserRole usr : userRoles) {
			isAdmin = usr.getRole().getRoleName() == "ADMIN";
		}
		return isAdmin;
	}

}
