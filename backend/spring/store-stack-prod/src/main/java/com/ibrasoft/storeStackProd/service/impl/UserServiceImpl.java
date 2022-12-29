package com.ibrasoft.storeStackProd.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.service.UsersRolesService;
import com.ibrasoft.storeStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository usersRepository;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SpecialistRepository specialistRepository;
	@Autowired
	SpecialistService specialistService;
	@Autowired
	UserRoleRepository userRoleRepo;
	@Autowired
	UsersRolesService usersRolesService;

	public List<User> findByUserContainingIgnoreCase(String term) {
		return usersRepository.findByUserContainingIgnoreCase(term);
	}

	@Override
	public User createOrUpdateUser(User usr) throws ClinicException {
		if (usr.getId() != null) {
			usr.setLastUpdateOn(new Date());
			this.updateAdminOrSpecialistByAdmin(usr);
			return usr;
		}

		usr.setStatus(Constants.STATE_ACTIVATED);
		usr.setPassword(passwordEncoder.encode(usr.getPassword()));
		usr.setCreatedOn(new Date());
		User userSave = usersRepository.save(usr);

		if (userSave != null) {
			Role role = roleRepo.findByRoleName("USER");
			if (role != null) {
				UserRoleId userRoleId = new UserRoleId(userSave, role);
				UserRole userRole = new UserRole(userRoleId);
				if (userSave.getUsersRolesList() == null) {
					userSave.setUsersRolesList(new ArrayList<UserRole>());
				}
				userSave.getUsersRolesList().add(userRole);
				return userSave;
			}
			return userSave;
		}
		return null;
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		usersRepository.getAllUser(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED).forEach(users::add);
		for (User user : users) {
			user.getUserRoleList();
			user.getUserPermissionList();
		}
		return users;
	}

	@Override
	public User deleteUser(Integer userId) throws ClinicException {
		Optional<User> userToDelete = usersRepository.findById(userId);
		if (userToDelete.isPresent() && (userToDelete.get().getStatus() == Constants.STATE_ACTIVATED
				|| userToDelete.get().getStatus() == Constants.STATE_DEACTIVATED)) {
			userToDelete.get().setStatus(Constants.STATE_DELETED);
			userToDelete.get().setLastUpdateOn(new Date());
		} else if (userToDelete.isPresent() && userToDelete.get().getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.USER_ALREADY_DELETED);
		} else if (!userToDelete.isPresent()) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		}
		User userDeleted = usersRepository.save(userToDelete.get());
		Boolean exist = false;
		Specialist specialistToFound = specialistRepository.findByUserIdId(userId);
		List<Role> roles = usersRolesService.getAllUserRoles(userId);
		if (this.findUserById(userId).getStatus() == Constants.STATE_DELETED) {
			for (Role role : roles) {
				if (role.getRoleName().equals(Constants.SPECIALIST)) {
					exist = true;
				}
			}
			if (specialistToFound != null && exist) {
				specialistToFound.setStatus(Constants.STATE_DELETED);
				specialistToFound.setLastUpdateOn((new Date()));
				specialistRepository.save(specialistToFound);
			}
		}
		return userDeleted;
	}

	@Override
	public User findByUserName(String userName) {
		return usersRepository.findByUsername(userName);
	}

	@Override
	public User findByUserMAil(String userMail) {
		return usersRepository.findByEmail(userMail);
	}

	@Override
	public User findUserById(Integer userId) throws ClinicException {
		Optional<User> user = usersRepository.findById(userId);
		if (!user.isPresent())
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		user.get().getUserRoleList();
		user.get().getUserPermissionList();
		return user.get();
	}

	@Override
	public User updateUserPassword(Integer userId, String oldPassword, String newPassword)
			throws ClinicException {
		Optional<User> userToUpdate = usersRepository.findById(userId);
		if (!userToUpdate.isPresent())
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		else if (userToUpdate.get().getStatus() == Constants.STATE_ACTIVATED) {
			if (oldPassword.equals(userToUpdate.get().getClearPassword())) {
				if (newPassword.equals(userToUpdate.get().getClearPassword())) {
					userToUpdate.get().setLastUpdateOn(new Date());
				} else {
					userToUpdate.get().setPassword(passwordEncoder.encode(newPassword));
					userToUpdate.get().setLastUpdateOn(new Date());
					userToUpdate.get().setClearPassword(newPassword);
				}
			} else {
				throw new ClinicException(Constants.INVALID_INPUT, Constants.OLD_PASSWORD_NOT_MATCH);
			}
		}
		return usersRepository.save(userToUpdate.get());
	}

	@Override
	public List<User> getAllArchivedUser() {
		List<User> users = new ArrayList<User>();
		usersRepository.getAllArchivedUser(Constants.STATE_ARCHIVE).forEach(users::add);
		return users;
	}

	@Override
	public User createOrUpdateAdminUser(User usr) {
		if (usr.getId() != null) {
			usr.setLastUpdateOn(new Date());
		} else {
			usr.setStatus(Constants.STATE_ACTIVATED);
			Role role = roleRepo.findByRoleName("ADMIN");
			if (role != null) {
				UserRoleId userRoleId = new UserRoleId(usr, role);
				UserRole userRole = new UserRole(userRoleId);
				usr.getUsersRolesList().add(userRole);
			}
			usr.setPassword(passwordEncoder.encode(usr.getPassword()));
			usr.setCreatedOn(new Date());
		}
		return usersRepository.save(usr);
	}

	@Override
	public User createAdminOrSpecialist(User user, Boolean admin, Boolean specialist) throws ClinicException {
		if (this.findByUserMAil(user.getEmail()) != null && this.findByUserName(user.getUsername()) == null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.EMAIL_ALREADY_EXIST);
		} else if (this.findByUserName(user.getUsername()) != null && this.findByUserMAil(user.getEmail()) == null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_ALREADY_EXIST);
		} else if (this.findByUserName(user.getUsername()) != null && this.findByUserMAil(user.getEmail()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_AND_EMAIL_ALREADY_EXIST);
		}
		user.setStatus(Constants.STATE_ACTIVATED);
		user.setClearPassword(user.getClearPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedOn(new Date());
		User userSave = usersRepository.save(user);

		if (userSave != null) {
			Role userRoleName = roleRepo.findByRoleName(Constants.USER);
			Role adminRoleName = roleRepo.findByRoleName(Constants.ADMIN);
			Role specialistRoleName = roleRepo.findByRoleName(Constants.SPECIALIST);

			if (userRoleName != null) {
				UserRoleId userRoleId = new UserRoleId(userSave, userRoleName);
				UserRole userRole = new UserRole(userRoleId);
				if (userSave.getUsersRolesList() == null) {
					userSave.setUsersRolesList(new ArrayList<UserRole>());
				}
				userSave.getUsersRolesList().add(userRole);
			}
			if (specialistRoleName != null && specialist) {
				Specialist specialistToSave = new Specialist();
				specialistToSave.setUserId(userSave);
				specialistRepository.save(specialistToSave);

				UserRoleId userRoleId = new UserRoleId(userSave, specialistRoleName);
				UserRole userRole = new UserRole(userRoleId);
				if (userSave.getUsersRolesList() == null) {
					userSave.setUsersRolesList(new ArrayList<UserRole>());
				}
				userSave.getUsersRolesList().add(userRole);
			}
			if (adminRoleName != null && admin) {
				UserRoleId userRoleId = new UserRoleId(userSave, adminRoleName);
				UserRole userRole = new UserRole(userRoleId);
				if (userSave.getUsersRolesList() == null) {
					userSave.setUsersRolesList(new ArrayList<UserRole>());
				}
				userSave.getUsersRolesList().add(userRole);
			}
			return userSave;
		}
		return null;
	}

	@Override
	public User updateAdminOrSpecialistByAdmin(User user) throws ClinicException {
		Integer id = user.getId();
		if (this.findUserById(id) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		} else if (this.findUserById(id) != null && this.findUserById(id).getStatus() == Constants.STATE_DELETED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.USER_ALREADY_DELETED);
		} else if (this.findUserById(id) != null && this.findUserById(id).getStatus() == Constants.STATE_DEACTIVATED) {
			throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.USER_ALREADY_DEACTIVATED);
		} else {
			usersRepository.updateAdminOrSpecialistByAdmin(user.getUsername(), user.getFirstName(),
					new Date(), user.getLastName(), user.getEmail(), user.getComment(), user.getPhone(),
					user.getBirthDate(), user.getId());
		}
		return this.findUserById(user.getId());
	}

	public Boolean updateImage(Integer userId, String profileImage) {
		usersRepository.updateImage(userId, profileImage);
		return true;
	}

	@Override
	public Boolean updateUserStatus(User user) throws ClinicException {
		Boolean exist = false;

		if (user.getId() != null) {
			if (usersRepository.findById(user.getId()).isPresent()
					&& !(usersRepository.findById(user.getId()).get().getStatus() == Constants.STATE_DELETED)) {
				usersRepository.updateUserStatus(user.getId(), user.getStatus());
			} else if (usersRepository.findById(user.getId()).isPresent()
					&& (usersRepository.findById(user.getId()).get().getStatus() == Constants.STATE_DELETED)) {
				throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.USER_ALREADY_DELETED);
			} else if (!usersRepository.findById(user.getId()).isPresent()) {
				throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
			}
		}

		List<Role> newUserRoleList = this.getAllUserRoles(user.getId());
		Specialist specialistToFound = specialistRepository.findByUserIdId(user.getId());
		for (Role role : newUserRoleList) {
			if (role.getRoleName().equals(Constants.SPECIALIST)) {
				exist = true;
			}
		}

		if (specialistToFound == null && exist) {
			Specialist specialistToCreate = new Specialist();
			specialistToCreate.setUserId(user);
			specialistToCreate.setCreatedOn(new Date());
			specialistRepository.save(specialistToCreate);
		} else if (specialistToFound != null && exist
				&& user.getStatus() == Constants.STATE_ACTIVATED) {
			specialistToFound.setStatus(Constants.STATE_ACTIVATED);
			specialistToFound.setLastUpdateOn(new Date());
			specialistRepository.save(specialistToFound);
		} else if (specialistToFound != null && exist
				&& user.getStatus() == Constants.STATE_DEACTIVATED) {
			specialistToFound.setStatus(Constants.STATE_DEACTIVATED);
			specialistToFound.setLastUpdateOn(new Date());
			specialistRepository.save(specialistToFound);
		}

		return true;
	};

	@Override
	public List<Role> getAllUserRoles(int userId) {
		List<Role> rolesList = new ArrayList<Role>();
		User user = usersRepository.findById(userId).get();
		if (user != null) {
			List<UserRole> userRoleList = userRoleRepo.findByUserRoleIdUser(user);
			userRoleList.forEach(userRole -> {
				rolesList.add(userRole.getRole());
			});
		}
		return rolesList;
	}
}
