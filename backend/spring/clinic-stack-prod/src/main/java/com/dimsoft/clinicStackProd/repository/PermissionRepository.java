package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

	boolean existsByPermissionName(String permissionName);

	@Query("SELECT p FROM Permission p WHERE p.status = :activatedStatus OR p.status = :deActivatedStatus")
	List<Permission> getAllPermission(@Param("activatedStatus") short activatedStatus,
									  @Param("deActivatedStatus") short deActivatedStatus);

	@Query("SELECT p FROM Permission p WHERE p.status = :activatedStatus")
	List<Permission> getActivePermission(@Param("activatedStatus") short activatedStatus);

	Permission findByPermissionName(String permissionName);

	@Query("SELECT p FROM Permission p WHERE p.status = :archivedStatus")
	List<Permission> getAllArchivedPermission(@Param("archivedStatus") short archivedStatus);
}
