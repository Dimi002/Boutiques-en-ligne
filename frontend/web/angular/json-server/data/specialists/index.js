const constant = require('../../helper/constant');
const data  = require('./json/data.json');
const data1  = require('./json/social-medial-data.json');

module.exports = {
  getSpecialists: data,
  getSocialMedias: data1,
  getAllActivatedSpecialists: getAllActivatedSpecialists,
  findByUserId: findByUserId,
  findBySpecialistId: findBySpecialistId,
};

/**
 *
 * @param {Array} users
 */
function getAllActivatedSpecialists(){
  const specialists = data.specialists
  var i =0, activatedSpecialists = [];
  for(i=0; i<specialists.length; i++ ){
    if(specialists[i].status === constant.STATE_ACTIVATED){
      activatedSpecialists.push(specialists[i])
    }
  }
  return activatedSpecialists;
}

/**
 *
 * @param {object} specialist
 * @returns
 */
 function findByUserId(specialist) {
  const specialists = data.specialists;
  var i=0;
  for(i =0; i< specialists.length; i++){
    if(specialists[i].userId.id == specialist.userId.id) {
      return specialists[i];
    }
  }
  return null;
}

/**
 *
 * @param {number} specialistId
 * @returns
 */
 function findBySpecialistId(specialistId) {
  const specialists = data.specialists;
  var i=0;
  for(i =0; i< specialists.length; i++){
    if(specialists[i].specialistId == specialistId) {
      return specialists[i];
    }
  }
  return null;
}


