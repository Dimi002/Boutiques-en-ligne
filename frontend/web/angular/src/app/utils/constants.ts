import { State } from "../additional-models/State"
/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */

export const STATE: State = {
  STATE_ACTIVATED: 0,
  STATE_DELETED: 2,
  STATE_ARCHIVE: 1,
  STATE_DEACTIVATED: 3
}

export const ROLES = {
  SUPER_ADMIN_ROLE: 'SUPER_ADMIN',
  ADMIN_ROLE: 'ADMIN',
  USER_ROLE: 'USER',
  MANAGER_ROLE: 'MANAGER'
}

export const websiteAddresses: { addresses: string[], phones: string[], emails: string[] } = {
  addresses: ['199 Rue FOUCAULD AKWA, BP: 293 Douala, Cameroun'],
  phones: ['237 233 431 101', '237 696 090 549'],
  emails: ['cnga@.gmail.com', 'arleonzemtsop@dimsoft.eu']
}


export const EXCEPTION = {
  USER_NOT_FOUND: "User not found",
  SPECIALIST_NOT_FOUND: "Specialist not found",
  APPOINTMET_NOT_FOUND: "Appointment not found",
  CONTACT_NOT_FOUND: "Contact not found",
  PERMISSION_NOT_FOUND: "Permission not found",
  PLANNING_NOT_FOUND: "Planning not found",
  ROLE_NOT_FOUND: "Role not found",
  SETTING_NOT_FOUND: "Setting not found",
  SPECIALITY_NOT_FOUND: "Speciality not found",
  SPECIALIST_AND_SPECIALITY_NOT_FOUND: "Speciality and Speciality not found",
  USER_AND_ROLE_NOT_FOUND: "User and role not found",
  ROLE_AND_PERMISSION_NOT_FOUND: "role and permission not found",
  ROLE_PERMISSION_NOT_FOUND: "Role-permission not found",
  SPECIALIST_SPECIALITY_NOT_FOUND: "Speciality-speciality not found",
  USER_ROLE_NOT_FOUND: "User-role not found",

  USERNAME_ALREADY_EXIST: "Username already exist",
  EMAIL_ALREADY_EXIST: "Email already exist",
  EMAIL_IS_NOT_CORRECT: "Email is not correct",
  SPECIALITY_NAME_ALREADY_EXIST: "Speciality name already exist",
  ROLE_NAME_ALREADY_EXIST: "Role name already exist",
  PERMISSION_NAME_ALREADY_EXIST: "Permission name already exist",
  SPECIALIST_ALREADY_EXIST: "Specialist already exist",

  USER_ALREADY_DELETED: "User already deleted",
  SPECIALIST_ALREADY_DELETED: "Specialist already deleted",
  APPOINTMET_ALREADY_DELETED: "Appointment already deleted",
  CONTACT_ALREADY_DELETED: "Contact already deleted",
  PERMISSION_ALREADY_DELETED: "Permission already deleted",
  PLANNING_ALREADY_DELETED: "Planning already deleted",
  ROLE_ALREADY_DELETED: "Role already deleted",
  SETTING_ALREADY_DELETED: "Setting already deleted",
  SPECIALITY_ALREADY_DELETED: "Speciality already deleted",

  USER_ALREADY_DEACTIVATED: "User already deactivated",
  SPECIALIST_ALREADY_DEACTIVATED: "Specialist already deactivated",
  ROLE_ALREADY_DEACTIVATED: "Role already deactivated",
  PERMISSION_ALREADY_DEACTIVATED: "Permission already deactivated",

  PATIENT_NAME_IS_REQUIRED: "Patient name is required",
  PATIENT_EMAIL_IS_REQUIRED: "Patient email is required",
  PATIENT_PHONE_IS_REQUIRED: "Patient phone is required",

  TIME_NOT_FREE: "This slot time is not free",
  INVALID_INPUT_STRING: "Bad request",
  COULD_NOT_READ_FILE: "Could not read file",
  COULD_NOT_DELETE_FILE: "Could not process the deletion",
  COULD_NOT_DOWNLOAD_FILE: "Could not download file",
  COULD_NOT_SEND_MAIL: "Could not download file",

  NOT_ACTIVE_USERS: "There is not active user",
  NOT_ACTIVE_SPECIALIST: "There is not active specialist",
  SETTING_MUST_NOT_BE_NULL: "The setting must not be null or empty",
  SETTING_IS_NULL: "The setting is null",
  CONTACT_MUST_BE_NOT_NULL: "The contact list must not be null or empty",
  CONTACT_IS_NULL: "The contact list is null",
  OLD_PASSWORD_NOT_MATCH: "Old password do not match",

  ROLE_PERMISSION_ALREADY_EXIST: "role-permission already exist",
  USER_ROLE_ALREADY_EXIST: "User-role already exist",
  SPECIALIST_SPECIALITY_ALREADY_EXIST: "specialist-speciality already exist",

  RUN_TIME_EXCEPTION: "Malformed URL has occurred",
  USER_IS_NULL: "Value of user is null",
  SPECIALIST_IS_NULL: "Value of specialist is null",
  SPECIALITY_IS_NULL: "Value of speciality is null",
  ROLE_IS_NULL: "Value of role is null",
  PERMISSION_IS_NULL: "Value of permission is null",
  USERNAME_AND_EMAIL_ALREADY_EXIST: "Username and email already exist",

  NO_INTERNET_CONNECTION: "An unknown error has occurred please check your internet connection",
  PERMISSION_DESACTIVED_SUCCES: "Permission successfully deactivated!",
  PERMISSION_ACTIVED_SUCCES: "Permission successfully activated!",
  UNKNOWN_ERROR_STATUS: "Unknown error: Unable to change the status",
  ERROR_CODE: 500,
  DELETE_PERMISSION_SUCCES: "Successfully delete!",
  PERMISSION_SUCCEFULLY_CREATE: "Permission successfully created!",
  UNKNOWN_ERROR_PERMISSION_CREATED: "Creation failed, an unknown error occurred",
  PERMISSION_SUCCEFULLY_UPDATE: "Permission successfully created!",
  UNKNOWN_ERROR_PERMISSION_UPDATE: "Creation failed, an unknown error occurred",
  NO_AUTORISATION_PERMISSION_CREATED: "You are not authorized to create a permission",
  PERMISSION_WAS_DELETED: "Permission was deleted",
  PERMISSION_WAS_DESACTIVED: "Permission was deactivated",
  NO_AUTORISATION_CHANGE_PERMISSION: "You are not authorized to change a permission",
  NO_AUTORISATION_ROLE_CREATED: "You are not authorized to create a ROLE",

  ROLE_WAS_DELETED: "Role was deleted",
  ROLE_WAS_DESACTIVED: "Role was deactivated",
  NO_AUTORISATION_CHANGE_ROLE: "You are not authorized to change a role",

  APPOINTMENT_SAVE_SUCCES: "Votre rendez-vous a été enregistré avec succès !",

  APPOINTMENT_HOUR_IS_REQUIRED: "Appointment hour is required",
  ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED: "Original appointment hour is required",
  APPOINTMENT_DATE_IS_REQUIRED: "Appointment date is required",
  PATIENT_MESSAGE_IS_REQUIRED: "Patient message is required",
  HOUR_PATTERN_NOT_MATCHES: "The format of appointment hour is incorrect"

}
