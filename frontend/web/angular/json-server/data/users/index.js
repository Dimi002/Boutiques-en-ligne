const constant = require('../../helper/constant');
const data  = require('./json/data.json');

module.exports = {
  getUsers: data,
  findByUserMAil: findByUserMAil,
  findByUserName: findByUserName,
  findByUserMAil1: findByUserMAil1,
  getAllArchivedUser: getAllArchivedUser,
};


/**
 *
 * @param {Array} users
 * @param {bject} user
 * @returns
 */
function findByUserMAil(users, user){
  var i =0;
  for(i=0; i<users.length; i++ ){
    if(users[i].email === user.email){
      return users[i]
    }
  }
  return null;
}

/**
 *
 * @param {Array} users
 * @param {string} user
 * @returns
 */
 function findByUserMAil1(users, userEmail){
  var i =0;
  for(i=0; i<users.length; i++ ){
    if(users[i].email === userEmail){
      return users[i]
    }
  }
  return null;
}

/**
 *
 * @param {Array} users
 * @param {bject} user
 * @returns
 */
 function findByUserName(users, user){
  var i =0;
  for(i=0; i<users.length; i++ ){
    if(users[i].username === user.username){
      return users[i]
    }
  }
  return null;
}

/**
 *
 * @param {Array} users
 */
function getAllArchivedUser(users){
  var i =0, archivedUsers = [];
  for(i=0; i<users.length; i++ ){
    if(users[i].status === constant.STATE_ARCHIVE){
      archivedUsers.push(users[i])
    }
  }
  return archivedUsers;
}
