package com.ibrasoft.storeStackProd.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.exceptions.NotFoundException;
import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.response.UserPasswordChangeModel;
import com.ibrasoft.storeStackProd.service.UserService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser() {
		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) throws ClinicException {
		if (userService.findByUserMAil(user.getEmail()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.EMAIL_ALREADY_EXIST);
		} else if (userService.findByUserName(user.getUsername()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_ALREADY_EXIST);
		}
		User userCreated = userService.createOrUpdateUser(user);
		return new ResponseEntity<>(userCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody User user) throws ClinicException {
		User userByMail = userService.findByUserMAil(user.getEmail());
		User userByUserName = userService.findByUserName(user.getUsername());
		if (userByMail != null) {
			if (userByMail.getId() != user.getId())
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.EMAIL_ALREADY_EXIST);
		} else if (userByUserName != null) {
			if (userByUserName.getId() != user.getId())
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_ALREADY_EXIST);
		}
		User userUpdated = userService.createOrUpdateUser(user);
		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deleteUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId)
			throws ClinicException {
		User userDeleted = userService.findUserById(userId);
		if (userDeleted != null) {
			return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findUserById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findUserById(@PathVariable("userId") int userId) throws ClinicException {
		User userFound = userService.findUserById(userId);
		return new ResponseEntity<>(userFound, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateUserPassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserPassword(@RequestBody UserPasswordChangeModel user)
			throws ClinicException {
		User userChanged = userService.updateUserPassword(user.getUserId(), user.getOldPassword(),
				user.getNewPassword());
		if (userChanged != null) {
			return new ResponseEntity<>(userChanged, HttpStatus.OK);
		} else {
			throw new ClinicException(Constants.INVALID_INPUT, Constants.OLD_PASSWORD_NOT_MATCH);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rememberUserPassword/{userMail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rememberUserPassword(@PathVariable("userMail") String userMail)
			throws ClinicException {
		User userFound = userService.findByUserMAil(userMail);
		if (userFound == null) {
			throw new ClinicException(Constants.INVALID_INPUT, Constants.EMAIL_IS_NOT_CORRECT);
		} else {
			String generatedPassword = Utils.generateRandomUserPassword();
			MimeMessage message = sender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message,
						MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
				Map<String, Object> model = new HashMap<>();
				model.put("email", userFound.getEmail());
				model.put("userName", userFound.getUsername());
				model.put("message", "Votre mot de passe est: " + generatedPassword
						+ ".                            Vous pouvez vous connecter pour le changer.");

				Context context = new Context();
				context.setVariable("model", model);
				String html = templateEngine.process("rememberUserPassword", context);

				helper.setTo(userMail);
				helper.setText(html, true);
				helper.setSubject("Rappel de mot de passe");
			} catch (MessagingException e) {
				// TODO: handle exception
				//
				e.printStackTrace();
			}
			sender.send(message);
			userFound.setPassword(passwordEncoder.encode(generatedPassword));
			userService.createOrUpdateUser(userFound);
			return new ResponseEntity<>(userFound, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllArchivedUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllArchivedUser() {
		return new ResponseEntity<>(userService.getAllArchivedUser(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/createAdminOrSpecialist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAdminOrSpecialist(
			@RequestBody User user,
			@RequestParam(value = "admin", required = true) Boolean admin,
			@RequestParam(value = "specialist", required = true) Boolean specialist) throws ClinicException {
		if (userService.findByUserMAil(user.getEmail()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.EMAIL_ALREADY_EXIST);
		} else if (userService.findByUserName(user.getUsername()) != null) {
			throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_ALREADY_EXIST);
		}
		User userCreated = userService.createAdminOrSpecialist(user, admin, specialist);
		return new ResponseEntity<>(userCreated, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateAdminOrSpecialist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAdminOrSpecialistByAdmin(
			@RequestBody User user) throws ClinicException {
		User userByMail = userService.findByUserMAil(user.getEmail());
		User userByUserName = userService.findByUserName(user.getUsername());
		if (userByMail != null) {
			if (userByMail.getId() != user.getId())
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.EMAIL_ALREADY_EXIST);
		} else if (userByUserName != null) {
			if (userByUserName.getId() != user.getId())
				throw new ClinicException(Constants.ITEM_ALREADY_EXIST, Constants.USERNAME_ALREADY_EXIST);
		}
		User update = userService.updateAdminOrSpecialistByAdmin(user);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateImage/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateImage(@PathVariable("userId") Integer userId, @RequestBody String profileImage) {
		Boolean update = userService.updateImage(userId, profileImage);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateUserStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserStatus(@RequestBody User user)
			throws ClinicException {
		if (userService.findUserById(user.getId()) == null) {
			throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.USER_NOT_FOUND);
		}
		Boolean update = userService.updateUserStatus(user);
		return new ResponseEntity<>(update, HttpStatus.OK);
	}

}
