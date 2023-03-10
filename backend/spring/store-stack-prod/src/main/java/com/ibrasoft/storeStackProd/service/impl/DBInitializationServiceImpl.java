package com.ibrasoft.storeStackProd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Permission;
import com.ibrasoft.storeStackProd.beans.Role;
import com.ibrasoft.storeStackProd.beans.RolePermission;
import com.ibrasoft.storeStackProd.beans.RolePermissionId;
import com.ibrasoft.storeStackProd.beans.Setting;
import com.ibrasoft.storeStackProd.beans.SpecialistSpeciality;
import com.ibrasoft.storeStackProd.beans.Speciality;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.beans.UserRole;
import com.ibrasoft.storeStackProd.beans.UserRoleId;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.InvalidInputException;
import com.ibrasoft.storeStackProd.models.SpecialityModel;
import com.ibrasoft.storeStackProd.rbac.models.AuthorityModel;
import com.ibrasoft.storeStackProd.repository.PermissionRepository;
import com.ibrasoft.storeStackProd.repository.RolePermissionRepository;
import com.ibrasoft.storeStackProd.repository.RoleRepository;
import com.ibrasoft.storeStackProd.repository.SpecialityRepository;
import com.ibrasoft.storeStackProd.repository.UserRepository;
import com.ibrasoft.storeStackProd.service.DBInitializationService;
import com.ibrasoft.storeStackProd.service.SettingService;
import com.ibrasoft.storeStackProd.service.SpecialityService;
import com.ibrasoft.storeStackProd.util.Constants;

@Service
@Transactional
public class DBInitializationServiceImpl implements DBInitializationService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository rolesRepo;
	@Autowired
	SpecialityRepository specialityRepo;
	@Autowired
	PermissionRepository permissionsRepo;
	@Autowired
	RolePermissionRepository rolesPermissionRepo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SpecialityService specialityService;
	@Autowired
	SettingService settingService;

	Role adminRole = new Role();
	Role specialistRole = new Role();
	Role userRole = new Role();

	@Value("${app.admin_username}")
	String adminUserName;
	@Value("${app.admin_password}")
	String adminPassword;

	@Value("${app.clinic_logo_location}")
	String clinicLogoLocation;
	@Value("${app.clinic_opening_hour}")
	Integer clinicOpeningHour;
	@Value("${app.clinic_closing_hour}")
	Integer clinicClosingHour;
	@Value("${app.default_clinic_email}")
	String defaultClinicEmail;
	@Value("${app.clinic_secondary_email}")
	String clinicSecondaryEmail;
	@Value("${app.default_clinic_phone}")
	String defaultClinicPhone;
	@Value("${app.clinic_secondary_phone}")
	String clinicSecondaryPhone;
	@Value("${app.default_clinic_address}")
	String defaultClinicAddress;
	@Value("${app.clinic_fb_link}")
	String clinicFbLink;
	@Value("${app.clinic_lk_link}")
	String clinicLinkedInLink;
	@Value("${app.clinic_tt_link}")
	String clinicTwitterLink;
	@Value("${app.clinic_insta_link}")
	String clinicInstaLink;
	@Value("${app.clinic_video_link}")
	String clinicVideoLink;
	@Value("${app.clinic_video_cover_location}")
	String clinicVideoCoverLocation;

	@Override
	public void initUsers() {
		List<Role> rolesList = new ArrayList<Role>();
		Role adminRole = rolesRepo.findByRoleName("ADMIN");
		if (adminRole != null) {
			rolesList.add(adminRole);
		}
		User user = new User(
				passwordEncoder.encode(adminPassword),
				adminUserName,
				"privileged@ibrasoft.eu",
				"Privileged",
				"The",
				new Date(),
				adminPassword,
				"237681190361");
		if (!userRepo.existsByUsername(adminUserName)) {
			User userSave = userRepo.save(user);
			if (rolesList.size() > 0) {
				rolesList.forEach(role -> {
					UserRoleId userRoleId = new UserRoleId(userSave, role);
					UserRole userRole = new UserRole(userRoleId);
					if (userSave.getUsersRolesList() == null) {
						userSave.setUsersRolesList(new ArrayList<UserRole>());
					}
					userSave.getUsersRolesList().add(userRole);
				});
			}
			userRepo.save(userSave);
		}
	}

	@Override
	public void initPermissions() {

		Stream.of(
				// users
				new AuthorityModel("GET_ALL_USERS",
						"Permission d'obtenir la liste des utilisateurs du syst??me sous format JSON"),
				new AuthorityModel("CREATE_USER",
						"Permission de cr??er un nouvel utilisateur"),
				new AuthorityModel("UPDATE_USER_INFOS",
						"Permission de modifier les informations d'un utilisateur y compris la possibilit?? d'activer ou de d??sactiver celui-ci"),
				new AuthorityModel("DELETE_USER",
						"Permission de supprimer un utilisateur"),
				new AuthorityModel("UPDATE_USER_STATUS",
						"Permission d'activer ou de d??sactiver un utilisateur"),
				new AuthorityModel("FIND_USER",
						"Permission de rechercher un utilisateur par son id sur le backend"),
				new AuthorityModel("UPDATE_PASSWORD",
						"Permission pour un utilisateur de modifier de modifier son mot de passe"),
				new AuthorityModel("UPDATE_ADMIN_OR_SPECIALIST",
						"Permission de modifier les informations basiques d'un utilisateur"),
				new AuthorityModel("CREATE_ADMIN_OR_SPECIALIST",
						"Permission de cr??er et d'attribuer un r??le ?? un nouvel utilisateur"),
				new AuthorityModel("UPDATE_IMAGE",
						"Permission de mettre ?? jour la photo de profil d'un utlisateur"),

				// user-roles
				new AuthorityModel("GET_ALL_USERS_ROLES",
						"Permission d'obtenir toutes les entr??es de la table users-roles sur le backend"),
				new AuthorityModel("CREATE_USER_ROLE",
						"Permission d'attribuer un role ?? un utilisateur"),
				new AuthorityModel("DELETE_USER_ROLE",
						"Permission de supprimer un r??le de la liste des r??les d'un utilisateur"),
				new AuthorityModel("FIND_USER_ROLE",
						"Permission de rechercher une entr??e de la table users-roles par son id sur le backend"),
				new AuthorityModel("ASSIGN_ROLES_TO_USER",
						"Permission d'assigner ou de retirer une liste de roles ?? un utilisateur"),
				new AuthorityModel("REMOVE_ROLES_TO_USER",
						"Permission de retirer une liste de roles ?? un utilisateur"),
				new AuthorityModel("GET_ALL_USER_ROLES",
						"Permission d'obtenir la liste des r??les d'un utilisateur"),

				// roles
				new AuthorityModel("CREATE_ROLE",
						"Permission de cr??er un r??le"),
				new AuthorityModel("UPDATE_ROLE_STATUS",
						"Permission d'activer ou de d??sactiver un r??le"),
				new AuthorityModel("DELETE_ROLE",
						"Permission de supprimer un r??le"),
				new AuthorityModel("UPDATE_ROLE_INFOS",
						"Permission de mettre les informations d'un r??le ?? jour"),
				new AuthorityModel("GET_ALL_ROLES",
						"Permission d'obtenir la liste de tout les r??les"),
				new AuthorityModel("GET_ACTIVE_ROLES",
						"Permission d'obtenir la liste des r??les actifs"),
				new AuthorityModel("FIND_ROLE",
						"Permission de rechercher un r??le par son id"),

				// roles-permission
				new AuthorityModel("VIEW_ROLE_PERMISSIONS",
						"Permission de lister les permissions d'un r??le"),
				new AuthorityModel("GET_ALL_ROLE_PERMISSIONS",
						"Permission d'obtenir la liste des permissions associ??es ?? un r??le"),
				new AuthorityModel("CREATE_ROLE_PERMISSION",
						"Permission d'attribuer une permission ?? un r??le"),
				new AuthorityModel("DELETE_ROLE_PERMISSION",
						"Permission de retirer une permission ?? un r??le"),
				new AuthorityModel("FIND_ROLE_PERMISSION",
						"Permission de rechercher une permission associ??e ?? un r??le"),
				new AuthorityModel("ASSIGN_PERMISSIONS_TO_ROLE",
						"Permission d'attribuer une permission ?? un r??le"),
				new AuthorityModel("REMOVE_PERMISSIONS_TO_ROLE",
						"Permission de retirer une permission ?? un r??le"),

				// permission
				new AuthorityModel("UPDATE_PERMISSION_STATUS",
						"Permission d'activer ou de d??sactiver une permission"),
				new AuthorityModel("DELETE_PERMISSION",
						"Permission de supprimer une permission"),
				new AuthorityModel("GET_ALL_PERMISSIONS",
						"Permission d'obtenir la liste des permissions supprim??es ou non de la base de donn??es"),
				new AuthorityModel("GET_ACTIVE_PERMISSIONS",
						"Permission d'obtenir la liste des permissions non supprim??es de la base de donn??es"),
				new AuthorityModel("CREATE_PERMISSION",
						"Permission de cr??er une permission"),
				new AuthorityModel("UPDATE_PERMISSION_INFOS",
						"Permission de mettre ?? jour les informations d'une permission"),
				new AuthorityModel("FIND_PERMISSIONS_BY_ID",
						"Permission de rechercher une permission par son id"),
				new AuthorityModel("GET_ALL_ARCHIVED_PERMISSIONS",
						"Permission d'obtenir la liste des permissions archiv??es"),

				// appointment
				new AuthorityModel("GET_ALL_APPOINTMENT",
						"Permission d'obtenir la liste des rendez-vous supprim??s ou non en base de donn??es"),
				new AuthorityModel("GET_ALL_APPOINTMENTS",
						"Permission d'obtenir la liste de tout les rendez-vous tri??e par ordre descendant de date"),
				new AuthorityModel("GET_ALL_DISTINCTS_PATIENTS",
						"Permission d'obtenir la liste de tout les rendez-vous group??e par patient"),
				new AuthorityModel("GET_COUNT_ALL_APPOINTMENT",
						"Permission d'obtenir le nombre total de rendez-vous d'un sp??cialiste"),
				new AuthorityModel("GET_AllAPPOINTMENT_SPECIALIST",
						"Permission d'obtenir les rendez-vous d'un sp??cialiste"),
				new AuthorityModel("UPDATE_SPECIALIST_STATE",
						"Permission de modifier le statut d'un rendez pour un sp??cialiste"),
				new AuthorityModel("GET_ALL_TODAY_APPOINTMENT",
						"Permission d'obtenir les rendez-vous du jour pour un sp??cialiste"),
				new AuthorityModel("GET_ALL_SUP_TODAY_APPOINTMENT",
						"Permission d'obtenir les rendez-vous futures ?? partir du jour j pour un sp??cialiste"),
				new AuthorityModel("GET_ALL_ACTIVED_APPOINTMENT",
						"Permission d'obtenir la liste de tout les rendez-vous non supprim??s pour un sp??cialiste"),
				new AuthorityModel("GET_ALL_ARCHIVED_APPOINTMENT",
						"Permission d'obtenir la liste de tout les rendez-vous archiv??s pour un sp??cialiste"),
				new AuthorityModel("DELETE_APPOINTMENT",
						"Permission de supprimer un rendez-vous"),
				new AuthorityModel("GET_ALL_BY_ID_APPOINTMENT",
						"Permission de rechercher un rendez-vous par son id"),
				new AuthorityModel("GET_ALL_BY_ID",
						"Permission de v??rifier si un rendez existe en base de donn??es"),
				new AuthorityModel("GET_BY_SPECIALIST_ID_APPOINTMENT",
						"Permission de rechercher un rendez-vous par son sp??cialiste"),

				// planning
				new AuthorityModel("GET_ALL_SPECIALIST_PLANNING",
						"Permission d'obtenir la liste des planning d'un sp??cialiste"),
				new AuthorityModel("CREATE_PLANNING",
						"Permission de cr??er un planning"),
				new AuthorityModel("UPDATE_PLANNING",
						"Permission de mettre ?? jour un planning"),
				new AuthorityModel("DELETE_ONE_PLANNING",
						"Permission de supprimer un planning"),
				new AuthorityModel("DELETE_MANY_PLANNING",
						"Permission de supprimer plusieurs planning"),

				// settings
				new AuthorityModel("GET_ALL_SETTINGS",
						"Permission d'acceder ?? toutes les configurations du site Web"),
				new AuthorityModel("CREATE_OR_UPDATE_SETTING",
						"Permission de cr??er ou de modifier une configuration du site Web"),
				new AuthorityModel("DELETE_SETTING",
						"Permission de supprimer une configuration du site Web"),

				// Specialist
				new AuthorityModel("CREATE_SPECIALIST",
						"Permission de cr??er un sp??cialiste"),
				new AuthorityModel("UPDATE_SPECIALIST",
						"Permission de modifier les informations d'un sp??cialiste"),
				new AuthorityModel("DELETE_SPECIALIST",
						"Permission de supprimer un sp??cialiste"),
				new AuthorityModel("FIND_SPECIALIST_BY_ID",
						"Permission de rechercher un sp??cialiste par son id"),
				new AuthorityModel("UPDATE_SOCIAL_MEDIA",
						"Permission de cr??er ou de modifier les liens vers les r??seaux sociaux d'un sp??cialiste"),
				new AuthorityModel("GET_SOCIAL_MEDIA",
						"Permission d'obtenir la liste des liens vers les r??seaux sociaux d'un sp??cialiste"),

				// specialist-specialities
				new AuthorityModel("CREATE_SPECIALIST_SPECIALISTY",
						"Permission d'attribuer une sp??cialit?? ?? un sp??cialiste"),
				new AuthorityModel("GET_ALL_SPECIALIST_SPECIALISTY",
						"Permission d'obtenir toutes les sp??cialit??s avec les sp??cialistes associ??s"),
				new AuthorityModel("GET_ALL_SPECIALITY_BY_SPECIALIST_ID",
						"Permission d'obtenir la liste des sp??cialit??s d'un sp??cialiste"),
				new AuthorityModel("UPDATE_SPECIALIST_SPECIALISTY",
						"Permission de mettre ?? jour la liste des sp??cialit??s d'un sp??cialiste"),
				new AuthorityModel("DELETE_SPECIALIST_SPECIALISTY_BY_ID",
						"Permission de supprimer une sp??cialit?? associ??e ?? un sp??cialiste"),
				new AuthorityModel("GET_BY_SPECIALIST_ID_AND_SPECIALISTY_ID",
						"Permission d'obtenir une sp??cialit?? associ??e ?? un sp??cialiste ?? partir des ids des deux"),
				new AuthorityModel("ASSIGN_SPECIALISTY_TO_SPECIALIST",
						"Permission d'assigner une sp??cialit?? ?? un sp??cialiste"),
				new AuthorityModel("REMOVE_SPECIALISTY_FROM_LIST",
						"Permission de retirer une sp??cialit?? ?? un sp??cialiste"),
				new AuthorityModel("FIND_SPECIALISTY_BY_NAME",
						"Permission de rechercher un sp??cialiste par son nom"),

				// specialities
				new AuthorityModel("CREATE_SPECIALITY",
						"Permission de cr??er une sp??cialit??"),
				new AuthorityModel("UPDATE_SPECIALITY",
						"Permission de modifier les informations d'une sp??cialist??"),
				new AuthorityModel("GET_ALL_ACTIVATED_SPECIALITY",
						"Permission d'obtenir la liste des sp??cialist??s non supprim??es dans la base de donn??es"),
				new AuthorityModel("DELETE_SPECIALITY",
						"Permission de supprimer une sp??cialit?? par son id"),
				new AuthorityModel("FIND_SPECIALITY",
						"Permission de rechercher une sp??cialit?? par son id"),

				// contacts GET_ALL_CONTACTS
				new AuthorityModel("GET_ALL_CONTACTS",
						"Permission d'obtenir tous les message de contact")

		)
				.forEach(auth -> {
					if (!permissionsRepo.existsByPermissionName(auth.getCode())) {
						Permission permission = new Permission(new Date(), auth.getCode(), auth.getDescription());
						permissionsRepo.save(permission);
					}
				});
	}

	@Override
	public void initRoles() {
		String adminRoleName = Constants.ADMIN;
		String userRoleName = Constants.USER;
		String specialistRoleName = Constants.SPECIALIST;
		adminRole = new Role(adminRoleName, "The administrator", new Date());
		userRole = new Role(userRoleName, "The user", new Date());
		specialistRole = new Role(specialistRoleName, "the specialist", new Date());

		Role adminRoleFound = rolesRepo.findByRoleName(adminRoleName);
		if (adminRoleFound == null)
			adminRole = rolesRepo.save(adminRole);

		Role userRoleFound = rolesRepo.findByRoleName(userRoleName);
		if (userRoleFound == null)
			userRole = rolesRepo.save(userRole);

		Role specialistRoleFound = rolesRepo.findByRoleName(specialistRoleName);
		if (specialistRoleFound == null)
			specialistRole = rolesRepo.save(specialistRole);

		// Assign permissions to admin
		Stream.of("GET_ALL_USERS", "CREATE_USER", "UPDATE_USER_INFOS", "DELETE_USER", "SEARCH_USER",
				"UPDATE_USER_STATUS", "FIND_USER", "GET_ALL_USERS_ROLES", "CREATE_USER_ROLE",
				"DELETE_USER_ROLE", "FIND_USER_ROLE", "ASSIGN_ROLES_TO_USER", "REMOVE_ROLES_TO_USER",
				"GET_ALL_USER_ROLES", "CREATE_ROLE", "UPDATE_ROLE_INFOS", "DELETE_ROLE", "VIEW_ROLE_PERMISSIONS",
				"UPDATE_PERMISSION_INFOS", "UPDATE_ROLE_STATUS",
				"UPDATE_PERMISSION_STATUS", "DELETE_PERMISSION", "UPDATE_PASSWORD",
				"GET_ALL_ROLES", "GET_ACTIVE_ROLES", "FIND_ROLE", "GET_ALL_ROLE_PERMISSIONS", "CREATE_ROLE_PERMISSION",
				"DELETE_ROLE_PERMISSION", "FIND_ROLE_PERMISSION", "ASSIGN_PERMISSIONS_TO_ROLE",
				"REMOVE_PERMISSIONS_TO_ROLE", "UPDATE_ADMIN_OR_SPECIALIST",
				"CREATE_ADMIN_OR_SPECIALIST", "UPDATE_IMAGE", "FIND_PERMISSIONS_BY_ID", "GET_ALL_ARCHIVED_PERMISSIONS",
				"GET_ALL_SETTINGS", "CREATE_OR_UPDATE_SETTING", "DELETE_SETTING",
				"CREATE_SPECIALIST", "DELETE_SPECIALIST", "FIND_SPECIALIST_BY_ID", "CREATE_SPECIALIST_SPECIALISTY",
				"GET_ALL_SPECIALIST_SPECIALISTY", "GET_ALL_SPECIALIST_SPECIALISTY_BY_ID",
				"GET_ALL_SPECIALITY_BY_SPECIALIST_ID",
				"UPDATE_SPECIALIST_SPECIALISTY", "DELETE_SPECIALIST_SPECIALISTY_BY_ID",
				"GET_BY_SPECIALIST_ID_AND_SPECIALISTY_ID", "ASSIGN_SPECIALISTY_TO_SPECIALIST",
				"REMOVE_SPECIALISTY_FROM_LIST", "FIND_SPECIALISTY_BY_NAME", "CREATE_SPECIALITY", "UPDATE_SPECIALITY",
				"GET_ALL_ACTIVATED_SPECIALITY", "DELETE_SPECIALITY", "FIND_SPECIALITY",
				"GET_ALL_APPOINTMENT", "GET_ALL_APPOINTMENTS", "GET_ALL_DISTINCTS_PATIENTS",
				"GET_COUNT_ALL_APPOINTMENT", "GET_AllAPPOINTMENT_SPECIALIST", "UPDATE_SPECIALIST_STATE",
				"GET_ALL_TODAY_APPOINTMENT", "GET_ALL_SUP_TODAY_APPOINTMENT", "GET_ALL_ACTIVED_APPOINTMENT",
				"GET_ALL_ARCHIVED_APPOINTMENT", "DELETE_APPOINTMENT", "GET_ALL_BY_ID_APPOINTMENT",
				"GET_ALL_BY_ID", "GET_BY_SPECIALIST_ID_APPOINTMENT", "GET_ALL_PERMISSIONS", "GET_ACTIVE_PERMISSIONS",
				"CREATE_PERMISSION", "GET_ALL_SPECIALIST_PLANNING", "CREATE_PLANNING", "UPDATE_PLANNING",
				"DELETE_ONE_PLANNING", "DELETE_MANY_PLANNING", "GET_ALL_CONTACTS")
				.forEach(permissionName -> {
					Permission permission = permissionsRepo.findByPermissionName(permissionName);
					if (permission != null) {
						Role roleFound = rolesRepo.findByRoleName(adminRoleName);
						if (roleFound != null) {
							RolePermissionId rolePermissionId = new RolePermissionId(roleFound, permission);
							RolePermission rolePermission = new RolePermission(rolePermissionId);
							Optional<RolePermission> rolePermissionFound = rolesPermissionRepo
									.findById(rolePermissionId);
							if (!rolePermissionFound.isPresent()) {
								rolesPermissionRepo.save(rolePermission);
							}
						}
					}
				});

		// Assign permissions to user
		Stream.of("")
				.forEach(permissionName -> {
					Permission permission = permissionsRepo.findByPermissionName(permissionName);
					if (permission != null) {
						Role roleFound = rolesRepo.findByRoleName(userRoleName);
						if (roleFound != null) {
							RolePermissionId rolePermissionId = new RolePermissionId(roleFound, permission);
							RolePermission rolePermission = new RolePermission(rolePermissionId);
							Optional<RolePermission> rolePermissionFound = rolesPermissionRepo
									.findById(rolePermissionId);
							if (!rolePermissionFound.isPresent()) {
								rolesPermissionRepo.save(rolePermission);
							}
						}
					}
				});

		// Assign permissions to specialist
		Stream.of("UPDATE_USER_INFOS", "UPDATE_PASSWORD", "UPDATE_IMAGE", "GET_ALL_APPOINTMENT", "GET_ALL_APPOINTMENTS",
				"GET_ALL_DISTINCTS_PATIENTS", "GET_COUNT_ALL_APPOINTMENT", "GET_AllAPPOINTMENT_SPECIALIST",
				"GET_AllAPPOINTMENT_SPECIALIST", "UPDATE_SPECIALIST_STATE", "GET_ALL_TODAY_APPOINTMENT",
				"GET_ALL_SUP_TODAY_APPOINTMENT", "GET_ALL_SUP_TODAY_APPOINTMENT", "GET_ALL_ARCHIVED_APPOINTMENT",
				"DELETE_APPOINTMENT", "GET_ALL_BY_ID_APPOINTMENT", "GET_ALL_BY_ID", "GET_BY_SPECIALIST_ID_APPOINTMENT",
				"DELETE_MANY_PLANNING", "CREATE_PLANNING", "UPDATE_PLANNING", "DELETE_ONE_PLANNING",
				"UPDATE_SPECIALIST", "UPDATE_SOCIAL_MEDIA", "GET_SOCIAL_MEDIA", "CREATE_SPECIALIST_SPECIALISTY",
				"GET_ALL_SPECIALIST_SPECIALISTY", "GET_ALL_SPECIALIST_SPECIALISTY_BY_ID",
				"GET_ALL_SPECIALITY_BY_SPECIALIST_ID",
				"UPDATE_SPECIALIST_SPECIALISTY", "DELETE_SPECIALIST_SPECIALISTY_BY_ID",
				"GET_BY_SPECIALIST_ID_AND_SPECIALISTY_ID", "ASSIGN_SPECIALISTY_TO_SPECIALIST",
				"REMOVE_SPECIALISTY_FROM_LIST", "FIND_SPECIALISTY_BY_NAME",
				"GET_ALL_ACTIVATED_SPECIALITY", "FIND_SPECIALITY",
				"GET_ALL_ACTIVED_APPOINTMENT", "GET_ALL_SPECIALIST_PLANNING", "GET_ALL_SETTINGS")
				.forEach(permissionName -> {
					Permission permission = permissionsRepo.findByPermissionName(permissionName);
					if (permission != null) {
						Role roleFound = rolesRepo.findByRoleName(specialistRoleName);
						if (roleFound != null) {
							RolePermissionId rolePermissionId = new RolePermissionId(roleFound, permission);
							RolePermission rolePermission = new RolePermission(rolePermissionId);
							Optional<RolePermission> rolePermissionFound = rolesPermissionRepo
									.findById(rolePermissionId);
							if (!rolePermissionFound.isPresent()) {
								rolesPermissionRepo.save(rolePermission);
							}
						}
					}
				});
	}

	public void initSpecialities() {

		List<SpecialistSpeciality> list = new ArrayList<SpecialistSpeciality>();

		Stream.of(
				new SpecialityModel(
						"M??decine g??n??rale",
						"M??decin g??n??raliste",
						"Le service de m??decine g??n??rale propose ?? ses patients une offre personnalis??e de soins humains, de qualit?? et innovants.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">M??decine g??n??rale</span></strong></h1><p><span style=\"color:rgb(0, 0, 0);\">Le service de m??decine g??n??rale propose ?? ses patients une offre personnalis??e de soins humains, de qualit?? et innovants. D??couvrez ci-dessous les informations essentielles sur le traitement, la consultation ou la prise de rendez-vous avec un m??decin g??n??raliste de la Clinique M??dico-Chirurgicale le Ber??eau.</span></p><h3><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que la m??decine g??n??rale et la m??decine polyvalente ?</span></h3><p>La <strong>m??decine g??n??rale </strong>assure une prise en charge globale du patient par la continuit?? et la coordination de ses soins en ville. C???est avant tout une <strong>m??decine de proximit??</strong> qui joue notamment un r??le essentiel dans le <strong>suivi des patients</strong> ??g??s et des personnes en situation pr??caire.</p><p>La <strong>m??decine polyvalente</strong>, elle, accueille les<strong> patients polypathologiques</strong> demandant une prise en charge globale et parfois complexe dans le cadre d???une hospitalisation.. La plupart des services de m??decine polyvalente de la CMCB ont une orientation g??riatrique.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909125/inline-images/chirurgie-generale-%28personnalise%29.jpg\" alt=\"Chirurgie g??n??rale\"></p><h3><span style=\"color:rgb(127, 164, 198);\">Que fait le m??decin g??n??raliste ?</span></h3><p>Le m??decin g??n??raliste, qualifi?? aussi de <strong>m??decin omnipraticien</strong>, re??oit tous les patients sans distinction d?????ge et de pathologies. Son champ d???intervention est large : il aide le patient ?? pr??ciser ses maux, proc??de ?? un examen clinique g??n??ral (tension, r??flexes, auscultation cardiaque???) ou sp??cifique, pose un diagnostic, prescrit des m??dicaments et suit les maladies chroniques. Si n??cessaire, le m??decin g??n??raliste fait r??aliser des examens compl??mentaires (analyse de sang, imagerie m??dicale) et adresse son patient ?? des m??decins sp??cialistes et/ou ?? des professionnels param??dicaux (infirmier, kin??sith??rapeute , orthophoniste???). Le m??decin g??n??raliste agit enfin au quotidien dans la promotion de la sant?? et la <strong>pr??vention des maladies</strong> (vaccination, d??pistage, conseils nutritionnels, contraception).</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment trouver un m??decin traitant ?</span></h3><p>Depuis 2005, tout patient de plus de 16 ans doit d??signer un m??decin comme son ?? m??decin traitant ?? aupr??s de l???<strong>assurance maladie</strong> (d??claration ?? remplir avec le m??decin choisi). Celui-ci assure la pr??vention personnalis??e et la coordination du suivi m??dical avec les autres professionnels de soins. ?? l???instar du m??decin de famille, le <strong>m??decin traitant</strong> est un interlocuteur privil??gi?? dans la dur??e. Afin de b??n??ficier d???un remboursement au taux maximal des actes m??dicaux une consultation chez un sp??cialiste doit ??tre initi??e par un m??decin traitant, hormis dans certains cas tr??s particuliers :</p><ul><li><p>un <strong>gyn??cologue </strong>, pour les examens cliniques gyn??cologiques p??riodiques, y compris les actes de d??pistage, la prescription et le suivi d'une contraception, le suivi d'une grossesse, l'interruption volontaire de grossesse (IVG) m??dicamenteuse ;</p></li><li><p>un <strong>ophtalmologue </strong>, pour la prescription et le renouvellement de lunettes, les actes de d??pistage et de suivi du glaucome ;</p></li><li><p>un <strong>psychiatre </strong>ou un neuropsychiatre, si vous avez entre 16 et 25 ans ;</p></li><li><p>un <strong>stomatologue </strong>sauf pour des actes chirurgicaux lourds.</p></li></ul><p>Il est possible de changer de m??decin traitant, sans motif particulier, sur simple information ?? la s??curit?? sociale.</p>",
						"Storage/Images/1665406200640-medecin-generalist.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"P??diatrie",
						"P??diatre",
						"Le p??diatre assure la prise en charge globale de l'enfant.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">P??diatrie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la p??diatrie ?</span></h2><p>La p??diatrie est une sp??cialit?? m??dicale qui se consacre ?? l???enfant, depuis la vie intra-ut??rine (en lien avec <a href=\"https://www.elsan.care/fr/patients/gynecologie-obstetrique\" title=\"Gyn??cologie-obst??trique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l???obst??trique </span></span></a>) jusqu????? la fin de l???adolescence. En s???int??ressant ?? l???alimentation et en suivant la croissance et l?????volution de l???enfant, la p??diatrie exerce un r??le important de pr??vention et de d??tection, et s???attache ?? diagnostiquer et ?? <strong>traiter les pathologies</strong> qui peuvent affecter sa sant??. Aujourd???hui, le <strong>m??decin p??diatre</strong>, qui travaille souvent en r??seau avec d???autres <strong>professionnels de sant??</strong>, de l?????ducation et des travailleurs sociaux, s???impose comme un acteur cl?? de son d??veloppement.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909184/inline-images/pediatrie-%28personnalise%29.jpg\" alt=\"P??diatrie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait un p??diatre ?</span></h2><p><strong>M??decin g??n??raliste de l???enfant</strong>, le p??diatre veille ?? sa sant??, au bon d??roulement de sa croissance et ?? son d??veloppement, physique et mental. Interlocuteur privil??gi?? des parents, il les conseille en mati??re de nutrition, de sommeil, d???hygi??ne, de <strong>pr??vention des accidents</strong>. C???est lui aussi qui se charge du d??pistage pr??coce de certaines pathologies et du suivi de la vaccination. Si l???enfant pr??sente des sympt??mes de maladies, il l???interroge si possible sur son affection, proc??de ?? un examen clinique, et prescrit des examens compl??mentaires (analyses de sang, d???urine, ??chographies, etc.), pour d??terminer le traitement adapt?? au contexte clinique et ?? son ??ge. .</p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un p??diatre ?</span></h2><p>Dans la petite enfance, o?? la croissance rapide fragilise l???organisme, les <strong>consultations chez le p??diatre</strong>, rembours??es par la s??curit?? sociale, r??pondent ?? un calendrier obligatoire : mensuelle jusqu????? 6 mois, trimestrielle jusqu????? 1 an, trois fois par an jusqu????? 2 ans, deux fois l???an jusqu????? 6 ans??? Le m??decin p??diatre mesure alors la taille et le <strong>poids de l???enfant</strong>, v??rifie son d??veloppement moteur et sensoriel, la vision et l???audition, et proc??de ?? la vaccination. Dans tous les cas, si l???enfant manifeste des troubles ou des signes de maladies, infantiles ou autres, il ne faut pas h??siter ?? consulter : le p??diatre assure la prise en charge, quitte ?? orienter vers d???autres sp??cialistes selon l???organe concern??, s???il le juge n??cessaire. Quant aux urgences p??diatriques, une consultation peut se justifier en cas de fi??vre importante, avec signes de convulsions, de douleurs importantes inexpliqu??es, de <strong>sympt??mes de troubles respiratoires </strong>(bronchiolite chez le b??b??, crises d???asthme persistante???), de diarrh??es et vomissements persistants, etc. Selon leur gravit??, les chutes, br??lures ou contusions n??cessitent aussi une <strong>consultation aux urgences p??diatriques</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son p??diatre ?</span></h2><p>Si le choix d???un p??diatre d??pend des crit??res des parents (sexe, ??ge, rattach?? ou non ?? un ??tablissement de sant??, <strong>centre p??diatrique</strong>,&nbsp;<strong>centre p??diatrique</strong>, etc.), il importe de s???assurer de sa disponibilit??. Bouche-??-oreille, information aupr??s de son g??n??raliste ou de son pharmacien??? : les sources pour identifier le p??diatre r??pondant aux attentes de chacun ne manquent pas. Le <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"M??decine g??n??rale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">g??n??raliste</span></span></a> peut aussi parfois tr??s bien assumer ce r??le, s???il l???accepte.</p>",
						"Storage/Images/1665401493558-pediatrie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Gyn??cologie",
						"Gyn??cologue",
						"La gyn??cologie est le domaine m??dical qui ??tudie et traite les diff??rentes pathologies de l???appareil g??nital de la femme et les troubles hormonaux f??minins.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Gyn??cologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que la gyn??cologie ?</span></h2><p>La gyn??cologie est le domaine m??dical qui ??tudie et traite les diff??rentes pathologies de l???appareil g??nital de la femme et les troubles hormonaux f??minins : r??gles, m??nopause, contraception, maladies du sein, de l???ut??rus, des ovaires, des trompes??? La gyn??cologie inclut aussi l???obst??trique, la sp??cialit?? du<strong> gyn??cologue obst??tricien</strong> est d??di??e au suivi de la grossesse et ?? l???accouchement.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909157/inline-images/gynecologie-medicale-%28personnalise%29.jpg\" alt=\"Gyn??cologie m??dicale\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un gyn??cologue ?</span></h2><p><strong>La consultation d???un gyn??cologue</strong> n???impose pas un premier passage chez le m??decin traitant. La premi??re visite intervient en g??n??ral ?? l???adolescence et/ou pour la mise en place d???une contraception. Il est ensuite recommand?? de proc??der ?? un suivi r??gulier, une fois par an, et de ne pas h??siter ?? consulter lors de toute situation anormale : grosseur au sein, saignements anormaux, douleurs pelviennes, irr??gularit?? menstruelle, r??gles douloureuses ou trop abondantes??? Lors d???une grossesse, de probl??me de fertilit?? ou ?? la m??nopause, le gyn??cologue reste aussi <strong>l???interlocuteur privil??gi?? de la femme.</strong></p><p>Si vous souhaitez obtenir d'avantage d'informations, n'h??sitez pas ?? nous <a href=\"https://www.elsan.care/fr/annuaire/specialistes/gynecologue-obstetricien/france?nom=\" target=\"_blank\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">contacter et ?? prendre rendez-vous en ligne</span></span></a> avec l'un de nos gyn??cologues.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son gyn??cologue ?</span></h2><p>La relation de confiance entre le gyn??cologue et sa patiente est essentielle. La clinique M??dico-Chirurgicale le Ber??eau dispose de services de gyn??cologie-obst??trique performants et de <a href=\"https://www.elsan.care/fr/store-locator?tab=praticiens&amp;specialite=59\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">praticiens </span></span></a>?? l?????coute.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quel suivi r??alise la maternit???</span></h2><p>Un<strong> suivi r??gulier gyn??cologique</strong> constitue un gage de bonne sant?? pour la femme. Celui-ci peut aussi ??ventuellement ??tre assur?? par un g??n??raliste et, depuis 2009, par une <a href=\"https://www.elsan.care/fr/metiers/sage-femme\" title=\"Sage-femme\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">sage-femme</span></span></a>. Le gyn??cologue prend en charge la <strong>contraception</strong>, le d??pistage du cancer du col de l???ut??rus par le frottis cervico-vaginal, r??alis?? tous les trois ans, et celui du cancer du sein par la mammographie, avec le concours d???un <a href=\"https://www.elsan.care/fr/patients/radiologie\" title=\"Radiologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">radiologue</span></span></a>. Il prescrit aussi des examens gyn??cologiques dont des ??cographies de contr??le.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir sa maternit?? ?</span></h2><p>La maternit?? constitue un service associ?? de la gyn??cologie. Pour<strong> choisir la maternit?? la plus adapt??e</strong> ?? ses besoins et ?? ses d??sirs, mieux vaut bien s???informer avant m??me la grossesse. S??curit?? optimale, accompagnement personnalis?? pour les futures mamans, cocooning, pr??paration ?? l???accouchement et ?? la parentalit??, assistante m??dicale ?? la procr??ation (AMP), ?? papa friendly ????? : La Clinique M??dico-Chirurgicale le Ber??eau innove et am??liore sans cesse ses services dans les <strong>34 maternit??s du groupe, </strong>pour que les parents vivent une belle exp??rience.</p>",
						"Storage/Images/1665401616984-gynecologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Cardiologie",
						"Cardiologue",
						"La cardiologie s???int??resse ?? l???appareil cardiovasculaire, c???est-??-dire au c??ur et aux vaisseaux (art??res et veines), ?? la pr??vention ainsi qu???au traitement des anomalies et des maladies qui l???affectent.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Cardiologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que la cardiologie ?</span></h2><p>La cardiologie s???int??resse ?? l???<strong>appareil cardiovasculaire</strong>, c???est-??-dire au c??ur et aux vaisseaux (art??res et veines), ?? la pr??vention ainsi qu???au traitement des anomalies et des maladies qui l???affectent : hypertension art??rielle, insuffisance cardiaque, troubles du rythme cardiaque, angine de poitrine, ath??roscl??rose ??? Le cardiologue peut ??tre amen?? ?? intervenir en urgence notamment en cas d???infarctus du myocarde.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909120/inline-images/cardiologie-%28personnalise%29.jpg\" alt=\"Cardiologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un cardiologue ?</span></h2><p>Hormis en cas d???urgence, c???est au <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"M??decine g??n??rale\"><strong><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">m??decin traitant</span></span></strong></a><strong> d???orienter son patient vers un cardiologue</strong> s???il le juge n??cessaire. En cardiologie, la pr??vention est primordiale, a fortiori pour les personnes ?? risques : diab??tiques, patients en surpoids, hypertendus, hypercholest??rol??miques, fumeurs, etc. Aujourd???hui, les maladies cardiovasculaires restent la premi??re cause de d??c??s en France chez les femmes devant les tumeurs.</p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le cardiologue ?</span></h2><p>Le cardiologue interroge le patient sur ses habitudes de vie et ses ant??c??dents m??dicaux et familiaux. Il proc??de ?? une <strong>auscultation cardiaque et pulmonaire</strong>, mesure la tension art??rielle. Plus les facteurs de risques sont nombreux, plus l???examen est pouss??. Pour visualiser le rythme cardiaque et d??celer une ??ventuelle anomalie, il peut effectuer un <strong>??lectrocardiogramme</strong>. Un test d???effort peut ??galement ??tre pratiqu??. Si besoin, il pr??conise des examens compl??mentaires : ??chographie cardiaque et/ou vasculaire, coronarographie, scintigraphie ou IRM.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passe le traitement cardiologique ?</span></h2><p>La prise en charge des <strong>probl??mes cardio-vasculaires </strong>passe g??n??ralement, en premier lieu, par une am??lioration de l???hygi??ne de vie (alimentation, activit?? physique, diminution du stress). La prise en charge m??dicale peut ??tre, quant ?? elle, m??dicamenteuse ou chirurgicale.</p><p>En plein essor, la cardiologie interventionnelle est une forme particuli??re de chirurgie. R??alis??e en urgence ou non, la <strong>cardiologie interventionnelle</strong> permet de traiter certaines affections (pathologies coronaires et valvulaires, malformations...) par voie endovasculaire, c???est-??-dire en passant des instruments chirurgicaux miniaturis??s ?? l???int??rieur d???une art??re ou d???une veine. En cas de prise en charge en urgence, le patient peut ??tre admis au service de soins intensifs cardiologiques.</p><p>La rythmologie est ??galement une sur sp??cialit?? en cardiologie qui prend en charge les troubles du rythme cardiaque par plusieurs techniques :</p><ul><li><p>Mise en place de stimulateurs et de d??fi brillateurs cardiaques, les premiers permettant d?????viter un ralentissement excessif du c??ur, et les seconds les troubles du rythme rapides, mettant la vie du patient en danger.</p></li><li><p>Ablation par radiofr??quence des troubles du rythme rapides qui consiste ?? explorer l???activit?? ??lectrique du c??ur par des cath??ters pour rep??rer le tissu cardiaque responsable des troubles du rythme. On d??livre ensuite un courant ??lectrique de radiofr??quence sur ce tissu afin de r??tablir le rythme normal du c??ur.</p></li></ul>",
						"Storage/Images/1665401734101-cardiologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Urologie",
						"Urologue",
						"Sp??cialit?? m??dico-chirurgicale, l???urologie prend en charge les affections de l???appareil urinaire de la femme et de l???homme (vessie, uret??re, ur??tre).",
						"<h1><strong><span style=\"color:deepskyblue;\">Qu???est-ce que l???urologie ?</span></strong></h1><p>Sp??cialit?? m??dico-chirurgicale, l???urologie prend en charge les affections de l???appareil urinaire de la femme et de l???homme (vessie, uret??re, ur??tre). Elle couvre aussi l???appareil g??nital et reproducteur masculin (prostate, p??nis, testicules).</p><p>                                                                         <img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909205/inline-images/urologie-%28personnalise%29.jpg\" alt=\"Urologie\"><br></p><h1><strong><span style=\"color:deepskyblue;\">Quand consulter un urologue ?</span></strong></h1><p>Il est recommand?? de prendre d???abord rendez-vous chez son m??decin g??n??raliste. C???est lui qui oriente vers une&nbsp;consultation chez un urologue&nbsp;lors notamment :</p><ul><li><p>d???infections urinaires ?? r??p??tition,</p></li><li><p>de fuites urinaires,</p></li><li><p>de traces de sang dans les urines,</p></li><li><p>de douleurs au niveau des testicules</p></li><li><p>de dysfonctionnements de la prostate ou encore de troubles de l?????rection: des affections intimes dont les patients peuvent s???ouvrir ?? ce sp??cialiste sans tabou.</p></li></ul><p>C???est lui ??galement qui propose un&nbsp;d??pistage du cancer de la prostate&nbsp;?? partir de 50 ans. Il existe aussi des&nbsp;urologues pour les enfants sp??cialis??s, par exemple, dans les malformations ou l?????nur??sie (incontinence nocturne).</p><h1><strong><span style=\"color:deepskyblue;\">Que soigne un chirurgien urologue ?</span></strong></h1><p>L???urologue soigne de multiples&nbsp;affections en rapport avec l???appareil urinaire&nbsp;: r??tention urinaire ou incontinence,</p><ul><li><p>infections,</p></li><li><p>l??sions,</p></li><li><p>malformations</p></li><li><p>cancers de l???appareil urinaire</p></li><li><p>etc..</p></li></ul><p>Le&nbsp;m??decin urologue&nbsp;traite aussi l???infertilit?? masculine, les troubles de l?????rection et de l?????jaculation, les cancers des testicules, de la prostate??? S???il peut prendre en charge des pathologies sans avoir recours ?? la chirurgie (colique n??phr??tique entra??nant des calculs r??naux notamment), de nombreuses maladies de cette sp??cialit?? sont trait??es par voie chirurgicale.</p><h1><strong><span style=\"color:deepskyblue;\">Comment se passent le diagnostic et les chirurgies en urologie ?</span></strong></h1><p>Pour ??mettre un&nbsp;diagnostic, l???urologue interroge son patient sur ses sympt??mes et ses ant??c??dents m??dicaux et familiaux avant de proc??der ?? un examen clinique et, en fonction des troubles, ?? un examen du p??rin??e et/ou ?? un toucher rectal. Il peut aussi prescrire des&nbsp;examens compl??mentaires&nbsp;: ECBU (examen cytobact??riologique des urines), ??chographie, endoscopie, radiographie, bilan urodynamique, cystoscopie, etc. S???appuyant sur des techniques aujourd???hui moins invasives (c??lioscopie, ultrasons, hyperthermie, laser ???) gr??ce ?? des ??quipements innovants pr??sents dans la Clinique M??dico-Chirurgicale le Ber??eau, la&nbsp;chirurgie en urologie&nbsp;a beaucoup progress?? ces derni??res ann??es, et les interventions sont de plus en plus souvent r??alis??es en ambulatoire.</p> ",
						"Storage/Images/1665401826095-urologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Endocrinologie -  Diab??tologie",
						"Diab??tologue",
						"Le service d'endocrinologie propose ?? ses patients une offre personnalis??e de soins humains, de qualit?? et innovants.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Endocrinologie - Diab??tologie</span></strong></h1><p><span style=\"color:rgb(0, 0, 0);\">Le service d'endocrinologie propose ?? ses patients une offre personnalis??e de soins humains, de qualit?? et innovants. D??couvrez ci-dessous les informations essentielles sur le traitement, la consultation ou la prise de rendez-vous avec un endocrinologue de la Clinique du Pont de Chaume.</span></p><h3><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que l???endocrinologie ?</span></h3><p>L???endocrinologie est la <strong>sp??cialit?? m??dicale</strong> s???int??ressant aux hormones, ?? leurs effets sur le fonctionnement du corps ??? le m??tabolisme - et aux maladies qui y sont li??es. Les hormones sont s??cr??t??es par diff??rentes glandes : hypophyse, thyro??de, glandes surr??nales... De nombreuses maladies sont dues ?? des <strong>d??r??glements hormonau</strong>x : diab??te de type 1, diab??te de type 2, troubles de la croissance, <strong>hypothyro??die</strong>, hyperthyro??die, troubles du poids???<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909149/inline-images/endocrino-diabete-%28personnalise%29.jpg\" alt=\"Endocrinologie\"></p><h3><span style=\"color:rgb(127, 164, 198);\">Comment prendre rendez-vous avec un endocrinologue ?</span></h3><p>Avant de <strong>prendre rendez-vous avec un endocrinologue</strong> et pour b??n??ficier d???un remboursement normal des soins de sant??, il est n??cessaire de consulter son m??decin traitant qui jugera de l???utilit?? de r??orienter ou non vers un endocrinologue.</p><h3><span style=\"color:rgb(127, 164, 198);\">Que fait un endocrinologue lors d???une consultation ?</span></h3><p>Lors d???une premi??re visite, l???endocrinologue interroge le patient sur ses sympt??mes : perte ou prise de poids inexpliqu??e, fatigue, douleurs, troubles de l???humeur... Il est recommand?? de se munir des r??sultats d???examens (analyses de sang, examens radiologiques...) et comptes-rendus m??dicaux dont on dispose pour ??viter la r??p??tition d???examens inutiles et faciliter l???<strong>??tablissement du diagnostic</strong>. Le cas ??ch??ant, l???endocrinologue peut demander des examens compl??mentaires : analyses de sang particuli??res, imagerie, ponctions, etc.</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment se passent les traitements en endocrinologie ?</span></h3><p>Le traitement d??pend du type de d??r??glement hormonal. A titre d???exemple, un d??ficit hormonal, comme dans le cas d???une hypothyro??die, peut se traiter par un apport quotidien en <strong>hormones thyro??diennes</strong> de substitution. L???hyperthyro??die peut, quant ?? elle, se traiter ?? l???aide de m??dicaments ou n??cessiter une intervention chirurgicale.</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment traiter les diff??rents types de diab??te ?</span></h3><p>Il existe trois types principaux de diab??te :</p><ul><li><p><strong>le diab??te de type 1</strong>, apparaissant d??s l???enfance ;</p></li><li><p><strong>le diab??te gestationnel</strong>, apparaissant lors d???une grossesse et disparaissant souvent apr??s l???accouchement ;</p></li><li><p><strong>le diab??te de type 2</strong>, le plus courant, apparaissant apr??s 40 ans.</p></li></ul><p>Le diab??te est li?? ?? un d??faut d???effet d???une hormone appel??e insuline. La prise en charge impose une adaptation de l???alimentation. Si elle ne suffit pas ?? normaliser le taux de sucre dans le sang ??? la glyc??mie ??? des m??dicaments ou l???injection r??guli??re d???insuline peut ??tre n??cessaires.</p><p>La Clinique M??dico-Chirurgicale le Ber??eau prend en charge ces pathologies, en utilisant les nouvelles technologies de mesure du glucose en continu : holter glyc??mique, pompe sous-cutan??e ?? insuline...</p>",
						"Storage/Images/1665402280755-diabetologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Rhumatologie",
						"Rhumatologue",
						"La rhumatologie est une sp??cialit?? m??dicale qui s???int??resse au fonctionnement de l???appareil locomoteur (squelette, muscles, articulations) et ?? ses dysfonctionnements.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Rhumatologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que la rhumatologie ?</span></h2><p>La rhumatologie est une <strong>sp??cialit?? m??dicale</strong> qui s???int??resse au fonctionnement de l???appareil locomoteur (squelette, muscles, articulations) et ?? ses dysfonctionnements.<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909200/inline-images/rhumatologie-%28personnalise%29.jpg\" alt=\"Rhumatologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un m??decin rhumatologue ?</span></h2><p>Votre <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"M??decine g??n??rale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">m??decin traitant</span></span></a> vous orientera vers un rhumatologue si vous souffrez de douleurs osseuses, articulaires ou musculaires inexpliqu??es ou d???une g??ne fonctionnelle (mouvement limit??, blocage articulaire).</p><h2><span style=\"color:rgb(127, 164, 198);\">Que soigne la rhumatologie ?</span></h2><p>Le rhumatologue prend en charge</p><ul><li><p>Des affections osseuses (fractures, ost??oporose, malformations???),</p></li><li><p>Des <strong>douleurs articulaires</strong> (arthrose, polyarthrite rhumato??de???)</p></li><li><p>Des douleurs p??ri-articulaires (tendinites, tennis elbow???)</p></li><li><p>Des affections nerveuses (sciatique, syndrome du canal carpien, lombalgies???)</p></li></ul><p>Outre des facteurs g??n??tiques, les seniors, les sportifs, certains corps de m??tier ??? b??timent, agriculture, couture ??? et les personnes en surpoids sont plus particuli??rement touch??s par ces <strong>pathologies</strong>. La population f??minine de plus de 50 ans est, elle-aussi, tr??s expos??e du fait de la fragilisation osseuse induite par la <strong>m??nopause</strong>. Quantifiable par un examen (l???ost??odensitom??trie), la perte de r??sistance osseuse constitue la cause premi??re de <strong>l???arthrose </strong>et de <strong>l???ost??oporose</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passe un traitement en rhumatologie ?</span></h2><p>Le terme g??n??rique de ?? <strong>rhumatismes </strong>?? couvre en fait plus d???une centaine d???affections, plus ou moins aigu??s, touchant l???ensemble de l???appareil locomoteur. Ils se manifestent par des douleurs, des gonflements et/ou des raideurs articulaires. La <strong>consultation d???un rhumatologue</strong> constitue un moment-cl?? pour d??finir un <strong>protocole th??rapeutique</strong> ad??quat et personnalis??. Le traitement de la douleur s???op??re de fa??on graduelle. Il peut passer par des traitements locaux (cr??mes, gels et patches, orth??ses de soulagement, infiltrations, stimulations ??lectriques???) ou g??n??raux, par injection ou par voie orale. Le recours ?? la chirurgie par un <a href=\"https://www.elsan.care/fr/patients/chirurgie-orthopedique-et-traumatologique\" title=\"Chirurgie orthop??dique et traumatologique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">chirurgien orthop??diste</span></span></a>, notamment la pose d???une proth??se (genou et hanche, le plus souvent) intervient dans les cas tr??s invalidants. Le maintien d???une activit?? adapt??e est recommand?? pour pr??server la masse musculaire et le <strong>fonctionnement articulaire</strong>.</p>",
						"Storage/Images/1665402507909-rhumatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Chirurgie",
						"Chirurgien",
						"La chirurgie g??n??rale s???occupe de diagnostiquer et de traiter diverses affections. Son champs d???intervention est vaste et inclut notamment.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Chirurgie g??n??rale</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la chirurgie g??n??rale ?</span></h2><p>La chirurgie g??n??rale s???occupe de diagnostiquer et de traiter diverses affections. Son champs d???intervention est vaste et inclut notamment&nbsp;:&nbsp;</p><ul><li><p>Chirurgie d???urgence</p></li><li><p>Appendicite</p></li><li><p>Calculs</p></li><li><p>traumatologie</p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le chirurgien g??n??ral ?</span></h2><p>Le chirurgien g??n??ral pratique des&nbsp;<strong>interventions chirurgicales</strong>&nbsp;vari??es afin de traiter divers syst??mes du corps humain, notamment&nbsp;:</p><ul><li><p>les organes abdominaux</p></li><li><p>le thorax</p></li><li><p>les glandes endocrines</p></li><li><p>la peau et les tissus mous</p></li><li><p>les art??res et les veines</p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un sp??cialiste en chirurgie visc??rale et digestive ?</span></h2><p>L???orientation vers un&nbsp;<strong>chirurgien g??n??ral&nbsp;</strong>repose sur l???avis du m??decin traitant et/ou du sp??cialiste suivant l???affection dont le patient souffre : h??pato-gastro-ent??rologue,&nbsp;<a href=\"https://www.elsan.care/fr/patients/oncologie-medicale%22%20%5Co%20%22Oncologie%20m%C3%A9dicale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">canc??rologue</span></span></a>, proctologue,&nbsp;<a href=\"https://www.elsan.care/fr/patients/endocrinologie%22%20%5Co%20%22Endocrinologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">endocrinologue</span></span></a>,&nbsp;<a href=\"https://www.elsan.care/fr/patients/gynecologie%22%20%5Co%20%22Gyn%C3%A9cologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">gyn??cologue</span></span></a>,&nbsp;<a href=\"https://www.elsan.care/fr/patients/urologie%22%20%5Co%20%22Urologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">urologue&nbsp;</span></span></a>...</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son chirurgien g??n??ral ?</span></h2><p>Cette sp??cialit?? de chirurgie g??n??rale fait appel ?? des gestes tr??s techniques, n??cessitant donc des&nbsp;<strong>praticiens exp??riment??s</strong>&nbsp;r??alisant un nombre important d???interventions et disposant d???un mat??riel de pointe : chirurgie robot-assist??e, mat??riel d???imagerie au bloc??? Il est important de choisir, lorsque c???est possible, un centre de soins disposant des&nbsp;<strong>??quipements les plus r??cents</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quels sont les risques d'une op??ration chirurgicale de la cavit?? abdominale ou thoracique&nbsp;?</span></h2><p>Il s???agit le plus souvent d???interventions sous anesth??sie g??n??rale. Les risques li??s ?? l???intervention (s??quelles musculaires et/ou neurologiques, h??morragie, infection???) ??&nbsp;<a href=\"https://www.elsan.care/fr/patients/anesthesie-et-reanimation%22%20%5Co%20%22Anesth%C3%A9sie%20et%20r%C3%A9animation\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l???anesth??sie&nbsp;</span></span></a>et ?? l?????tat de sant?? du patient sont r??els. C???est la raison pour laquelle le chirurgien, en lien avec l???anesth??siste, ??value ces risques, d??cide du bien-fond?? de l???op??ration et des modalit??s d???intervention les plus adapt??es.</p>",
						"Storage/Images/1665402814577-chirurgie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Dermatologie",
						"Dermatologue",
						"La dermatologie est la sp??cialit?? m??dicale qui s???int??resse ?? l?????tude de la peau, des cheveux, des poils et des ongles ainsi qu????? la pr??vention, au diagnostic et au traitement des maladies qui les touchent.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Dermatologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu???est-ce que la dermatologie ?</span></h2><p>La dermatologie est la sp??cialit?? m??dicale qui s???int??resse ?? l?????tude de la peau, des cheveux, des poils et des ongles ainsi qu????? la pr??vention, au diagnostic et au traitement des maladies qui les touchent. Tr??s vari??es, ces maladies concernent tous les ??ges de la vie.</p><p>La dermatologie s???int??resse aussi ?? l???aspect de la peau et ?? son am??lioration (??limination des rides, ??pilation???). On parle alors de <a href=\"https://www.elsan.care/fr/patients/chirurgie-plastique-reconstructrice-et-esthetique\" title=\"Chirurgie plastique reconstructrice et esth??tique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">dermatologie esth??tique et cosm??tique</span></span></a>. Dans ce secteur, la clinique M??dico-Chirurgicale le Ber??eau propose aussi des soins de qualit??.<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909146/inline-images/dermatologie-%28personnalise%29.jpg\" alt=\"Dermatologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un dermatologue ?</span></h2><p>D??s que la peau prend un aspect inhabituel ??? apparition d???une protub??rance, de rougeurs ou de d??mangeaisons, augmentation de la taille d???un grain de beaut??, perte de cheveux??? ???&nbsp;il est conseill?? de consulter son m??decin traitant qui, s???il le juge utile, vous r??orientera vers un <strong>m??decin dermatologue</strong>.</p><p>En cas d???<strong>ant??c??dents de maladies de peau</strong>, personnels ou familiaux, ou d???exposition ?? des facteurs de risque (exposition importante au soleil, ?? des produits chimiques, ?? un milieu humide???), il peut ??tre utile d?????tre suivi r??guli??rement par un dermatologue. Acn??, ecz??ma, <strong>psoriasis</strong>, verrue, herp??s, zona, kyste cutan??, hyperpigmentation, m??lanome??? : les pathologies de la peau, des plus b??nignes aux plus graves, sont prises en charge dans les <a href=\"https://www.elsan.care/fr/groupe\" title=\"Le Groupe\"><strong><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">services de dermatologie</span></span></strong></a> des <a href=\"https://www.elsan.care/fr/groupe\" title=\"Le Groupe\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">Clinique M??dico-Chirurgicale le Ber??eau</span></span></a>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le dermatologue ?</span></h2><p>Le dermatologue interroge le patient sur la nature du probl??me, sur ses ant??c??dents m??dicaux et familiaux et son hygi??ne de vie. Il proc??de ?? l???auscultation de la zone touch??e par le probl??me (l??sion, grain de beaut?????) mais aussi d???autres zones o?? il peut ??tre pr??sent sans ??tre encore visible. Il peut r??aliser une <strong>dermoscopie</strong>, examen indolore, pour visualiser la peau en profondeur, ou une <strong>biopsie</strong> <strong>cutan??e</strong> pour pr??ciser la nature du probl??me (<a href=\"https://www.elsan.care/fr/centre-cancerologie-dentellieres/nos-actualites/la-prevention-des-cancers-cutanes-au-centre-de\" target=\"_blank\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">m??lanome ou autre pathologie</span></span></a>).</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passent les traitements dermatologiques ?</span></h2><p>En plus des m??dicaments administr??s localement ou par voie orale, le dermatologue dispose de diff??rentes techniques de traitement comme :</p><ul><li><p>L?????limination de l??sions (verrues, grains de beaut?????) ou de d??fauts cutan??s par peeling, azote liquide, ex??r??se chirurgicale, laser???&nbsp;</p></li><li><p>La phototh??rapie contre le psoriasis,</p></li><li><p>Les injections locales de produits de comblement ou de toxine botulique (Botox) contre les rides.</p></li><li><p>Phototh??rapie contre le psoriasis</p></li></ul>",
						"Storage/Images/1665402996459-dermatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Traumatologie",
						"Traumatologue",
						"Le service de chirurgie orthop??dique et traumatologie propose ?? ses patients une offre personnalis??e de soins humains, de qualit?? et innovants.",
						"<h1 style=\"text-align:center\"><strong><span style=\"color:rgb(0, 0, 98);\"><span style=\"background-color:rgb(255, 255, 255);\">TRAUMATOLOGIE : ??TUDE DES TRAUMATISMES</span></span></strong></h1><p><span style=\"color:rgb(0, 0, 98);\"><span style=\"background-color:rgb(255, 255, 255);\"><img src=\"https://www.concilio.com/wp-content/uploads/Orthop%C3%A9die-proth%C3%A8se-hanches-genou-chirurgien-Concilio_718x452.jpg?x41967\" alt=\"Concilio - Traumatologie : ??tude des traumatismes\"></span></span></p><h2><span style=\"color:rgb(0, 0, 98) !important;\"><span style=\"background-color:rgb(245, 245, 245);\">LE SAVIEZ-VOUS ?</span></span></h2><p><span style=\"background-color:rgb(245, 245, 245);\">Au Cameroun, une grande majorit?? de la population doit faire face un jour ?? un probl??me orthop??dique. Ainsi 150 000 proth??ses de hanche et 100 000 de genou sont pos??es par an.</span></p><p><span style=\"background-color:rgb(245, 245, 245);\">Pour mettre toutes les chances de votre c??t?? face ?? la maladie, l?????quipe m??dicale de la CMCB vous accompagne personnellement.</span></p><h2><span style=\"color:rgb(0, 0, 98) !important;\"><span style=\"background-color:rgb(255, 255, 255);\">QU'EST-CE QUE LA TRAUMATOLOGIE ?</span></span></h2><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">La traumatologie est la discipline ??tudiant les traumatismes physiques regroupant l???ensemble des chocs subits par un sujet. Ils incluent la br??lure, l???accident de voiture, la chute, l???</span><a href=\"https://www.concilio.com/orthopedie-entorse-de-la-cheville\"><span style=\"color:rgb(190, 0, 135) !important;\"><span style=\"background-color:transparent;\">entorse</span></span></a>, etc. On distingue la traumatologie sportive, des l??sions ??troitement li??es ?? la pratique du sport, la traumatologie routi??re, etc.</p><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les autres facteurs favorables aux traumatismes incluent entre autres&nbsp;:</span></p><ul><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les agressions</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">La noyade</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les complications de soins m??dicaux et chirurgicaux</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les accidents atteignant la respiration</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les l??sions</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les faits de guerre</span></p></li></ul>",
						"Storage/Images/1665403275772-Traumatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Hepato-Gastro-Enterologie",
						"Gastro-Ent??rologue",
						"L???h??pato-gastro-ent??rologie est la sp??cialit?? m??dicale qui s???int??resse aux organes de la digestion, leurs fonctionnements, leurs maladies et les moyens de les soigner.",
						"<h1><strong><span style=\"color:inherit;\">Gastro-ent??rologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Qu'est-ce que la gastro-ent??rologie ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">L???h??pato-gastro-ent??rologie est la sp??cialit?? m??dicale qui s???int??resse aux organes de la digestion, leurs fonctionnements, leurs maladies et les moyens de les soigner. Les organes constituant le syst??me digestif sont :</span></span></p><ul><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le tube digestif (??sophage, estomac, intestins, le colon et le rectum)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le foie</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le pancr??as</span></span></p></li></ul><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\"><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909152/inline-images/gastroenterologie-%28personnalise%29.jpg\" alt=\"H??pato-gastro-ent??rologie\"></span></span></p><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Que fait le gastro-ent??rologue ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">Il d??piste, diagnostique et traite des maladies du syst??me digestif aussi vari??es que :</span></span></p><ul><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies inflammatoires de l???intestin (rectocolite h??morragie, Maladie de Crohn???)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies du foie et des voies biliaires (calculs, tumeurs???)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les h??patites</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les h??morragies digestives</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies du pancr??as</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les cancers digestifs</span></span></p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Quand consulter un sp??cialiste en gastro-ent??rologie ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">La consultation d???un h??pato-gastro-ent??rologue a lieu ?? la demande du m??decin traitant ou d???un autre sp??cialiste. Cette r??orientation permet de faire r??aliser et/ou interpr??ter des examens pour ??tablir un diagnostic, mettre en place un traitement, r??aliser un&nbsp;geste technique particulier.</span></span></p><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Comment choisir un gastro-ent??rologue ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">L???h??pato-gastro-ent??rologie fait appel ?? des techniques, notamment d???imageries, particuli??res. Le choix de ce sp??cialiste sera donc fait en concertation avec le m??decin traitant qui connait les ??tablissements les mieux ??quip??s et les praticiens avec lesquels il travaille en confiance.</span></span></p>",
						"Storage/Images/1665403549033-H??pato-gastro-ent??rologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Neurochirurgie",
						"Neurochirurgien",
						"La neurochirurgie est la sp??cialit?? chirurgicale dont le domaine d???expertise est le diagnostic et la prise en charge chirurgicale des troubles du syst??me nerveux.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Neurochirurgie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la neurochirurgie ?</span></h2><p>La neurochirurgie est la sp??cialit?? chirurgicale dont le domaine d???expertise est le diagnostic et la prise en charge chirurgicale des troubles du syst??me nerveux. Le syst??me nerveux se divise en trois grands segments :</p><ul><li><p>le syst??me nerveux central (cerveau, mo??lle ??pini??re)</p></li><li><p>le syst??me nerveux p??riph??rique (nerfs qui vont du syst??me nerveux central vers le reste du corps)</p></li><li><p>les syst??mes nerveux v??g??tatifs ou ?? autonomes ??, assurant le fonctionnement d???organes de mani??re automatique (innervation de l???intestin, du muscle cardiaque???)</p></li></ul><p>Le champ d???intervention est particuli??rement large et les <strong>neurochirurgiens </strong>sont fr??quemment sp??cialis??s dans un <strong>type particulier de neurochirurgie</strong> :</p><ul><li><p>neurochirurgie cr??nienne</p></li><li><p>neurochirurgie du rachis</p></li><li><p>neurochirurgie p??diatrique???</p></li></ul><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909170/inline-images/neurochirurgie-%28personnalise%29.jpg\" alt=\"Neurochirurgie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le neurochirurgien ?</span></h2><p>Le neurochirurgien intervient au sein d???une ??quipe chirurgicale, en coop??ration particuli??rement ??troite avec <a href=\"https://www.elsan.care/fr/patients/anesthesie-et-reanimation\" title=\"Anesth??sie et r??animation\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l???anesth??siste</span></span></a>. Il traite des maladies aussi vari??es que la hernie discale, la maladie de Parkinson, l???hyperacousie, l???an??vrisme ou le traumatisme intracr??nien, l???h??matome c??r??bral, les compressions m??dullaires parfois responsables de douleurs chroniques (lombalgies, sciatiques???) et l???ensemble des tumeurs pouvant affecter les syst??mes nerveux.</p><p>Avant l???intervention il r??alise un <strong>bilan pr??op??ratoire</strong> pour d??terminer le type d???op??ration ?? r??aliser puis l???op??ration et assure un suivi <strong>post-op??ratoire</strong> pour s???assurer de l???efficacit?? de l???intervention et de la bonne r??mission du patient.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un neurochirurgien ?</span></h2><p>Le neurochirurgien intervient en coordination avec d???autres sp??cialistes comme le <a href=\"https://www.elsan.care/fr/patients/neurologie\" title=\"Neurologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">neurologue </span></span></a>ou le <a href=\"https://www.elsan.care/fr/patients/oncologie-medicale\" title=\"Oncologie m??dicale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">canc??rologue</span></span></a>. Hormis dans le cadre d???une urgence, le choix d???une intervention repose donc le plus souvent sur une d??cision coll??giale de l???ensemble des sp??cialistes suivant un patient pour une affection donn??e.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quels sont les risques lors de l???intervention d???un neurochirurgien ?</span></h2><p>Toute intervention chirurgicale, a fortiori sous anesth??sie g??n??rale, pr??sente des risques : h??morragie, infection, s??quelles op??ratoires (s??quelles neurologiques, douleurs???). Ils sont directement li??s au type d???intervention et ?? l?????tat de sant?? du patient. Le neurochirurgien doit en faire une description pr??cise lors du <strong>bilan pr??op??ratoire</strong>.</p>",
						"Storage/Images/1665403658041-neurochirurgie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list))
				.forEach(specialityModel -> {
					Speciality speciality = specialityModel.getSpeciality(specialityModel);
					if (!specialityRepo.existsBySpecialityName(speciality.getSpecialityName()))
						specialityRepo.save(speciality);
				});
	}

	public void initSettings() throws ClinicException, InvalidInputException {
		Setting stettings;
		stettings = settingService.getSetting();
		if (stettings == null) {
			Setting newSettings = new Setting(clinicLogoLocation, clinicOpeningHour, clinicClosingHour,
					defaultClinicEmail, clinicSecondaryEmail, defaultClinicAddress, defaultClinicPhone,
					clinicSecondaryPhone, clinicLinkedInLink, clinicFbLink, clinicTwitterLink, clinicInstaLink,
					clinicVideoLink, clinicVideoCoverLocation);
			settingService.createOrUpdate(newSettings);
		}
	}

}
