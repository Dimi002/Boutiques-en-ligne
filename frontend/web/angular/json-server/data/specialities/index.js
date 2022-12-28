const data  = require('./json/data.json');
const dataMin  = require('./json/data-min.json');

module.exports = {
  getSpecialities: data,
  getSpecialitiesMin: dataMin,
  findBySpecialityName: findBySpecialityName,
  getAllActivatedSpecialities: getAllActivatedSpecialities,
};


/**
 *
 * @param {Array} specialities
 * @param {string} specialityName
 * @return
 */
function findBySpecialityName(specialities, specialityName) {
  var i =0;
  for(i=0; i<specialities.length; i++ ){
    if(specialities[i].specialityName === specialityName){
      return specialities[i]
    }
  }
  return null;
}

/**
 *
 * @param {Array} specialities
 * @return
 */
 function getAllActivatedSpecialities(specialities) {
  var i =0, activatedSpecialities = [];
  for(i=0; i<specialities.length; i++ ){
    if(specialities[i].status == 1){
      activatedSpecialities.push(specialities[i])
    }
  }
  return activatedSpecialities;
}
