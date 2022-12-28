const constant = require('../../helper/constant');
const data  = require('./json/data.json');

module.exports = {
  getSettings: data,
  settingIsValid: settingIsValid,

};


/**
 *
 * @param {object} setting
 */
function settingIsValid(setting){
  return setting.email != null && setting.adresse != null && setting.tel != null  && setting.planingEndAt !=null && setting.planingStartAt != null;
}
