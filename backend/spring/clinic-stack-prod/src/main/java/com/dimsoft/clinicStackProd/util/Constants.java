package com.dimsoft.clinicStackProd.util;

public class Constants {
	public static final short STATE_ACTIVATED = 0, STATE_DELETED = 2, STATE_ARCHIVE = 1, STATE_DEACTIVATED = 3;

	// format de l'heure effective du rendez-vous
	public static final String APPOINTMENT_HOUR_PATTERN = "..:.. .M";

	// Codes d'erreurs
	public static final String USER_NON_AUTHENTICATED = "401", CONNECTION_TIMEOUT = "402", ERROR_PAGE_NOT_FOUND = "404",
			SERVER_ERROR = "500", SERVER_DENY_RESPONSE = "504", ITEM_NOT_FOUND = "403", ITEM_ALREADY_EXIST = "501",
			ITEM_ALREADY_DELETED = "502", ITEM_ALREADY_DEACTIVATED = "503", ITEM_IS_REQUIRED = "504",
			SLOT_TIME_NOT_FREE = "505", INVALID_INPUT = "506", FILE_NOT_READ = "507", FILE_NOT_DOWNLOAD = "508",
			MAIL_NOT_SEND = "509", NOT_ACTIVE_ITEM = "510", RUN_TIME = "511", NULL_POINTER = "512";

	// rôles par défaut
	public static final String USER = "USER", ADMIN = "ADMIN", SPECIALIST = "SPECIALIST";
	public static final String N_A_CREATE = "CREATE", N_A_UPDATE = "UPDATE", N_A_CRON = "CRON";

	// message d'erreurs
	public static final String USER_NOT_FOUND = "User not found",
			SPECIALIST_NOT_FOUND = "Specialist not found",
			APPOINTMENT_NOT_FOUND = "Appointment not found",
			CONTACT_NOT_FOUND = "Contact not found",
			PERMISSION_NOT_FOUND = "Permission not found",
			PLANNING_NOT_FOUND = "Planning not found",
			ROLE_NOT_FOUND = "Role not found",
			SETTING_NOT_FOUND = "Setting not found",
			SPECIALITY_NOT_FOUND = "Speciality not found",
			SPECIALIST_AND_SPECIALITY_NOT_FOUND = "Speciality and Speciality not found",
			USER_AND_ROLE_NOT_FOUND = "User and role not found",
			ROLE_AND_PERMISSION_NOT_FOUND = "role and permission not found",
			ROLE_PERMISSION_NOT_FOUND = "Role-permission not found",
			SPECIALIST_SPECIALITY_NOT_FOUND = "Speciality-speciality not found",
			USER_ROLE_NOT_FOUND = "User-role not found",

			USERNAME_ALREADY_EXIST = "Username already exist",
			EMAIL_ALREADY_EXIST = "Email already exist",
			USERNAME_AND_EMAIL_ALREADY_EXIST = "Username and email already exist",
			EMAIL_IS_NOT_CORRECT = "Email is not correct",
			SPECIALITY_NAME_ALREADY_EXIST = "Speciality name already exist",
			ROLE_NAME_ALREADY_EXIST = "Role name already exist",
			PERMISSION_NAME_ALREADY_EXIST = "Permission name already exist",
			SPECIALIST_ALREADY_EXIST = "Specialist already exist",

			USER_ALREADY_DELETED = "User already deleted",
			SPECIALIST_ALREADY_DELETED = "Specialist already deleted",
			APPOINTMET_ALREADY_DELETED = "Appointment already deleted",
			CONTACT_ALREADY_DELETED = "Contact already deleted",
			PERMISSION_ALREADY_DELETED = "Permission already deleted",
			PLANNING_ALREADY_DELETED = "Planning already deleted",
			ROLE_ALREADY_DELETED = "Role already deleted",
			SETTING_ALREADY_DELETED = "Setting already deleted",
			SPECIALITY_ALREADY_DELETED = "Speciality already deleted",
			SPECIALIST_SPECIALITY_ALREADY_DELETED = "Specialist-speciality already deleted",
			SPECIALIST_SPECIALITY_ALREADY_DEACTIVATED = "Specialist-speciality already deactivated",

			USER_ALREADY_DEACTIVATED = "User already deactivated",
			SPECIALIST_ALREADY_DEACTIVATED = "Specialist already deactivated",
			ROLE_ALREADY_DEACTIVATED = "Role already deactivated",
			PERMISSION_ALREADY_DEACTIVATED = "Permission already deactivated",

			PATIENT_NAME_IS_REQUIRED = "Patient name is required",
			PATIENT_EMAIL_IS_REQUIRED = "Patient email is required",
			PATIENT_PHONE_IS_REQUIRED = "Patient phone is required",
			APPOINTMENT_HOUR_IS_REQUIRED = "Appointment hour is required",
			ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED = "Original appointment hour is required",
			APPOINTMENT_DATE_IS_REQUIRED = "Appointment date is required",
			PATIENT_MESSAGE_IS_REQUIRED = "Patient message is required",
			HOUR_PATTERN_NOT_MATCHES = "The format of appointment hour is incorrect",

			TIME_NOT_FREE = "This slot time is not free",
			INVALID_INPUT_STRING = "Bad request",
			COULD_NOT_READ_FILE = "Could not read file",
			COULD_NOT_DELETE_FILE = "Could not process the deletion",
			COULD_NOT_DOWNLOAD_FILE = "Could not download file",
			COULD_NOT_SEND_MAIL = "Could not download file",

			NOT_ACTIVE_USERS = "There is not active user",
			NOT_ACTIVE_SPECIALIST = "There is not active specialist",
			SETTING_MUST_NOT_BE_NULL = "The setting must not be null or empty",
			SETTING_IS_NULL = "The setting is null",
			CONTACT_MUST_BE_NOT_NULL = "The contact list must not be null or empty",
			CONTACT_IS_NULL = "The contact list is null",
			OLD_PASSWORD_NOT_MATCH = "Old password do not match",

			ROLE_PERMISSION_ALREADY_EXIST = "role-permission already exist",
			USER_ROLE_ALREADY_EXIST = "User-role already exist",
			SPECIALIST_SPECIALITY_ALREADY_EXIST = "specialist-speciality already exist",

			RUN_TIME_EXCEPTION = "Malformed URL has occurred",

			USER_IS_NULL = "Value of user is null",
			SPECIALIST_IS_NULL = "Value of specialist is null",
			SPECIALITY_IS_NULL = "Value of speciality is null",
			ROLE_IS_NULL = "Value of role is null",
			PERMISSION_IS_NULL = "Value of permission is null";

}
