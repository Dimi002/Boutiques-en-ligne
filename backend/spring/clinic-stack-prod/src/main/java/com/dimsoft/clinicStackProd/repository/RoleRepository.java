package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("SELECT r FROM Role r WHERE r.status = :activatedStatus OR r.status = :deActivatedStatus")
	List<Role> getAllRole(@Param("activatedStatus") short roleActivatedStatus,
			@Param("deActivatedStatus") short roleDeActivatedStatus);

	@Query("SELECT r FROM Role r WHERE r.status = :activatedStatus")
	List<Role> getActiveRoles(@Param("activatedStatus") short roleActivatedStatus);

	Role findByRoleName(String roleName);

	@Query("SELECT r FROM Role r WHERE r.status = :archivedStatus")
	List<Role> getAllArchivedRole(@Param("archivedStatus") short archivedStatus);
}
