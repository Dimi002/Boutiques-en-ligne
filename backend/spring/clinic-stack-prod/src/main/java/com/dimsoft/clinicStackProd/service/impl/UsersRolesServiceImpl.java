package com.dimsoft.clinicStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.beans.UserRole;
import com.dimsoft.clinicStackProd.beans.UserRoleId;
import com.dimsoft.clinicStackProd.exceptions.ClinicException;
import com.dimsoft.clinicStackProd.repository.RoleRepository;
import com.dimsoft.clinicStackProd.repository.SpecialistRepository;
import com.dimsoft.clinicStackProd.repository.UserRepository;
import com.dimsoft.clinicStackProd.repository.UserRoleRepository;
import com.dimsoft.clinicStackProd.response.RolesIds;
import com.dimsoft.clinicStackProd.response.StateResponse;
import com.dimsoft.clinicStackProd.response.UserRoleMin;
import com.dimsoft.clinicStackProd.service.UsersRolesService;
import com.dimsoft.clinicStackProd.util.Constants;
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
		// Liste des id des roles envoyés dépuis le client
		List<Integer> roleIdsList = rolesListWrapper.getRolesIdsList();
		// Liste des roles envoyées dépuis le client
		List<Role> rolesToAssingList = new ArrayList<Role>();
		// Liste des roles de l'utilisateur
		List<Role> userRoleList = new ArrayList<Role>();
		// Liste des roles de l'utilisateur qui ont encore été envoyées
		List<Role> rolesToMaintainList = new ArrayList<Role>();
		// vérifie l'existance d'un rôle SPECIALIST associé à un user
		Boolean exist = false;

		if (user.isPresent() && user.get().getStatus() == Constants.STATE_ACTIVATED) {
			// Initialiser le tableau de roles envoyés dépuis le client
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

			// On parcours la liste des rôles à assigner à l'utilisateur, si l'utilisateur
			// ne possède pas le rôle à assigner, on lui assigne le rôle. Sinon si
			// l'utilisateur possède déjà le rôle à assigner, on le sauvegerde dans une
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
			// On enlève tout le rôles maintenus pour ne rester qu'avec les roles à
			// supprimer
			userRoleList.removeAll(rolesToMaintainList);

			// On supprime les roles qui ne sont plus assignés
			userRoleList.forEach(roleToDelete -> {
				UserRoleId userRoleId = new UserRoleId(user.get(), roleToDelete);
				UserRole userRole = new UserRole(userRoleId);
				userRoleRepo.delete(userRole);
			});

			// on vérifie si l'utlisateur possède le rôle SPECIALIST et un spécialiste
			// associé, sinon on le crée
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
