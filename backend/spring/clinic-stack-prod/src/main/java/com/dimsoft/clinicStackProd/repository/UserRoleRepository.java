package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.User;
import com.dimsoft.clinicStackProd.beans.UserRole;
import com.dimsoft.clinicStackProd.beans.UserRoleId;
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
