const data  = require('./json/data.json');
const data1  = require('./json/data-min.json');

module.exports = {
  getSpecialistSpeciality: data,
  getSpecialistSpecialityMin: data1,
  getAllSpecialistSpecialityById: getAllSpecialistSpecialityById,
  getAllSpecialitySpecialistsById: getAllSpecialitySpecialistsById,
};

/**
 *
 * @param {number} specialistId
 */
function getAllSpecialistSpecialityById(specialistId){
  const specialistsSpecialities = data.specialistSpeciality
  var i =0, specialistSpecialitiesById = [];
  for(i=0; i<specialistsSpecialities.length; i++ ){
    if(specialistsSpecialities[i].specialistId == specialistId){
      specialistSpecialitiesById.push(specialistsSpecialities[i])
    }
  }
  return specialistSpecialitiesById;
}

/**
 *
 * @param {number} specialityId
 */
function getAllSpecialitySpecialistsById(specialityId){
  const specialistsSpecialities = data.specialistSpeciality
  var i =0, specialitySpecialistsById = [];
  for(i=0; i<specialistsSpecialities.length; i++ ){
    if(specialistsSpecialities[i].specialityId == specialityId){
      specialitySpecialistsById.push(specialistsSpecialities[i])
    }
  }
  return specialitySpecialistsById;
}
