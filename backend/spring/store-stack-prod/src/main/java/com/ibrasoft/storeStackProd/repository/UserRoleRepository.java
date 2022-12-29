package com.ibrasoft.storeStackProd.repository;

import java.util.List;

import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.beans.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

	@Query("SELECT ur FROM UserRole ur WHERE ur.status = :activatedStatus OR ur.status = :deActivatedStatus")
	List<UserRole> getAllUserRoles(@Param("activatedStatus") short roleActivatedStatus,
								   @Param("deActivatedStatus") short roleDeActivatedStatus);

	UserRole findByUserRoleId(UserRoleId userRoleId);

	List<UserRole> findByUserRoleIdUser(User user);

	UserRole findByUserRoleIdUserAndUserRoleIdRole(User user, Role role);
}
