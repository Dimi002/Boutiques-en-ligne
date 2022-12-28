const constant = require('../../helper/constant');
const data  = require('./json/data.json');

module.exports = {
  getPlanings: data,
  recordBySpecialistIdAndPlanDay: recordBySpecialistIdAndPlanDay,

};


/**
 *
 * @param {number} specialistId
 * @param {number} planDay
 * @returns
 */
function recordBySpecialistIdAndPlanDay(specialistId, planDay){
  const planings = data.planings;
  var recordedPlaning = [];
  var i =0;
  for(i=0; i<planings.length; i++ ){
    if(planings[i].planDay == planDay && planings[i].specialist.specialistId == specialistId){
      recordedPlaning.push(planings[i])
    }
  }
  return recordedPlaning;
}
