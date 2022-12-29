package com.ibrasoft.storeStackProd.service;

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;

import java.util.List;

public interface UserService {

	List<User> findByUserContainingIgnoreCase(String term);

	public User createOrUpdateUser(User usr) throws ClinicException;

	public List<User> getAllUser();

	public User deleteUser(Integer userId) throws ClinicException;

	public User findByUserName(String userName);

	public User findByUserMAil(String userMail);

	public User findUserById(Integer userId) throws ClinicException;

	public User updateUserPassword(Integer userId, String olpPassword, String newPassword)
			throws ClinicException;

	public List<User> getAllArchivedUser();

	public User createOrUpdateAdminUser(User usr);

	public User createAdminOrSpecialist(User user, Boolean admin, Boolean specialist) throws ClinicException;

	public User updateAdminOrSpecialistByAdmin(User user) throws ClinicException;

	public Boolean updateImage(Integer userId, String profileImage);

	public Boolean updateUserStatus(User user) throws ClinicException;

	public List<Role> getAllUserRoles(int userId);

}