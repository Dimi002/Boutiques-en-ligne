const data  = require('./json/data.json');

module.exports = {
  getContacts: data,
  isValidContact: isValidContact,
};


/**
 *
 * @param {object} contact
 * @return boolean
 */
function isValidContact(contact) {
  return contact.email != null && contact.nom != null && contact.sujet != null && contact.message != null;
}
