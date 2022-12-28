const data = require('./json/data.json');

module.exports = {
    getUsersRoles: data,
    createUserRole: createUserRole,
    findByUserRoleId: findByUserRoleId,
    findsByUserRoleId: findsByUserRoleId,
    getAllUserRoleId: getAllUserRoleId,
    assignRoleToUser: assignRoleToUser,
    removeRoleToUser: removeRoleToUser
}

function removeRoleToUser(userid, idRole, UserRole) {
    var i = 0;
    for (let i = 0; i < UserRole.length; i++) {
        if (UserRole[i].user.id == userid) {
            UserRole[i].role = null
            return UserRole;
        }
    }
}

function createUserRole(usersRole) {
    return usersRole
}

function findByUserRoleId(idusersRole, usersRole) {
    var i = 0;
    for (let i = 0; i < usersRole.length; i++) {
        if (usersRole[i].role.roleId == idusersRole.role.roleId &&
            usersRole[i].user.id == idusersRole.user.id
        ) {
            return usersRole[i]
        }
    }
    return null
}

function findsByUserRoleId(idRole, iduser, usersRole) {
    var i = 0;
    for (let i = 0; i < usersRole.length; i++) {
        if (usersRole[i].role.roleId == idRole &&
            usersRole[i].user.id == iduser
        ) {
            return usersRole[i]
        }
    }
    return null
}

function getAllUserRoleId(idRole, UserRole) {
    var i = 0;
    var tabRoleP = []
    for (let i = 0; i < UserRole.length; i++) {
        if (UserRole[i].role.roleId == idRole) {
            tabRoleP.push(UserRole[i])
        }
    }
    return tabRoleP;
}

function assignRoleToUser(userid, idRole, UserRole) {
    var i = 0;
    for (let i = 0; i < UserRole.length; i++) {
        if (UserRole[i].user.id == userid) {
            UserRole[i].role.roleId = idRole
            return UserRole;
        }
    }

}