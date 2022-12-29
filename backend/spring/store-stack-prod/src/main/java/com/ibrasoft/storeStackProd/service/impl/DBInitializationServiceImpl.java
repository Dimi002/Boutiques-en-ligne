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
						"Permission d'obtenir la liste des utilisateurs du système sous format JSON"),
				new AuthorityModel("CREATE_USER",
						"Permission de créer un nouvel utilisateur"),
				new AuthorityModel("UPDATE_USER_INFOS",
						"Permission de modifier les informations d'un utilisateur y compris la possibilité d'activer ou de désactiver celui-ci"),
				new AuthorityModel("DELETE_USER",
						"Permission de supprimer un utilisateur"),
				new AuthorityModel("UPDATE_USER_STATUS",
						"Permission d'activer ou de désactiver un utilisateur"),
				new AuthorityModel("FIND_USER",
						"Permission de rechercher un utilisateur par son id sur le backend"),
				new AuthorityModel("UPDATE_PASSWORD",
						"Permission pour un utilisateur de modifier de modifier son mot de passe"),
				new AuthorityModel("UPDATE_ADMIN_OR_SPECIALIST",
						"Permission de modifier les informations basiques d'un utilisateur"),
				new AuthorityModel("CREATE_ADMIN_OR_SPECIALIST",
						"Permission de créer et d'attribuer un rôle à un nouvel utilisateur"),
				new AuthorityModel("UPDATE_IMAGE",
						"Permission de mettre à jour la photo de profil d'un utlisateur"),

				// user-roles
				new AuthorityModel("GET_ALL_USERS_ROLES",
						"Permission d'obtenir toutes les entrées de la table users-roles sur le backend"),
				new AuthorityModel("CREATE_USER_ROLE",
						"Permission d'attribuer un role à un utilisateur"),
				new AuthorityModel("DELETE_USER_ROLE",
						"Permission de supprimer un rôle de la liste des rôles d'un utilisateur"),
				new AuthorityModel("FIND_USER_ROLE",
						"Permission de rechercher une entrée de la table users-roles par son id sur le backend"),
				new AuthorityModel("ASSIGN_ROLES_TO_USER",
						"Permission d'assigner ou de retirer une liste de roles à un utilisateur"),
				new AuthorityModel("REMOVE_ROLES_TO_USER",
						"Permission de retirer une liste de roles à un utilisateur"),
				new AuthorityModel("GET_ALL_USER_ROLES",
						"Permission d'obtenir la liste des rôles d'un utilisateur"),

				// roles
				new AuthorityModel("CREATE_ROLE",
						"Permission de créer un rôle"),
				new AuthorityModel("UPDATE_ROLE_STATUS",
						"Permission d'activer ou de désactiver un rôle"),
				new AuthorityModel("DELETE_ROLE",
						"Permission de supprimer un rôle"),
				new AuthorityModel("UPDATE_ROLE_INFOS",
						"Permission de mettre les informations d'un rôle à jour"),
				new AuthorityModel("GET_ALL_ROLES",
						"Permission d'obtenir la liste de tout les rôles"),
				new AuthorityModel("GET_ACTIVE_ROLES",
						"Permission d'obtenir la liste des rôles actifs"),
				new AuthorityModel("FIND_ROLE",
						"Permission de rechercher un rôle par son id"),

				// roles-permission
				new AuthorityModel("VIEW_ROLE_PERMISSIONS",
						"Permission de lister les permissions d'un rôle"),
				new AuthorityModel("GET_ALL_ROLE_PERMISSIONS",
						"Permission d'obtenir la liste des permissions associées à un rôle"),
				new AuthorityModel("CREATE_ROLE_PERMISSION",
						"Permission d'attribuer une permission à un rôle"),
				new AuthorityModel("DELETE_ROLE_PERMISSION",
						"Permission de retirer une permission à un rôle"),
				new AuthorityModel("FIND_ROLE_PERMISSION",
						"Permission de rechercher une permission associée à un rôle"),
				new AuthorityModel("ASSIGN_PERMISSIONS_TO_ROLE",
						"Permission d'attribuer une permission à un rôle"),
				new AuthorityModel("REMOVE_PERMISSIONS_TO_ROLE",
						"Permission de retirer une permission à un rôle"),

				// permission
				new AuthorityModel("UPDATE_PERMISSION_STATUS",
						"Permission d'activer ou de désactiver une permission"),
				new AuthorityModel("DELETE_PERMISSION",
						"Permission de supprimer une permission"),
				new AuthorityModel("GET_ALL_PERMISSIONS",
						"Permission d'obtenir la liste des permissions supprimées ou non de la base de données"),
				new AuthorityModel("GET_ACTIVE_PERMISSIONS",
						"Permission d'obtenir la liste des permissions non supprimées de la base de données"),
				new AuthorityModel("CREATE_PERMISSION",
						"Permission de créer une permission"),
				new AuthorityModel("UPDATE_PERMISSION_INFOS",
						"Permission de mettre à jour les informations d'une permission"),
				new AuthorityModel("FIND_PERMISSIONS_BY_ID",
						"Permission de rechercher une permission par son id"),
				new AuthorityModel("GET_ALL_ARCHIVED_PERMISSIONS",
						"Permission d'obtenir la liste des permissions archivées"),

				// appointment
				new AuthorityModel("GET_ALL_APPOINTMENT",
						"Permission d'obtenir la liste des rendez-vous supprimés ou non en base de données"),
				new AuthorityModel("GET_ALL_APPOINTMENTS",
						"Permission d'obtenir la liste de tout les rendez-vous triée par ordre descendant de date"),
				new AuthorityModel("GET_ALL_DISTINCTS_PATIENTS",
						"Permission d'obtenir la liste de tout les rendez-vous groupée par patient"),
				new AuthorityModel("GET_COUNT_ALL_APPOINTMENT",
						"Permission d'obtenir le nombre total de rendez-vous d'un spécialiste"),
				new AuthorityModel("GET_AllAPPOINTMENT_SPECIALIST",
						"Permission d'obtenir les rendez-vous d'un spécialiste"),
				new AuthorityModel("UPDATE_SPECIALIST_STATE",
						"Permission de modifier le statut d'un rendez pour un spécialiste"),
				new AuthorityModel("GET_ALL_TODAY_APPOINTMENT",
						"Permission d'obtenir les rendez-vous du jour pour un spécialiste"),
				new AuthorityModel("GET_ALL_SUP_TODAY_APPOINTMENT",
						"Permission d'obtenir les rendez-vous futures à partir du jour j pour un spécialiste"),
				new AuthorityModel("GET_ALL_ACTIVED_APPOINTMENT",
						"Permission d'obtenir la liste de tout les rendez-vous non supprimés pour un spécialiste"),
				new AuthorityModel("GET_ALL_ARCHIVED_APPOINTMENT",
						"Permission d'obtenir la liste de tout les rendez-vous archivés pour un spécialiste"),
				new AuthorityModel("DELETE_APPOINTMENT",
						"Permission de supprimer un rendez-vous"),
				new AuthorityModel("GET_ALL_BY_ID_APPOINTMENT",
						"Permission de rechercher un rendez-vous par son id"),
				new AuthorityModel("GET_ALL_BY_ID",
						"Permission de vérifier si un rendez existe en base de données"),
				new AuthorityModel("GET_BY_SPECIALIST_ID_APPOINTMENT",
						"Permission de rechercher un rendez-vous par son spécialiste"),

				// planning
				new AuthorityModel("GET_ALL_SPECIALIST_PLANNING",
						"Permission d'obtenir la liste des planning d'un spécialiste"),
				new AuthorityModel("CREATE_PLANNING",
						"Permission de créer un planning"),
				new AuthorityModel("UPDATE_PLANNING",
						"Permission de mettre à jour un planning"),
				new AuthorityModel("DELETE_ONE_PLANNING",
						"Permission de supprimer un planning"),
				new AuthorityModel("DELETE_MANY_PLANNING",
						"Permission de supprimer plusieurs planning"),

				// settings
				new AuthorityModel("GET_ALL_SETTINGS",
						"Permission d'acceder à toutes les configurations du site Web"),
				new AuthorityModel("CREATE_OR_UPDATE_SETTING",
						"Permission de créer ou de modifier une configuration du site Web"),
				new AuthorityModel("DELETE_SETTING",
						"Permission de supprimer une configuration du site Web"),

				// Specialist
				new AuthorityModel("CREATE_SPECIALIST",
						"Permission de créer un spécialiste"),
				new AuthorityModel("UPDATE_SPECIALIST",
						"Permission de modifier les informations d'un spécialiste"),
				new AuthorityModel("DELETE_SPECIALIST",
						"Permission de supprimer un spécialiste"),
				new AuthorityModel("FIND_SPECIALIST_BY_ID",
						"Permission de rechercher un spécialiste par son id"),
				new AuthorityModel("UPDATE_SOCIAL_MEDIA",
						"Permission de créer ou de modifier les liens vers les réseaux sociaux d'un spécialiste"),
				new AuthorityModel("GET_SOCIAL_MEDIA",
						"Permission d'obtenir la liste des liens vers les réseaux sociaux d'un spécialiste"),

				// specialist-specialities
				new AuthorityModel("CREATE_SPECIALIST_SPECIALISTY",
						"Permission d'attribuer une spécialité à un spécialiste"),
				new AuthorityModel("GET_ALL_SPECIALIST_SPECIALISTY",
						"Permission d'obtenir toutes les spécialités avec les spécialistes associés"),
				new AuthorityModel("GET_ALL_SPECIALITY_BY_SPECIALIST_ID",
						"Permission d'obtenir la liste des spécialités d'un spécialiste"),
				new AuthorityModel("UPDATE_SPECIALIST_SPECIALISTY",
						"Permission de mettre à jour la liste des spécialités d'un spécialiste"),
				new AuthorityModel("DELETE_SPECIALIST_SPECIALISTY_BY_ID",
						"Permission de supprimer une spécialité associée à un spécialiste"),
				new AuthorityModel("GET_BY_SPECIALIST_ID_AND_SPECIALISTY_ID",
						"Permission d'obtenir une spécialité associée à un spécialiste à partir des ids des deux"),
				new AuthorityModel("ASSIGN_SPECIALISTY_TO_SPECIALIST",
						"Permission d'assigner une spécialité à un spécialiste"),
				new AuthorityModel("REMOVE_SPECIALISTY_FROM_LIST",
						"Permission de retirer une spécialité à un spécialiste"),
				new AuthorityModel("FIND_SPECIALISTY_BY_NAME",
						"Permission de rechercher un spécialiste par son nom"),

				// specialities
				new AuthorityModel("CREATE_SPECIALITY",
						"Permission de créer une spécialité"),
				new AuthorityModel("UPDATE_SPECIALITY",
						"Permission de modifier les informations d'une spécialisté"),
				new AuthorityModel("GET_ALL_ACTIVATED_SPECIALITY",
						"Permission d'obtenir la liste des spécialistés non supprimées dans la base de données"),
				new AuthorityModel("DELETE_SPECIALITY",
						"Permission de supprimer une spécialité par son id"),
				new AuthorityModel("FIND_SPECIALITY",
						"Permission de rechercher une spécialité par son id"),

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
						"Médecine générale",
						"Médecin généraliste",
						"Le service de médecine générale propose à ses patients une offre personnalisée de soins humains, de qualité et innovants.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Médecine générale</span></strong></h1><p><span style=\"color:rgb(0, 0, 0);\">Le service de médecine générale propose à ses patients une offre personnalisée de soins humains, de qualité et innovants. Découvrez ci-dessous les informations essentielles sur le traitement, la consultation ou la prise de rendez-vous avec un médecin généraliste de la Clinique Médico-Chirurgicale le Berçeau.</span></p><h3><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que la médecine générale et la médecine polyvalente ?</span></h3><p>La <strong>médecine générale </strong>assure une prise en charge globale du patient par la continuité et la coordination de ses soins en ville. C’est avant tout une <strong>médecine de proximité</strong> qui joue notamment un rôle essentiel dans le <strong>suivi des patients</strong> âgés et des personnes en situation précaire.</p><p>La <strong>médecine polyvalente</strong>, elle, accueille les<strong> patients polypathologiques</strong> demandant une prise en charge globale et parfois complexe dans le cadre d’une hospitalisation.. La plupart des services de médecine polyvalente de la CMCB ont une orientation gériatrique.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909125/inline-images/chirurgie-generale-%28personnalise%29.jpg\" alt=\"Chirurgie générale\"></p><h3><span style=\"color:rgb(127, 164, 198);\">Que fait le médecin généraliste ?</span></h3><p>Le médecin généraliste, qualifié aussi de <strong>médecin omnipraticien</strong>, reçoit tous les patients sans distinction d’âge et de pathologies. Son champ d’intervention est large : il aide le patient à préciser ses maux, procède à un examen clinique général (tension, réflexes, auscultation cardiaque…) ou spécifique, pose un diagnostic, prescrit des médicaments et suit les maladies chroniques. Si nécessaire, le médecin généraliste fait réaliser des examens complémentaires (analyse de sang, imagerie médicale) et adresse son patient à des médecins spécialistes et/ou à des professionnels paramédicaux (infirmier, kinésithérapeute , orthophoniste…). Le médecin généraliste agit enfin au quotidien dans la promotion de la santé et la <strong>prévention des maladies</strong> (vaccination, dépistage, conseils nutritionnels, contraception).</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment trouver un médecin traitant ?</span></h3><p>Depuis 2005, tout patient de plus de 16 ans doit désigner un médecin comme son « médecin traitant » auprès de l’<strong>assurance maladie</strong> (déclaration à remplir avec le médecin choisi). Celui-ci assure la prévention personnalisée et la coordination du suivi médical avec les autres professionnels de soins. À l’instar du médecin de famille, le <strong>médecin traitant</strong> est un interlocuteur privilégié dans la durée. Afin de bénéficier d’un remboursement au taux maximal des actes médicaux une consultation chez un spécialiste doit être initiée par un médecin traitant, hormis dans certains cas très particuliers :</p><ul><li><p>un <strong>gynécologue </strong>, pour les examens cliniques gynécologiques périodiques, y compris les actes de dépistage, la prescription et le suivi d'une contraception, le suivi d'une grossesse, l'interruption volontaire de grossesse (IVG) médicamenteuse ;</p></li><li><p>un <strong>ophtalmologue </strong>, pour la prescription et le renouvellement de lunettes, les actes de dépistage et de suivi du glaucome ;</p></li><li><p>un <strong>psychiatre </strong>ou un neuropsychiatre, si vous avez entre 16 et 25 ans ;</p></li><li><p>un <strong>stomatologue </strong>sauf pour des actes chirurgicaux lourds.</p></li></ul><p>Il est possible de changer de médecin traitant, sans motif particulier, sur simple information à la sécurité sociale.</p>",
						"Storage/Images/1665406200640-medecin-generalist.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Pédiatrie",
						"Pédiatre",
						"Le pédiatre assure la prise en charge globale de l'enfant.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Pédiatrie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la pédiatrie ?</span></h2><p>La pédiatrie est une spécialité médicale qui se consacre à l’enfant, depuis la vie intra-utérine (en lien avec <a href=\"https://www.elsan.care/fr/patients/gynecologie-obstetrique\" title=\"Gynécologie-obstétrique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l’obstétrique </span></span></a>) jusqu’à la fin de l’adolescence. En s’intéressant à l’alimentation et en suivant la croissance et l’évolution de l’enfant, la pédiatrie exerce un rôle important de prévention et de détection, et s’attache à diagnostiquer et à <strong>traiter les pathologies</strong> qui peuvent affecter sa santé. Aujourd’hui, le <strong>médecin pédiatre</strong>, qui travaille souvent en réseau avec d’autres <strong>professionnels de santé</strong>, de l’éducation et des travailleurs sociaux, s’impose comme un acteur clé de son développement.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909184/inline-images/pediatrie-%28personnalise%29.jpg\" alt=\"Pédiatrie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait un pédiatre ?</span></h2><p><strong>Médecin généraliste de l’enfant</strong>, le pédiatre veille à sa santé, au bon déroulement de sa croissance et à son développement, physique et mental. Interlocuteur privilégié des parents, il les conseille en matière de nutrition, de sommeil, d’hygiène, de <strong>prévention des accidents</strong>. C’est lui aussi qui se charge du dépistage précoce de certaines pathologies et du suivi de la vaccination. Si l’enfant présente des symptômes de maladies, il l’interroge si possible sur son affection, procède à un examen clinique, et prescrit des examens complémentaires (analyses de sang, d’urine, échographies, etc.), pour déterminer le traitement adapté au contexte clinique et à son âge. .</p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un pédiatre ?</span></h2><p>Dans la petite enfance, où la croissance rapide fragilise l’organisme, les <strong>consultations chez le pédiatre</strong>, remboursées par la sécurité sociale, répondent à un calendrier obligatoire : mensuelle jusqu’à 6 mois, trimestrielle jusqu’à 1 an, trois fois par an jusqu’à 2 ans, deux fois l’an jusqu’à 6 ans… Le médecin pédiatre mesure alors la taille et le <strong>poids de l’enfant</strong>, vérifie son développement moteur et sensoriel, la vision et l’audition, et procède à la vaccination. Dans tous les cas, si l’enfant manifeste des troubles ou des signes de maladies, infantiles ou autres, il ne faut pas hésiter à consulter : le pédiatre assure la prise en charge, quitte à orienter vers d’autres spécialistes selon l’organe concerné, s’il le juge nécessaire. Quant aux urgences pédiatriques, une consultation peut se justifier en cas de fièvre importante, avec signes de convulsions, de douleurs importantes inexpliquées, de <strong>symptômes de troubles respiratoires </strong>(bronchiolite chez le bébé, crises d’asthme persistante…), de diarrhées et vomissements persistants, etc. Selon leur gravité, les chutes, brûlures ou contusions nécessitent aussi une <strong>consultation aux urgences pédiatriques</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son pédiatre ?</span></h2><p>Si le choix d’un pédiatre dépend des critères des parents (sexe, âge, rattaché ou non à un établissement de santé, <strong>centre pédiatrique</strong>,&nbsp;<strong>centre pédiatrique</strong>, etc.), il importe de s’assurer de sa disponibilité. Bouche-à-oreille, information auprès de son généraliste ou de son pharmacien… : les sources pour identifier le pédiatre répondant aux attentes de chacun ne manquent pas. Le <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"Médecine générale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">généraliste</span></span></a> peut aussi parfois très bien assumer ce rôle, s’il l’accepte.</p>",
						"Storage/Images/1665401493558-pediatrie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Gynécologie",
						"Gynécologue",
						"La gynécologie est le domaine médical qui étudie et traite les différentes pathologies de l’appareil génital de la femme et les troubles hormonaux féminins.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Gynécologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que la gynécologie ?</span></h2><p>La gynécologie est le domaine médical qui étudie et traite les différentes pathologies de l’appareil génital de la femme et les troubles hormonaux féminins : règles, ménopause, contraception, maladies du sein, de l’utérus, des ovaires, des trompes… La gynécologie inclut aussi l’obstétrique, la spécialité du<strong> gynécologue obstétricien</strong> est dédiée au suivi de la grossesse et à l’accouchement.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909157/inline-images/gynecologie-medicale-%28personnalise%29.jpg\" alt=\"Gynécologie médicale\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un gynécologue ?</span></h2><p><strong>La consultation d’un gynécologue</strong> n’impose pas un premier passage chez le médecin traitant. La première visite intervient en général à l’adolescence et/ou pour la mise en place d’une contraception. Il est ensuite recommandé de procéder à un suivi régulier, une fois par an, et de ne pas hésiter à consulter lors de toute situation anormale : grosseur au sein, saignements anormaux, douleurs pelviennes, irrégularité menstruelle, règles douloureuses ou trop abondantes… Lors d’une grossesse, de problème de fertilité ou à la ménopause, le gynécologue reste aussi <strong>l’interlocuteur privilégié de la femme.</strong></p><p>Si vous souhaitez obtenir d'avantage d'informations, n'hésitez pas à nous <a href=\"https://www.elsan.care/fr/annuaire/specialistes/gynecologue-obstetricien/france?nom=\" target=\"_blank\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">contacter et à prendre rendez-vous en ligne</span></span></a> avec l'un de nos gynécologues.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son gynécologue ?</span></h2><p>La relation de confiance entre le gynécologue et sa patiente est essentielle. La clinique Médico-Chirurgicale le Berçeau dispose de services de gynécologie-obstétrique performants et de <a href=\"https://www.elsan.care/fr/store-locator?tab=praticiens&amp;specialite=59\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">praticiens </span></span></a>à l’écoute.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quel suivi réalise la maternité?</span></h2><p>Un<strong> suivi régulier gynécologique</strong> constitue un gage de bonne santé pour la femme. Celui-ci peut aussi éventuellement être assuré par un généraliste et, depuis 2009, par une <a href=\"https://www.elsan.care/fr/metiers/sage-femme\" title=\"Sage-femme\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">sage-femme</span></span></a>. Le gynécologue prend en charge la <strong>contraception</strong>, le dépistage du cancer du col de l’utérus par le frottis cervico-vaginal, réalisé tous les trois ans, et celui du cancer du sein par la mammographie, avec le concours d’un <a href=\"https://www.elsan.care/fr/patients/radiologie\" title=\"Radiologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">radiologue</span></span></a>. Il prescrit aussi des examens gynécologiques dont des écographies de contrôle.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir sa maternité ?</span></h2><p>La maternité constitue un service associé de la gynécologie. Pour<strong> choisir la maternité la plus adaptée</strong> à ses besoins et à ses désirs, mieux vaut bien s’informer avant même la grossesse. Sécurité optimale, accompagnement personnalisé pour les futures mamans, cocooning, préparation à l’accouchement et à la parentalité, assistante médicale à la procréation (AMP), « papa friendly »… : La Clinique Médico-Chirurgicale le Berçeau innove et améliore sans cesse ses services dans les <strong>34 maternités du groupe, </strong>pour que les parents vivent une belle expérience.</p>",
						"Storage/Images/1665401616984-gynecologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Cardiologie",
						"Cardiologue",
						"La cardiologie s’intéresse à l’appareil cardiovasculaire, c’est-à-dire au cœur et aux vaisseaux (artères et veines), à la prévention ainsi qu’au traitement des anomalies et des maladies qui l’affectent.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Cardiologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que la cardiologie ?</span></h2><p>La cardiologie s’intéresse à l’<strong>appareil cardiovasculaire</strong>, c’est-à-dire au cœur et aux vaisseaux (artères et veines), à la prévention ainsi qu’au traitement des anomalies et des maladies qui l’affectent : hypertension artérielle, insuffisance cardiaque, troubles du rythme cardiaque, angine de poitrine, athérosclérose … Le cardiologue peut être amené à intervenir en urgence notamment en cas d’infarctus du myocarde.</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909120/inline-images/cardiologie-%28personnalise%29.jpg\" alt=\"Cardiologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un cardiologue ?</span></h2><p>Hormis en cas d’urgence, c’est au <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"Médecine générale\"><strong><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">médecin traitant</span></span></strong></a><strong> d’orienter son patient vers un cardiologue</strong> s’il le juge nécessaire. En cardiologie, la prévention est primordiale, a fortiori pour les personnes à risques : diabétiques, patients en surpoids, hypertendus, hypercholestérolémiques, fumeurs, etc. Aujourd’hui, les maladies cardiovasculaires restent la première cause de décès en France chez les femmes devant les tumeurs.</p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le cardiologue ?</span></h2><p>Le cardiologue interroge le patient sur ses habitudes de vie et ses antécédents médicaux et familiaux. Il procède à une <strong>auscultation cardiaque et pulmonaire</strong>, mesure la tension artérielle. Plus les facteurs de risques sont nombreux, plus l’examen est poussé. Pour visualiser le rythme cardiaque et déceler une éventuelle anomalie, il peut effectuer un <strong>électrocardiogramme</strong>. Un test d’effort peut également être pratiqué. Si besoin, il préconise des examens complémentaires : échographie cardiaque et/ou vasculaire, coronarographie, scintigraphie ou IRM.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passe le traitement cardiologique ?</span></h2><p>La prise en charge des <strong>problèmes cardio-vasculaires </strong>passe généralement, en premier lieu, par une amélioration de l’hygiène de vie (alimentation, activité physique, diminution du stress). La prise en charge médicale peut être, quant à elle, médicamenteuse ou chirurgicale.</p><p>En plein essor, la cardiologie interventionnelle est une forme particulière de chirurgie. Réalisée en urgence ou non, la <strong>cardiologie interventionnelle</strong> permet de traiter certaines affections (pathologies coronaires et valvulaires, malformations...) par voie endovasculaire, c’est-à-dire en passant des instruments chirurgicaux miniaturisés à l’intérieur d’une artère ou d’une veine. En cas de prise en charge en urgence, le patient peut être admis au service de soins intensifs cardiologiques.</p><p>La rythmologie est également une sur spécialité en cardiologie qui prend en charge les troubles du rythme cardiaque par plusieurs techniques :</p><ul><li><p>Mise en place de stimulateurs et de défi brillateurs cardiaques, les premiers permettant d’éviter un ralentissement excessif du cœur, et les seconds les troubles du rythme rapides, mettant la vie du patient en danger.</p></li><li><p>Ablation par radiofréquence des troubles du rythme rapides qui consiste à explorer l’activité électrique du cœur par des cathéters pour repérer le tissu cardiaque responsable des troubles du rythme. On délivre ensuite un courant électrique de radiofréquence sur ce tissu afin de rétablir le rythme normal du cœur.</p></li></ul>",
						"Storage/Images/1665401734101-cardiologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Urologie",
						"Urologue",
						"Spécialité médico-chirurgicale, l’urologie prend en charge les affections de l’appareil urinaire de la femme et de l’homme (vessie, uretère, urètre).",
						"<h1><strong><span style=\"color:deepskyblue;\">Qu’est-ce que l’urologie ?</span></strong></h1><p>Spécialité médico-chirurgicale, l’urologie prend en charge les affections de l’appareil urinaire de la femme et de l’homme (vessie, uretère, urètre). Elle couvre aussi l’appareil génital et reproducteur masculin (prostate, pénis, testicules).</p><p>                                                                         <img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909205/inline-images/urologie-%28personnalise%29.jpg\" alt=\"Urologie\"><br></p><h1><strong><span style=\"color:deepskyblue;\">Quand consulter un urologue ?</span></strong></h1><p>Il est recommandé de prendre d’abord rendez-vous chez son médecin généraliste. C’est lui qui oriente vers une&nbsp;consultation chez un urologue&nbsp;lors notamment :</p><ul><li><p>d’infections urinaires à répétition,</p></li><li><p>de fuites urinaires,</p></li><li><p>de traces de sang dans les urines,</p></li><li><p>de douleurs au niveau des testicules</p></li><li><p>de dysfonctionnements de la prostate ou encore de troubles de l’érection: des affections intimes dont les patients peuvent s’ouvrir à ce spécialiste sans tabou.</p></li></ul><p>C’est lui également qui propose un&nbsp;dépistage du cancer de la prostate&nbsp;à partir de 50 ans. Il existe aussi des&nbsp;urologues pour les enfants spécialisés, par exemple, dans les malformations ou l’énurésie (incontinence nocturne).</p><h1><strong><span style=\"color:deepskyblue;\">Que soigne un chirurgien urologue ?</span></strong></h1><p>L’urologue soigne de multiples&nbsp;affections en rapport avec l’appareil urinaire&nbsp;: rétention urinaire ou incontinence,</p><ul><li><p>infections,</p></li><li><p>lésions,</p></li><li><p>malformations</p></li><li><p>cancers de l’appareil urinaire</p></li><li><p>etc..</p></li></ul><p>Le&nbsp;médecin urologue&nbsp;traite aussi l’infertilité masculine, les troubles de l’érection et de l’éjaculation, les cancers des testicules, de la prostate… S’il peut prendre en charge des pathologies sans avoir recours à la chirurgie (colique néphrétique entraînant des calculs rénaux notamment), de nombreuses maladies de cette spécialité sont traitées par voie chirurgicale.</p><h1><strong><span style=\"color:deepskyblue;\">Comment se passent le diagnostic et les chirurgies en urologie ?</span></strong></h1><p>Pour émettre un&nbsp;diagnostic, l’urologue interroge son patient sur ses symptômes et ses antécédents médicaux et familiaux avant de procéder à un examen clinique et, en fonction des troubles, à un examen du périnée et/ou à un toucher rectal. Il peut aussi prescrire des&nbsp;examens complémentaires&nbsp;: ECBU (examen cytobactériologique des urines), échographie, endoscopie, radiographie, bilan urodynamique, cystoscopie, etc. S’appuyant sur des techniques aujourd’hui moins invasives (cœlioscopie, ultrasons, hyperthermie, laser …) grâce à des équipements innovants présents dans la Clinique Médico-Chirurgicale le Berçeau, la&nbsp;chirurgie en urologie&nbsp;a beaucoup progressé ces dernières années, et les interventions sont de plus en plus souvent réalisées en ambulatoire.</p> ",
						"Storage/Images/1665401826095-urologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Endocrinologie -  Diabétologie",
						"Diabétologue",
						"Le service d'endocrinologie propose à ses patients une offre personnalisée de soins humains, de qualité et innovants.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Endocrinologie - Diabétologie</span></strong></h1><p><span style=\"color:rgb(0, 0, 0);\">Le service d'endocrinologie propose à ses patients une offre personnalisée de soins humains, de qualité et innovants. Découvrez ci-dessous les informations essentielles sur le traitement, la consultation ou la prise de rendez-vous avec un endocrinologue de la Clinique du Pont de Chaume.</span></p><h3><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que l’endocrinologie ?</span></h3><p>L’endocrinologie est la <strong>spécialité médicale</strong> s’intéressant aux hormones, à leurs effets sur le fonctionnement du corps – le métabolisme - et aux maladies qui y sont liées. Les hormones sont sécrétées par différentes glandes : hypophyse, thyroïde, glandes surrénales... De nombreuses maladies sont dues à des <strong>dérèglements hormonau</strong>x : diabète de type 1, diabète de type 2, troubles de la croissance, <strong>hypothyroïdie</strong>, hyperthyroïdie, troubles du poids…<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909149/inline-images/endocrino-diabete-%28personnalise%29.jpg\" alt=\"Endocrinologie\"></p><h3><span style=\"color:rgb(127, 164, 198);\">Comment prendre rendez-vous avec un endocrinologue ?</span></h3><p>Avant de <strong>prendre rendez-vous avec un endocrinologue</strong> et pour bénéficier d’un remboursement normal des soins de santé, il est nécessaire de consulter son médecin traitant qui jugera de l’utilité de réorienter ou non vers un endocrinologue.</p><h3><span style=\"color:rgb(127, 164, 198);\">Que fait un endocrinologue lors d’une consultation ?</span></h3><p>Lors d’une première visite, l’endocrinologue interroge le patient sur ses symptômes : perte ou prise de poids inexpliquée, fatigue, douleurs, troubles de l’humeur... Il est recommandé de se munir des résultats d’examens (analyses de sang, examens radiologiques...) et comptes-rendus médicaux dont on dispose pour éviter la répétition d’examens inutiles et faciliter l’<strong>établissement du diagnostic</strong>. Le cas échéant, l’endocrinologue peut demander des examens complémentaires : analyses de sang particulières, imagerie, ponctions, etc.</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment se passent les traitements en endocrinologie ?</span></h3><p>Le traitement dépend du type de dérèglement hormonal. A titre d’exemple, un déficit hormonal, comme dans le cas d’une hypothyroïdie, peut se traiter par un apport quotidien en <strong>hormones thyroïdiennes</strong> de substitution. L’hyperthyroïdie peut, quant à elle, se traiter à l’aide de médicaments ou nécessiter une intervention chirurgicale.</p><h3><span style=\"color:rgb(127, 164, 198);\">Comment traiter les différents types de diabète ?</span></h3><p>Il existe trois types principaux de diabète :</p><ul><li><p><strong>le diabète de type 1</strong>, apparaissant dès l’enfance ;</p></li><li><p><strong>le diabète gestationnel</strong>, apparaissant lors d’une grossesse et disparaissant souvent après l’accouchement ;</p></li><li><p><strong>le diabète de type 2</strong>, le plus courant, apparaissant après 40 ans.</p></li></ul><p>Le diabète est lié à un défaut d’effet d’une hormone appelée insuline. La prise en charge impose une adaptation de l’alimentation. Si elle ne suffit pas à normaliser le taux de sucre dans le sang – la glycémie – des médicaments ou l’injection régulière d’insuline peut être nécessaires.</p><p>La Clinique Médico-Chirurgicale le Berçeau prend en charge ces pathologies, en utilisant les nouvelles technologies de mesure du glucose en continu : holter glycémique, pompe sous-cutanée à insuline...</p>",
						"Storage/Images/1665402280755-diabetologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Rhumatologie",
						"Rhumatologue",
						"La rhumatologie est une spécialité médicale qui s’intéresse au fonctionnement de l’appareil locomoteur (squelette, muscles, articulations) et à ses dysfonctionnements.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Rhumatologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que la rhumatologie ?</span></h2><p>La rhumatologie est une <strong>spécialité médicale</strong> qui s’intéresse au fonctionnement de l’appareil locomoteur (squelette, muscles, articulations) et à ses dysfonctionnements.<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909200/inline-images/rhumatologie-%28personnalise%29.jpg\" alt=\"Rhumatologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un médecin rhumatologue ?</span></h2><p>Votre <a href=\"https://www.elsan.care/fr/patients/medecine-generale\" title=\"Médecine générale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">médecin traitant</span></span></a> vous orientera vers un rhumatologue si vous souffrez de douleurs osseuses, articulaires ou musculaires inexpliquées ou d’une gêne fonctionnelle (mouvement limité, blocage articulaire).</p><h2><span style=\"color:rgb(127, 164, 198);\">Que soigne la rhumatologie ?</span></h2><p>Le rhumatologue prend en charge</p><ul><li><p>Des affections osseuses (fractures, ostéoporose, malformations…),</p></li><li><p>Des <strong>douleurs articulaires</strong> (arthrose, polyarthrite rhumatoïde…)</p></li><li><p>Des douleurs péri-articulaires (tendinites, tennis elbow…)</p></li><li><p>Des affections nerveuses (sciatique, syndrome du canal carpien, lombalgies…)</p></li></ul><p>Outre des facteurs génétiques, les seniors, les sportifs, certains corps de métier – bâtiment, agriculture, couture – et les personnes en surpoids sont plus particulièrement touchés par ces <strong>pathologies</strong>. La population féminine de plus de 50 ans est, elle-aussi, très exposée du fait de la fragilisation osseuse induite par la <strong>ménopause</strong>. Quantifiable par un examen (l’ostéodensitométrie), la perte de résistance osseuse constitue la cause première de <strong>l’arthrose </strong>et de <strong>l’ostéoporose</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passe un traitement en rhumatologie ?</span></h2><p>Le terme générique de « <strong>rhumatismes </strong>» couvre en fait plus d’une centaine d’affections, plus ou moins aiguës, touchant l’ensemble de l’appareil locomoteur. Ils se manifestent par des douleurs, des gonflements et/ou des raideurs articulaires. La <strong>consultation d’un rhumatologue</strong> constitue un moment-clé pour définir un <strong>protocole thérapeutique</strong> adéquat et personnalisé. Le traitement de la douleur s’opère de façon graduelle. Il peut passer par des traitements locaux (crèmes, gels et patches, orthèses de soulagement, infiltrations, stimulations électriques…) ou généraux, par injection ou par voie orale. Le recours à la chirurgie par un <a href=\"https://www.elsan.care/fr/patients/chirurgie-orthopedique-et-traumatologique\" title=\"Chirurgie orthopédique et traumatologique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">chirurgien orthopédiste</span></span></a>, notamment la pose d’une prothèse (genou et hanche, le plus souvent) intervient dans les cas très invalidants. Le maintien d’une activité adaptée est recommandé pour préserver la masse musculaire et le <strong>fonctionnement articulaire</strong>.</p>",
						"Storage/Images/1665402507909-rhumatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Chirurgie",
						"Chirurgien",
						"La chirurgie générale s’occupe de diagnostiquer et de traiter diverses affections. Son champs d’intervention est vaste et inclut notamment.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Chirurgie générale</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la chirurgie générale ?</span></h2><p>La chirurgie générale s’occupe de diagnostiquer et de traiter diverses affections. Son champs d’intervention est vaste et inclut notamment&nbsp;:&nbsp;</p><ul><li><p>Chirurgie d’urgence</p></li><li><p>Appendicite</p></li><li><p>Calculs</p></li><li><p>traumatologie</p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le chirurgien général ?</span></h2><p>Le chirurgien général pratique des&nbsp;<strong>interventions chirurgicales</strong>&nbsp;variées afin de traiter divers systèmes du corps humain, notamment&nbsp;:</p><ul><li><p>les organes abdominaux</p></li><li><p>le thorax</p></li><li><p>les glandes endocrines</p></li><li><p>la peau et les tissus mous</p></li><li><p>les artères et les veines</p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un spécialiste en chirurgie viscérale et digestive ?</span></h2><p>L’orientation vers un&nbsp;<strong>chirurgien général&nbsp;</strong>repose sur l’avis du médecin traitant et/ou du spécialiste suivant l’affection dont le patient souffre : hépato-gastro-entérologue,&nbsp;<a href=\"https://www.elsan.care/fr/patients/oncologie-medicale%22%20%5Co%20%22Oncologie%20m%C3%A9dicale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">cancérologue</span></span></a>, proctologue,&nbsp;<a href=\"https://www.elsan.care/fr/patients/endocrinologie%22%20%5Co%20%22Endocrinologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">endocrinologue</span></span></a>,&nbsp;<a href=\"https://www.elsan.care/fr/patients/gynecologie%22%20%5Co%20%22Gyn%C3%A9cologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">gynécologue</span></span></a>,&nbsp;<a href=\"https://www.elsan.care/fr/patients/urologie%22%20%5Co%20%22Urologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">urologue&nbsp;</span></span></a>...</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment choisir son chirurgien général ?</span></h2><p>Cette spécialité de chirurgie générale fait appel à des gestes très techniques, nécessitant donc des&nbsp;<strong>praticiens expérimentés</strong>&nbsp;réalisant un nombre important d’interventions et disposant d’un matériel de pointe : chirurgie robot-assistée, matériel d’imagerie au bloc… Il est important de choisir, lorsque c’est possible, un centre de soins disposant des&nbsp;<strong>équipements les plus récents</strong>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quels sont les risques d'une opération chirurgicale de la cavité abdominale ou thoracique&nbsp;?</span></h2><p>Il s’agit le plus souvent d’interventions sous anesthésie générale. Les risques liés à l’intervention (séquelles musculaires et/ou neurologiques, hémorragie, infection…) à&nbsp;<a href=\"https://www.elsan.care/fr/patients/anesthesie-et-reanimation%22%20%5Co%20%22Anesth%C3%A9sie%20et%20r%C3%A9animation\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l’anesthésie&nbsp;</span></span></a>et à l’état de santé du patient sont réels. C’est la raison pour laquelle le chirurgien, en lien avec l’anesthésiste, évalue ces risques, décide du bien-fondé de l’opération et des modalités d’intervention les plus adaptées.</p>",
						"Storage/Images/1665402814577-chirurgie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Dermatologie",
						"Dermatologue",
						"La dermatologie est la spécialité médicale qui s’intéresse à l’étude de la peau, des cheveux, des poils et des ongles ainsi qu’à la prévention, au diagnostic et au traitement des maladies qui les touchent.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Dermatologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu’est-ce que la dermatologie ?</span></h2><p>La dermatologie est la spécialité médicale qui s’intéresse à l’étude de la peau, des cheveux, des poils et des ongles ainsi qu’à la prévention, au diagnostic et au traitement des maladies qui les touchent. Très variées, ces maladies concernent tous les âges de la vie.</p><p>La dermatologie s’intéresse aussi à l’aspect de la peau et à son amélioration (élimination des rides, épilation…). On parle alors de <a href=\"https://www.elsan.care/fr/patients/chirurgie-plastique-reconstructrice-et-esthetique\" title=\"Chirurgie plastique reconstructrice et esthétique\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">dermatologie esthétique et cosmétique</span></span></a>. Dans ce secteur, la clinique Médico-Chirurgicale le Berçeau propose aussi des soins de qualité.<br>&nbsp;</p><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909146/inline-images/dermatologie-%28personnalise%29.jpg\" alt=\"Dermatologie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un dermatologue ?</span></h2><p>Dès que la peau prend un aspect inhabituel – apparition d’une protubérance, de rougeurs ou de démangeaisons, augmentation de la taille d’un grain de beauté, perte de cheveux… –&nbsp;il est conseillé de consulter son médecin traitant qui, s’il le juge utile, vous réorientera vers un <strong>médecin dermatologue</strong>.</p><p>En cas d’<strong>antécédents de maladies de peau</strong>, personnels ou familiaux, ou d’exposition à des facteurs de risque (exposition importante au soleil, à des produits chimiques, à un milieu humide…), il peut être utile d’être suivi régulièrement par un dermatologue. Acné, eczéma, <strong>psoriasis</strong>, verrue, herpès, zona, kyste cutané, hyperpigmentation, mélanome… : les pathologies de la peau, des plus bénignes aux plus graves, sont prises en charge dans les <a href=\"https://www.elsan.care/fr/groupe\" title=\"Le Groupe\"><strong><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">services de dermatologie</span></span></strong></a> des <a href=\"https://www.elsan.care/fr/groupe\" title=\"Le Groupe\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">Clinique Médico-Chirurgicale le Berçeau</span></span></a>.</p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le dermatologue ?</span></h2><p>Le dermatologue interroge le patient sur la nature du problème, sur ses antécédents médicaux et familiaux et son hygiène de vie. Il procède à l’auscultation de la zone touchée par le problème (lésion, grain de beauté…) mais aussi d’autres zones où il peut être présent sans être encore visible. Il peut réaliser une <strong>dermoscopie</strong>, examen indolore, pour visualiser la peau en profondeur, ou une <strong>biopsie</strong> <strong>cutanée</strong> pour préciser la nature du problème (<a href=\"https://www.elsan.care/fr/centre-cancerologie-dentellieres/nos-actualites/la-prevention-des-cancers-cutanes-au-centre-de\" target=\"_blank\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">mélanome ou autre pathologie</span></span></a>).</p><h2><span style=\"color:rgb(127, 164, 198);\">Comment se passent les traitements dermatologiques ?</span></h2><p>En plus des médicaments administrés localement ou par voie orale, le dermatologue dispose de différentes techniques de traitement comme :</p><ul><li><p>L’élimination de lésions (verrues, grains de beauté…) ou de défauts cutanés par peeling, azote liquide, exérèse chirurgicale, laser…&nbsp;</p></li><li><p>La photothérapie contre le psoriasis,</p></li><li><p>Les injections locales de produits de comblement ou de toxine botulique (Botox) contre les rides.</p></li><li><p>Photothérapie contre le psoriasis</p></li></ul>",
						"Storage/Images/1665402996459-dermatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Traumatologie",
						"Traumatologue",
						"Le service de chirurgie orthopédique et traumatologie propose à ses patients une offre personnalisée de soins humains, de qualité et innovants.",
						"<h1 style=\"text-align:center\"><strong><span style=\"color:rgb(0, 0, 98);\"><span style=\"background-color:rgb(255, 255, 255);\">TRAUMATOLOGIE : ÉTUDE DES TRAUMATISMES</span></span></strong></h1><p><span style=\"color:rgb(0, 0, 98);\"><span style=\"background-color:rgb(255, 255, 255);\"><img src=\"https://www.concilio.com/wp-content/uploads/Orthop%C3%A9die-proth%C3%A8se-hanches-genou-chirurgien-Concilio_718x452.jpg?x41967\" alt=\"Concilio - Traumatologie : étude des traumatismes\"></span></span></p><h2><span style=\"color:rgb(0, 0, 98) !important;\"><span style=\"background-color:rgb(245, 245, 245);\">LE SAVIEZ-VOUS ?</span></span></h2><p><span style=\"background-color:rgb(245, 245, 245);\">Au Cameroun, une grande majorité de la population doit faire face un jour à un problème orthopédique. Ainsi 150 000 prothèses de hanche et 100 000 de genou sont posées par an.</span></p><p><span style=\"background-color:rgb(245, 245, 245);\">Pour mettre toutes les chances de votre côté face à la maladie, l’équipe médicale de la CMCB vous accompagne personnellement.</span></p><h2><span style=\"color:rgb(0, 0, 98) !important;\"><span style=\"background-color:rgb(255, 255, 255);\">QU'EST-CE QUE LA TRAUMATOLOGIE ?</span></span></h2><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">La traumatologie est la discipline étudiant les traumatismes physiques regroupant l’ensemble des chocs subits par un sujet. Ils incluent la brûlure, l’accident de voiture, la chute, l’</span><a href=\"https://www.concilio.com/orthopedie-entorse-de-la-cheville\"><span style=\"color:rgb(190, 0, 135) !important;\"><span style=\"background-color:transparent;\">entorse</span></span></a>, etc. On distingue la traumatologie sportive, des lésions étroitement liées à la pratique du sport, la traumatologie routière, etc.</p><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les autres facteurs favorables aux traumatismes incluent entre autres&nbsp;:</span></p><ul><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les agressions</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">La noyade</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les complications de soins médicaux et chirurgicaux</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les accidents atteignant la respiration</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les lésions</span></p></li><li><p style=\"text-align:justify\"><span style=\"background-color:rgb(255, 255, 255);\">Les faits de guerre</span></p></li></ul>",
						"Storage/Images/1665403275772-Traumatologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Hepato-Gastro-Enterologie",
						"Gastro-Entérologue",
						"L’hépato-gastro-entérologie est la spécialité médicale qui s’intéresse aux organes de la digestion, leurs fonctionnements, leurs maladies et les moyens de les soigner.",
						"<h1><strong><span style=\"color:inherit;\">Gastro-entérologie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Qu'est-ce que la gastro-entérologie ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">L’hépato-gastro-entérologie est la spécialité médicale qui s’intéresse aux organes de la digestion, leurs fonctionnements, leurs maladies et les moyens de les soigner. Les organes constituant le système digestif sont :</span></span></p><ul><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le tube digestif (œsophage, estomac, intestins, le colon et le rectum)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le foie</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">le pancréas</span></span></p></li></ul><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\"><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909152/inline-images/gastroenterologie-%28personnalise%29.jpg\" alt=\"Hépato-gastro-entérologie\"></span></span></p><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Que fait le gastro-entérologue ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">Il dépiste, diagnostique et traite des maladies du système digestif aussi variées que :</span></span></p><ul><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies inflammatoires de l’intestin (rectocolite hémorragie, Maladie de Crohn…)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies du foie et des voies biliaires (calculs, tumeurs…)</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les hépatites</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les hémorragies digestives</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les maladies du pancréas</span></span></p></li><li><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">les cancers digestifs</span></span></p></li></ul><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Quand consulter un spécialiste en gastro-entérologie ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">La consultation d’un hépato-gastro-entérologue a lieu à la demande du médecin traitant ou d’un autre spécialiste. Cette réorientation permet de faire réaliser et/ou interpréter des examens pour établir un diagnostic, mettre en place un traitement, réaliser un&nbsp;geste technique particulier.</span></span></p><h2><span style=\"color:rgb(127, 164, 198);\"><span style=\"background-color:rgb(255, 255, 255);\">Comment choisir un gastro-entérologue ?</span></span></h2><p><span style=\"color:rgb(0, 0, 0);\"><span style=\"background-color:rgb(255, 255, 255);\">L’hépato-gastro-entérologie fait appel à des techniques, notamment d’imageries, particulières. Le choix de ce spécialiste sera donc fait en concertation avec le médecin traitant qui connait les établissements les mieux équipés et les praticiens avec lesquels il travaille en confiance.</span></span></p>",
						"Storage/Images/1665403549033-Hépato-gastro-entérologie.jpeg",
						(short) 0,
						new Date(),
						new Date(),
						list),
				new SpecialityModel(
						"Neurochirurgie",
						"Neurochirurgien",
						"La neurochirurgie est la spécialité chirurgicale dont le domaine d’expertise est le diagnostic et la prise en charge chirurgicale des troubles du système nerveux.",
						"<h1><strong><span style=\"color:rgb(0, 0, 0);\">Neurochirurgie</span></strong></h1><h2><span style=\"color:rgb(127, 164, 198);\">Qu'est-ce que la neurochirurgie ?</span></h2><p>La neurochirurgie est la spécialité chirurgicale dont le domaine d’expertise est le diagnostic et la prise en charge chirurgicale des troubles du système nerveux. Le système nerveux se divise en trois grands segments :</p><ul><li><p>le système nerveux central (cerveau, moëlle épinière)</p></li><li><p>le système nerveux périphérique (nerfs qui vont du système nerveux central vers le reste du corps)</p></li><li><p>les systèmes nerveux végétatifs ou « autonomes », assurant le fonctionnement d’organes de manière automatique (innervation de l’intestin, du muscle cardiaque…)</p></li></ul><p>Le champ d’intervention est particulièrement large et les <strong>neurochirurgiens </strong>sont fréquemment spécialisés dans un <strong>type particulier de neurochirurgie</strong> :</p><ul><li><p>neurochirurgie crânienne</p></li><li><p>neurochirurgie du rachis</p></li><li><p>neurochirurgie pédiatrique…</p></li></ul><p><img src=\"https://res.cloudinary.com/void-elsan/image/upload/v1652909170/inline-images/neurochirurgie-%28personnalise%29.jpg\" alt=\"Neurochirurgie\"></p><h2><span style=\"color:rgb(127, 164, 198);\">Que fait le neurochirurgien ?</span></h2><p>Le neurochirurgien intervient au sein d’une équipe chirurgicale, en coopération particulièrement étroite avec <a href=\"https://www.elsan.care/fr/patients/anesthesie-et-reanimation\" title=\"Anesthésie et réanimation\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">l’anesthésiste</span></span></a>. Il traite des maladies aussi variées que la hernie discale, la maladie de Parkinson, l’hyperacousie, l’anévrisme ou le traumatisme intracrânien, l’hématome cérébral, les compressions médullaires parfois responsables de douleurs chroniques (lombalgies, sciatiques…) et l’ensemble des tumeurs pouvant affecter les systèmes nerveux.</p><p>Avant l’intervention il réalise un <strong>bilan préopératoire</strong> pour déterminer le type d’opération à réaliser puis l’opération et assure un suivi <strong>post-opératoire</strong> pour s’assurer de l’efficacité de l’intervention et de la bonne rémission du patient.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quand consulter un neurochirurgien ?</span></h2><p>Le neurochirurgien intervient en coordination avec d’autres spécialistes comme le <a href=\"https://www.elsan.care/fr/patients/neurologie\" title=\"Neurologie\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">neurologue </span></span></a>ou le <a href=\"https://www.elsan.care/fr/patients/oncologie-medicale\" title=\"Oncologie médicale\"><span style=\"color:rgb(249, 176, 4);\"><span style=\"background-color:transparent;\">cancérologue</span></span></a>. Hormis dans le cadre d’une urgence, le choix d’une intervention repose donc le plus souvent sur une décision collégiale de l’ensemble des spécialistes suivant un patient pour une affection donnée.</p><h2><span style=\"color:rgb(127, 164, 198);\">Quels sont les risques lors de l’intervention d’un neurochirurgien ?</span></h2><p>Toute intervention chirurgicale, a fortiori sous anesthésie générale, présente des risques : hémorragie, infection, séquelles opératoires (séquelles neurologiques, douleurs…). Ils sont directement liés au type d’intervention et à l’état de santé du patient. Le neurochirurgien doit en faire une description précise lors du <strong>bilan préopératoire</strong>.</p>",
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
