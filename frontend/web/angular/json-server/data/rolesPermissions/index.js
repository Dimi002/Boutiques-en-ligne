const data = require('./json/data.json');

const Constants = require('../../helper/constant')

module.exports = {
    getRolesPermissions: data,
    createRolePermission: createRolePermission,
    findByRolePermissionId: findByRolePermissionId,
    findsByRolePermissionId: findsByRolePermissionId,
    getAllRolePermissions: getAllRolePermissions,
    assignPermissionsToRole: assignPermissionsToRole,
    removePermissionsToRole: removePermissionsToRole
}

function createRolePermission(rolesPermission) {
    return rolesPermission
}

function findByRolePermissionId(idRolePermision, rolesPermission) {
    var i = 0;
    for (let i = 0; i < rolesPermission.length; i++) {
        if (rolesPermission[i].rolePermissionId.role.roleId == idRolePermision.rolePermissionId.role.roleId &&
            rolesPermission[i].rolePermissionId.permission.permissionId == idRolePermision.rolePermissionId.permission.permissionId
        ) {
            return rolesPermission[i]
        }
        return null
    }
    return null
}

function findsByRolePermissionId(idRole, idPermision, rolesPermission) {
    var i = 0;
    for (let i = 0; i < rolesPermission.length; i++) {
        if (rolesPermission[i].rolePermissionId.role.roleId == idRole &&
            rolesPermission[i].rolePermissionId.permission.permissionId == idPermision
        ) {
            return rolesPermission[i]
        }
        return null
    }
    return null
}

function getAllRolePermissions(idRole, rolesPermission) {
    var i = 0;
    var tabRoleP = []
    for (let i = 0; i < rolesPermission.length; i++) {
        if (rolesPermission[i].rolePermissionId.role.roleId == idRole) {
            tabRoleP.push(rolesPermission[i])
        }
    }
    return tabRoleP;
}

function assignPermissionsToRole(idRole, permission, rolesPermission) {
    var i = 0;
    for (let i = 0; i < rolesPermission.length; i++) {
        if (rolesPermission[i].rolePermissionId.role.roleId == idRole) {
            rolesPermission[i].rolePermissionId.permission = permission
            return rolesPermission;
        }
    }

}

function removePermissionsToRole(idRole, permission, rolesPermission) {
    var i = 0;
    console.log(permission);
    for (let i = 0; i < rolesPermission.length; i++) {
        if (rolesPermission[i].rolePermissionId.role.roleId == idRole) {
            rolesPermission[i].rolePermissionId.permission = null
        }
    }
    return rolesPermission;
}