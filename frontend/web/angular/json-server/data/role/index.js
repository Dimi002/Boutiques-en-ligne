const data = require('./json/data.json');

const Constants = require('../../helper/constant')

module.exports = {
    getRoles: data,
    getActiveRoles: getActiveRoles,
    createRole: createRole,
    RoleExist: RoleExist,
    updateRole: updateRole,
    findByRoleName: findByRoleName,
    deleteRole: deleteRole,
    findRoleById: findRoleById,
    getAllArchivedRole: getAllArchivedRole
};

function getAllArchivedRole(roles) {
    var i = 0;
    var Roles = []
    for (let i = 0; i < roles.length; i++) {
        if (roles[i].status === Constants.STATE_ARCHIVE) {
            Roles.push(roles[i]);
        }
    }
    return Roles
}

function deleteRole(role, listRoles) {
    return findRoleById(role, listRoles)
}

function findRoleById(role, listRoles) {
    var i = 0;
    for (let i = 0; i < listRoles.length; i++) {
        if (listRoles[i].roleId == role.id) {
            return role
        }
    }
    return null
}


function findByRoleName(role, listRoles) {
    var i = 0;
    for (let i = 0; i < listRoles.length; i++) {
        if (listRoles[i].roleName == role.roleName) {
            return role
        }
    }
    return null
}

function updateRole(role, listRoles) {
    return findByRoleName(role, listRoles)
}

function RoleExist(role, listRoles) {
    var i = 0;
    for (let i = 0; i < listRoles.length; i++) {
        if (listRoles[i].roleName == role.roleName) {
            return true
        }
    }
    return false
}

function getActiveRoles(roles) {
    var i = 0;
    var Roles = []
    for (let i = 0; i < roles.length; i++) {
        if (roles[i].status === Constants.STATE_ACTIVATED) {
            Roles.push(roles[i]);
        }
    }
    return Roles
}

function createRole(role) {
    return role;
}