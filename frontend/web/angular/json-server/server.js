const jsonServer = require('json-server');

const middleware = jsonServer.defaults();
const server = jsonServer.create();

const utils = require('./helper/helper.js');


server.use(middleware);
server.use(jsonServer.bodyParser);

// load constants
const Constants = require('./helper/constant')

// ressources
const Contact = require('./data/contacts');
const Specialities = require('./data/specialities');
const Users = require('./data/users');
const Permissions = require('./data/permissions');
const Planings = require('./data/planing');
const Specialists = require('./data/specialists');
const Settings = require('./data/settings');
const Apointments = require('./data/appointments');
const Roles = require('./data/role');
const RolesPermissions = require('./data/rolesPermissions');
const UsersRoles = require('./data/usersRoles');
const SpecialistSpecialities = require('./data/specialistSpeciality');



/**
 * ========================================================
 *
 *                         Contact
 *
 * ========================================================
 */

server.get('/contact/records', (req, res) => {
    if (Contact.getContacts !== null) {
        res.status(200).send(Contact.getContacts.contacts);
    } else {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.Contact_IS_NULL,
            Constants.INVALID_INPUT
        ))
    }
});

server.post("/contact/create", function(req, res) {
    if (Contact.isValidContact(req.body)) {
        const lastIndex = utils.lastIndex(Contact.getContacts.contacts)
        res.status(200).send({...req.body, id: lastIndex + 1 });
    } else {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.CONTACT_MUST_BE_NOT_NULL,
            Constants.INVALID_INPUT
        ))
    }
});

server.get('/contact/delete/:id', (req, res) => {
    const item = utils.findInList(Contact.getContacts.contacts, req.params.id)
    if (item != null) {
        res.status(200).jsonp(item)
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.CONTACT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

/**
 * ========================================================
 *
 *                         usersRole
 *
 * ========================================================
 */
server.get('/users-roles/getAllUsersRole', (req, res, next) => {
    res.status(200).send(UsersRoles.getUsersRoles.usersroles);
});

server.get('/users-roles/createUserRole', (req, res, next) => {
    if (UsersRoles.findByUserRoleId(req.body, UsersRoles.getUsersRoles.usersroles) == null) {
        res.status(200).send(UsersRoles.createUserRole(req.body));
    } else {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.USER_ROLE_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    }
});

server.get('/users-roles/deleteUserRole/:roleId/:userId', (req, res, next) => {
    if (UsersRoles.findsByUserRoleId(req.params.roleId, req.params.userId, UsersRoles.getUsersRoles.usersroles) !== null) {
        res.status(200).send(UsersRoles.findsByUserRoleId(req.params.roleId, req.params.userId, UsersRoles.getUsersRoles.usersroles));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.USER_ROLE_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/users-roles/findUserRoleById/:roleId/:userId', (req, res, next) => {
    if (UsersRoles.findsByUserRoleId(req.params.roleId, req.params.userId, UsersRoles.getUsersRoles.usersroles) !== null) {
        res.status(200).send(UsersRoles.findsByUserRoleId(req.params.roleId, req.params.userId, UsersRoles.getUsersRoles.usersroles));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.ROLE_PERMISSION_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/users-roles/assignRoleToUser/:userId', (req, res, next) => {
    res.status(200).send(UsersRoles.assignRoleToUser(req.params.userId, req.body.roleId, UsersRoles.getUsersRoles.usersroles));
});

server.get('/users-roles/removeRoleToUser/:userId', (req, res, next) => {
    res.status(200).send(UsersRoles.removeRoleToUser(req.params.userId, req.body.roleId, UsersRoles.getUsersRoles.usersroles));
});

server.get('/users-roles/getAllUserRole/:userId', (req, res, next) => {
    res.status(200).send(UsersRoles.getAllUserRoleId(req.params.userId, UsersRoles.getUsersRoles.usersroles));
});




/**
 * ========================================================
 *
 *                         RolesPermissions
 *
 * ========================================================
 */

server.get('/roles-permissions/getAllRolesPermissions', (req, res, next) => {
    res.status(200).send(RolesPermissions.getRolesPermissions.rolesPermission);
});

server.get('/roles-permissions/createRolePermission', (req, res, next) => {
    if (RolesPermissions.findByRolePermissionId(req.body, RolesPermissions.getRolesPermissions.rolesPermission) == null) {
        res.status(200).send(RolesPermissions.createRolePermission(RolesPermissions.getRolesPermissions.rolesPermission));
    } else {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.ROLE_PERMISSION_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    }
});

server.get('/roles-permissions/deleteRolePermission/:roleId/:permissionId', (req, res, next) => {
    if (RolesPermissions.findsByRolePermissionId(req.params.roleId, req.params.permissionId, RolesPermissions.getRolesPermissions.rolesPermission) !== null) {
        res.status(200).send(RolesPermissions.findsByRolePermissionId(req.params.roleId, req.params.permissionId, RolesPermissions.getRolesPermissions.rolesPermission));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.ROLE_PERMISSION_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/roles-permissions/findRolePermissionById/:roleId/:permissionId', (req, res, next) => {
    if (RolesPermissions.findsByRolePermissionId(req.params.roleId, req.params.permissionId, RolesPermissions.getRolesPermissions.rolesPermission) !== null) {
        res.status(200).send(RolesPermissions.findsByRolePermissionId(req.params.roleId, req.params.permissionId, RolesPermissions.getRolesPermissions.rolesPermission));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.ROLE_PERMISSION_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/roles-permissions/getAllRolePermissions/:roleId', (req, res, next) => {
    res.status(200).send(RolesPermissions.getAllRolePermissions(req.params.roleId, RolesPermissions.getRolesPermissions.rolesPermission));
});

server.get('/roles-permissions/assignPermissionsToRole/:roleId', (req, res, next) => {
    res.status(200).send(RolesPermissions.assignPermissionsToRole(req.params.roleId, req.body.permission, RolesPermissions.getRolesPermissions.rolesPermission));
});

server.get('/roles-permissions/removePermissionsToRole/:roleId', (req, res, next) => {
    res.status(200).send(RolesPermissions.removePermissionsToRole(req.params.roleId, req.body.permission, RolesPermissions.getRolesPermissions.rolesPermission));
});

/**
 * ========================================================
 *
 *                         Appointments
 *
 * ========================================================
 */

server.get('/appointments/getAllAppointments', (req, res, next) => {
    res.status(200).send(Apointments.getAppointments.appointments);
});

server.get('/appointments/getAllDistinctPatients', (req, res, next) => {
    res.status(200).send(Apointments.getAllDistinctPatients((Apointments.getAppointments.appointments)));
});

server.get('/appointments/getCountAllAppointment/:id', (req, res, next) => {
    res.status(200).send(Apointments.getAllDistinctPatients((Apointments.getAppointments.appointments)));
});

server.get('/appointments/getAllAppointmentSpecialist/:id', (req, res, next) => {
    if (Apointments.findBySpecialityId(Apointments.getAppointments.appointments, req.params.id)) {
        res.status(200).send(Apointments.getAllAppointmentSpecialist(Apointments.getAppointments.appointments, req.params.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/getAllTodayAppointment/:id', (req, res, next) => {
    res.status(200).send(Apointments.getAllTodayAppointment(Apointments.getAppointments.appointments, req.params.id));
});

server.get('/appointments/getAllSupTodayAppointment/:id', (req, res, next) => {
    res.status(200).send(Apointments.getAllSupTodayAppointment(Apointments.getAppointments.appointments, req.params.id));
});

server.post('/appointments/createAppointment', (req, res, next) => {
    if (req.body.appointmentDate !== null && req.body.appointmentHour !== null && req.body.patientEmail !== null && req.body.patientMessage !== null && req.body.patientName !== null && req.body.appointmentDate !== null && req.body.patientPhone !== null) {
        res.status(200).send(Apointments.createAppointment(req.body));
    } else {
        if (req.body.appointmentDate == null) {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.APPOINTMENT_DATE_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        } else if (req.body.appointmentHour == null) {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        } else if (req.body.patientEmail == null) {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.PATIENT_EMAIL_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        } else if (req.body.patientMessage == null) {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.PATIENT_MESSAGE_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        } else if (req.body.patientName == null) {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.PATIENT_NAME_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        } else {
            res.status(Constants.ITEM_IS_REQUIRED).jsonp(Constants.error(
                Constants.PATIENT_PHONE_IS_REQUIRED,
                Constants.ITEM_IS_REQUIRED
            ))
        }
    }
});

server.get('/appointments/getAllActivedAppoitment', (req, res, next) => {
    res.status(200).send(Apointments.getAllActivedAppoitment(Apointments.getAppointments.appointments));
});

server.get('/appointments/getAllArchivedAppoitment', (req, res, next) => {
    res.status(200).send(Apointments.getAllArchivedAppoitment(Apointments.getAppointments.appointments));
});

server.get('/appointments/deleteAppoitment', (req, res, next) => {
    if (Apointments.findAppointmentById(Apointments.getAppointments.appointments, req.query.id) !== null) {
        res.status(200).send(Apointments.deleteAppoitment(Apointments.getAppointments.appointments, req.query.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.APPOINTMENT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/getAllByIdAppoitment', (req, res, next) => {
    if (Apointments.getAllByIdAppoitment(Apointments.getAppointments.appointments, req.query.id) !== null) {
        res.status(200).send(Apointments.getAllByIdAppoitment(Apointments.getAppointments.appointments, req.query.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.APPOINTMENT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/getAllById', (req, res, next) => {
    if (Apointments.getAllByIdAppoitment(Apointments.getAppointments.appointments, req.query.id) !== null) {
        res.status(200).send(Apointments.getAllByIdAppoitment(Apointments.getAppointments.appointments, req.query.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.APPOINTMENT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/getByspecialistIdAppoitment', (req, res, next) => {
    if (Apointments.getByspecialistIdAppoitment(Apointments.getAppointments.appointments, req.query.id) !== null) {
        res.status(200).send(Apointments.getByspecialistIdAppoitment(Apointments.getAppointments.appointments, req.query.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.APPOINTMENT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/updateSpecialistState/:id/:state', (req, res, next) => {
    if (Apointments.findAppointmentById(Apointments.getAppointments.appointments, req.params.id) !== null) {
        res.status(200).send(Apointments.updateSpecialistState(Apointments.getAppointments.appointments, req.params.id, req.params.state));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.APPOINTMENT_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/appointments/getCountAllAppointment/:id', (req, res, next) => {
    if (Apointments.getByspecialistIdAppoitment(Apointments.getAppointments.appointments, req.params.id) !== null) {
        res.status(200).send(Apointments.getCountAllAppointment(Apointments.getAppointments.appointments, req.params.id));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

/**
 * ========================================================
 *
 *                         Role
 *
 * ========================================================
 */

server.get('/roles/getAllRole', (req, res, next) => {
    res.status(200).send(Roles.getRoles.roles);
});

server.get('/roles/getActiveRoles', (req, res, next) => {
    res.status(200).send(Roles.getActiveRoles(Roles.getRoles.roles));
});

server.post('/roles/createRole', (req, res, next) => {
    if (Roles.RoleExist(req.body, Roles.getRoles.roles) == false) {
        res.status(200).send(Roles.createRole(req.body));
    } else {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.ROLE_NAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    }
});


server.post('/roles/updateRole', (req, res, next) => {
    if (Roles.findByRoleName(req.body, Roles.getRoles.roles) !== null) {
        res.status(200).send(Roles.updateRole(req.body, Roles.getRoles.roles));
    } else {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.ROLE_NAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    }
});

server.get('/roles/deleteRole/:id', (req, res, next) => {
    if (Roles.findRoleById(req.params, Roles.getRoles.roles) !== null) {
        res.status(200).send(Roles.deleteRole(req.params, Roles.getRoles.roles));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.ROLE_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/roles/findRoleById/:id', (req, res, next) => {
    if (Roles.findRoleById(req.params, Roles.getRoles.roles) !== null) {
        res.status(200).send(Roles.findRoleById(req.params, Roles.getRoles.roles));
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.ROLE_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/roles/getAllArchivedRole', (req, res, next) => {
    res.status(200).send(Roles.getAllArchivedRole(Roles.getRoles.roles));
});

/**
 * ========================================================
 *
 *                         FILE  UPALOAD
 *
 * ========================================================
 */

server.get('/file/download', (req, res) => {
    const fileKey = req.query.fileKey
    if (fileKey) {
        const file_path = __dirname + '/clinic-images/' + fileKey
        res.sendFile(file_path);
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            "The file " + fileKey + "does not exists",
            Constants.ITEM_NOT_FOUND
        ))
    }
});

/**
 * ========================================================
 *
 *                         speciality
 *
 * ========================================================
 */


server.get('/specialities/getAllSpecialities', (req, res) => {
    if (Specialities.getSpecialities !== null) {
        res.status(200).send(Specialities.getSpecialities.specialities);
    } else {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.Contact_IS_NULL,
            Constants.INVALID_INPUT
        ))
    }
});

server.get('/specialities/findAllSpecialitiesMin', (req, res) => {
    res.status(200).send(Specialities.getSpecialitiesMin.specialities);
});

server.post("/specialities/createSpeciality", function(req, res) {
    const speciality = Specialities.findBySpecialityName(Specialities.getSpecialities.specialities, req.body.specialityName)
    if (speciality === null) {
        const lastIndex = utils.lastIndex(Specialities.getSpecialities.specialities)
        res.status(200).send({...req.body, id: lastIndex + 1 });
    } else {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.SPECIALITY_NAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    }
});

server.post("/specialities/updateSpeciality", function(req, res) {
    const speciality = Specialities.findBySpecialityName(Specialities.getSpecialities.specialities, req.body.specialityName)
    if (speciality !== null) {
        res.status(200).send(req.body);
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get("/specialities/getAllActivatedSpecialities", function(req, res) {
    const activatedSpecialities = Specialities.getAllActivatedSpecialities(Specialities.getSpecialities.specialities)
    res.status(200).send(activatedSpecialities);
});

server.get('/specialities/deleteSpeciality/:id', (req, res) => {
    const item = utils.findInList(Specialities.getSpecialities.specialities, req.params.id)
    console.log("la spe cherche est  : ", item);
    if (item !== null && item.status == Constants.STATE_ACTIVATED) {
        // suppression de l'item okay
        res.status(200).jsonp(item)
    } else if (item != null && item.status == Constants.STATE_DELETED) {
        res.status(Constants.ITEM_ALREADY_DELETED).jsonp(Constants.error(
            Constants.SPECIALITY_ALREADY_DELETED,
            Constants.ITEM_ALREADY_DELETED
        ))
    } else {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
});

server.get('/specialities/findSpeciality/:specialityId', (req, res) => {
    const item = utils.findInList(Specialities.getSpecialities.specialities, req.params.specialityId)
    res.status(200).jsonp(item)
});

/**
 * ========================================================
 *
 *                         users
 *
 * ========================================================
 */
server.get('/users/getAllUser', (req, res) => {
    res.status(200).send(Users.getUsers.users);
});

server.post("/users/createUser", function(req, res) {
    const userByMail = Users.findByUserMAil(Users.getUsers.users, req.body)
    if (userByMail !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.EMAIL_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else if (Users.findByUserName(Users.getUsers.users, req.body) !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.USERNAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        const lastIndex = utils.lastIndex(Users.getUsers.users)
        res.status(200).send({...req.body, id: lastIndex + 1 });
    }
});

server.post("/users/updateUser", function(req, res) {
    const userByMail = Users.findByUserMAil(Users.getUsers.users, req.body)
    const userByName = Users.findByUserName(Users.getUsers.users, req.body)

    if (userByMail !== null) {
        if (userByMail.id != req.body.id) {
            res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
                Constants.EMAIL_ALREADY_EXIST,
                Constants.ITEM_ALREADY_EXIST
            ))
        }
    } else if (userByName !== null) {
        if (userByMail.id != req.body.id) {
            res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
                Constants.USERNAME_ALREADY_EXIST,
                Constants.ITEM_ALREADY_EXIST
            ))
        }
    } else {
        res.status(200).send(req.body);
    }
});

server.get('/users/findUserById/:userId', (req, res) => {
    const item = utils.findInList(Users.getUsers.users, req.params.userId)
    res.status(200).jsonp(item)
});

server.get('/users/rememberUserPassword/:userMail', (req, res) => {
    const userByMail = Users.findByUserMAil1(Users.getUsers.users, req.params.userMail)
    if (userByMail === null) {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.EMAIL_IS_NOT_CORRECT,
            Constants.INVALID_INPUT
        ))
    } else {
        res.status(200).send(userByMail);
    }
});

server.get('/users/getAllArchivedUser', (req, res) => {
    res.status(200).send(Users.getAllArchivedUser(Users.getUsers.users));
});

server.post('/users/createAdminOrSpecialist', (req, res) => {
    const admin = req.query.admin
    const specialist = req.query.specialist

    const userByMail = Users.findByUserMAil(Users.getUsers.users, req.body)
    const userByName = Users.findByUserName(Users.getUsers.users, req.body)

    if (userByMail !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.EMAIL_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else if (userByName !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.USERNAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.post('/users/updateAdminOrSpecialist', (req, res) => {
    const userByMail = Users.findByUserMAil(Users.getUsers.users, req.body)
    const userByName = Users.findByUserName(Users.getUsers.users, req.body)

    if (userByMail !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.EMAIL_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else if (userByName !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.USERNAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.get('/users/updateImage/:userId', (req, res) => {
    res.status(200).send(true);
});

server.post('/users/updateUserStatus', (req, res) => {
    const item = utils.findInList(Users.getUsers.users, req.body.id)
    if (item == null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.USER_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});


/**
 * ========================================================
 *
 *                         permissions
 *
 * ========================================================
 */

server.get('/permissions/getAllPermission', (req, res) => {
    res.status(200).send(Permissions.getPermissions.permissions);
});

server.get('/permissions/getActivePermission', (req, res) => {
    res.status(200).send(Permissions.getActivePermission());
});

server.post('/permissions/createPermission', (req, res) => {
    if (Permissions.findByPermissionName(req.body.permissionName) !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.PERMISSION_NAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.post('/permissions/updatePermission', (req, res) => {
    if (Permissions.findByPermissionName(req.body.permissionName) !== null) {
        res.status(Constants.ITEM_ALREADY_DELETED).jsonp(Constants.error(
            Constants.PERMISSION_NAME_ALREADY_EXIST,
            Constants.ITEM_ALREADY_DELETED
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.get('/permissions/deletePermission/:permissionId', (req, res) => {
    const item = Permissions.findByPermissionId(Permissions.getPermissions.permissions, req.params.permissionId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.PERMISSION_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});

server.get('/permissions/findPermissionById/:permissionId', (req, res) => {
    const item = Permissions.findByPermissionId(Permissions.getPermissions.permissions, req.params.permissionId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.PERMISSION_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});

server.get('/permissions/getAllArchivedPermission', (req, res) => {
    res.status(200).send(Permissions.getAllArchivedPermission());
});

/**
 * ========================================================
 *
 *                         planings
 *
 * ========================================================
 */

server.get('/planing/:specialistID/:planDay', (req, res) => {
    const specialistID = req.params.specialistID
    const planDay = req.params.planDay
    res.status(200).send(Planings.recordBySpecialistIdAndPlanDay(specialistID, planDay));
});

server.post('/planing/create', (req, res) => {
    const planing = req.body
    if (planing === null) {
        res.status(200).send(true);
    } else {
        res.status(200).send(false);
    }
});

server.post('/planing/update', (req, res) => {
    const planing = req.body
    if (planing === null) {
        res.status(200).send(true);
    } else {
        res.status(200).send(false);
    }
});

server.delete('/planing/deleteOne', (req, res) => {
    const planing = req.body
    if (planing === null) {
        res.status(200).send(false);
    } else {
        res.status(200).send(true);
    }
});

server.delete('/planing/deleteMany', (req, res) => {
    const planing = req.body
    if (planing === null) {
        res.status(200).send(false);
    } else {
        res.status(200).send(true);
    }
});

/**
 * ========================================================
 *
 *                         specialists
 *
 * ========================================================
 */

server.get('/specialists/getAllSpecialist', (req, res) => {
    res.status(200).send(Specialists.getSpecialists.specialists);
});

server.get('/specialists/getAllActiveSpecialist', (req, res) => {
    res.status(200).send(Specialists.getAllActivatedSpecialists());
});

server.get('/specialists/getSpecialistsAllResources', (req, res) => {
    const specialist = Specialists.getSpecialists.specialists
    const specialistsRessources = specialist.map(specialist => {
        const spe = specialist
        spe["specialitiesList"] = ["Gynecologie", "Pediatrie", "Urologie"]
        return spe;
    })
    res.status(200).send(specialistsRessources);
});

server.post('/specialists/createSpecialist', (req, res) => {
    const specialistByUserId = Specialists.findByUserId(req.body)
    if (specialistByUserId !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.SPECIALIST_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.post('/specialists/updateSpecialist', (req, res) => {
    const specialistByUserId = Specialists.findByUserId(req.body)
    if (specialistByUserId !== null) {
        res.status(Constants.ITEM_ALREADY_EXIST).jsonp(Constants.error(
            Constants.SPECIALIST_ALREADY_EXIST,
            Constants.ITEM_ALREADY_EXIST
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.get('/specialists/deleteSpecialist/:specialistId', (req, res) => {
    const item = Specialists.findBySpecialistId(req.params.specialistId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});

server.get('/specialists/findSpecialistById/:specialistId', (req, res) => {
    const item = Specialists.findBySpecialistId(req.params.specialistId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});

server.post('/specialists/updateSocialMediaById/:specialistId', (req, res) => {
    const item = Specialists.findBySpecialistId(req.params.specialistId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});

server.get('/specialists/getSocialMediaById/:specialistId', (req, res) => {
    const item = Specialists.findBySpecialistId(req.params.specialistId)
    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(Specialists.getSocialMedias.social_media_datas[0]);
    }
});


/**
 * ========================================================
 *
 *                         settings
 *
 * ========================================================
 */

server.get('/setting/records', (req, res) => {
    const item = Settings.getSettings.settings[0]
    if (item === null) {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.SETTING_IS_NULL,
            Constants.INVALID_INPUT
        ))
    } else {
        res.status(200).send(item);
    }
});

server.post('/setting/createOrUpdate', (req, res) => {

    if (!Settings.settingIsValid(req.body)) {
        res.status(Constants.INVALID_INPUT).jsonp(Constants.error(
            Constants.SETTING_MUST_NOT_BE_NULL,
            Constants.INVALID_INPUT
        ))
    } else {
        res.status(200).send(req.body);
    }
});

server.delete('/setting/delete/:id', (req, res) => {
    const item = utils.findInList(Settings.getSettings.settings, req.params.id)

    if (item === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SETTING_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send(item);
    }
});



/**
 * ========================================================
 *
 *                   specialist speciality
 *
 * ========================================================
 */


server.post('/specialist-specialities/createSpecialistSpeciality', (req, res) => {
    const specialistSpecialityMin = req.body

    if (specialistSpecialityMin === null) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SETTING_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else {
        res.status(200).send({
            "createdOn": new Date(),
            "lastUpdateOn": new Date(),
            "status": Constants.STATE_ACTIVATED,
            "specialityId": specialistSpecialityMin.specialityId,
            "specialistId": specialistSpecialityMin.specialistId
        });
    }
});

server.get('/specialist-specialities/getAllSpecialistSpecialities', (req, res) => {
    res.status(200).send(SpecialistSpecialities.getSpecialistSpeciality.specialistSpeciality);
});

server.get('/specialist-specialities/getAllSpecialistSpecialityById', (req, res) => {
    res.status(200).send(SpecialistSpecialities.getAllSpecialistSpecialityById(req.query.specialityId));
});

server.get('/specialist-specialities/getAllSpecialitySpecialistsById', (req, res) => {
    res.status(200).send(SpecialistSpecialities.getAllSpecialitySpecialistsById(req.query.specialityId));
});

server.post('/specialist-specialities/updateSpecialistSpeciality', (req, res) => {
    const specialistSpecialityBySpecialist = SpecialistSpecialities.getAllSpecialistSpecialityById(req.body.specialistId)
    const specialistSpecialityBySpeciality = SpecialistSpecialities.getAllSpecialistSpecialityById(req.body.specialityId)

    if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(200).send({
            "createdOn": new Date(),
            "lastUpdateOn": new Date(),
            "status": specialistSpecialityBySpecialist[0].status,
            "specialityId": specialistSpecialityBySpecialist[0].specialityId,
            "specialistId": specialistSpecialityBySpecialist[0].specialistId
        });
    } else if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
    res.status(200).send(req.body)
});

server.get('/specialist-specialities/getAllSpecialitySpecialistsById', (req, res) => {
    res.status(200).send(SpecialistSpecialities.getAllSpecialitySpecialistsById(req.query.specialityId));
});


server.get('/specialist-specialities/deleteSpecialistSpecialityById', (req, res) => {
    const specialistId = req.query.specialistId
    const specialityId = req.query.specialityId

    const specialistSpecialityBySpecialist = SpecialistSpecialities.getAllSpecialistSpecialityById(specialistId)
    const specialistSpecialityBySpeciality = SpecialistSpecialities.getAllSpecialistSpecialityById(specialityId)


    if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(200).send({
            "createdOn": specialistSpecialityBySpecialist[0].createdOn,
            "lastUpdateOn": specialistSpecialityBySpecialist[0].lastUpdateOn,
            "status": specialistSpecialityBySpecialist[0].status,
            "specialityId": specialistSpecialityBySpecialist[0].specialityId,
            "specialistId": specialistSpecialityBySpecialist[0].specialistId
        });
    } else if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
    res.status(200).send(null)
});

server.get('/specialist-specialities/getBySpecialistIdAndSpecialityId', (req, res) => {
    const specialistId = req.query.specialistId
    const specialityId = req.query.specialityId

    const specialistSpecialityBySpecialist = SpecialistSpecialities.getAllSpecialistSpecialityById(specialistId)
    const specialistSpecialityBySpeciality = SpecialistSpecialities.getAllSpecialistSpecialityById(specialityId)


    if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(200).send({
            "createdOn": specialistSpecialityBySpecialist[0].createdOn,
            "lastUpdateOn": specialistSpecialityBySpecialist[0].lastUpdateOn,
            "status": specialistSpecialityBySpecialist[0].status,
            "specialityId": specialistSpecialityBySpecialist[0].specialityId,
            "specialistId": specialistSpecialityBySpecialist[0].specialistId
        });
    } else if (specialistSpecialityBySpecialist.length > 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length > 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    } else if (specialistSpecialityBySpecialist.length === 0 && specialistSpecialityBySpeciality.length === 0) {
        res.status(Constants.ITEM_NOT_FOUND).jsonp(Constants.error(
            Constants.SPECIALIST_AND_SPECIALITY_NOT_FOUND,
            Constants.ITEM_NOT_FOUND
        ))
    }
    res.status(200).send(null)
});

server.get('/specialist-specialities/getAllSpecialitySpecialistsById', (req, res) => {
    res.status(200).send(SpecialistSpecialities.getAllSpecialitySpecialistsById(req.query.specialityId));
});

/**
 * ========================================================
 *
 *                         SERVEUR
 *
 * ========================================================
 */

server.listen(8075, () => {
    console.log('JSON server listening on port 8075');
});