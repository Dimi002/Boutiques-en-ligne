const constant = require('../../helper/constant');
const data  = require('./json/data.json');

module.exports = {
  getPermissions: data,
  getActivePermission: getActivePermission,
  findByPermissionName: findByPermissionName,
  findByPermissionId: findByPermissionId,
  getAllArchivedPermission: getAllArchivedPermission,
};


function getActivePermission(){
  var i =0, activatedPermissions = [];
  for(i=0; i<data.permissions.length; i++ ){
    if(data.permissions[i].status == constant.STATE_ACTIVATED){
      activatedPermissions.push(data.permissions[i])
    }
  }
  return activatedPermissions;
}

function getAllArchivedPermission(){
  var i =0, archivedPermissions = [];
  for(i=0; i<data.permissions.length; i++ ){
    if(data.permissions[i].status == constant.STATE_ARCHIVE){
      archivedPermissions.push(data.permissions[i])
    }
  }
  return archivedPermissions;
}

/**
 *
 * @param {string} permissionName
 */
function findByPermissionName(permissionName){
  var i =0
  for(i=0; i<data.permissions.length; i++ ){
    if(data.permissions[i].permissionName === permissionName){
      return data.permissions[i];
    }
  }
  return null;
}


/**
 *
 * @param {Array} list
 * @param {number} id
 * @returns
 */
 function findByPermissionId(list, id) {
  var i=0;
  for(i =0; i< list.length; i++){
    if(list[i].permissionId == id) {
      return list[i];
    }
  }
  return null;
}
