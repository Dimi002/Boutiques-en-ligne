package com.dimsoft.clinicStackProd.repository;

import java.util.List;

import com.dimsoft.clinicStackProd.beans.Permission;
import com.dimsoft.clinicStackProd.beans.Role;
import com.dimsoft.clinicStackProd.beans.RolePermission;
import com.dimsoft.clinicStackProd.beans.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

	@Query("SELECT ur FROM RolePermission ur WHERE ur.status = :activatedStatus OR ur.status = :deActivatedStatus")
	List<RolePermission> getAllRolePermissions(@Param("activatedStatus") short roleActivatedStatus,
											   @Param("deActivatedStatus") short roleDeActivatedStatus);

	RolePermission findByRolePermissionId(RolePermissionId rolePermissionId);

	List<RolePermission> findByRolePermissionIdRole(Role role);
}
