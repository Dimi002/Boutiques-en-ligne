package com.ibrasoft.storeStackProd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ibrasoft.storeStackProd.beans.User;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.mail.MailMail;
import com.ibrasoft.storeStackProd.sms.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.Planing;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.models.CountDoctorDashbord;
import com.ibrasoft.storeStackProd.models.EnumStatus;
import com.ibrasoft.storeStackProd.repository.AppointmentRepository;
import com.ibrasoft.storeStackProd.repository.PlaningRepository;
import com.ibrasoft.storeStackProd.repository.SpecialistRepository;
import com.ibrasoft.storeStackProd.service.AppointmentService;
import com.ibrasoft.storeStackProd.service.SpecialistService;
import com.ibrasoft.storeStackProd.util.Constants;
import com.ibrasoft.storeStackProd.util.*;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

  @Autowired
  AppointmentRepository appointRepo;
  @Autowired
  SpecialistService specialistService;
  @Autowired
  SpecialistRepository specialistRepository;
  @Autowired
  MailMail mail;
  @Autowired
  SmsService smsService;
  @Autowired
  PlaningRepository planingRepository;

  @Override
  public Appointment createOrUpdateAppointment(Appointment appoint) throws ClinicException {
    List<Appointment> appointments = appointRepo.findSpecialityByIdAndAppointmentDate(appoint.getSpecialistId(),
        appoint.getAppointmentDate(), EnumStatus.ACCEPTED.getStatus(), appoint.getAppointmentHour());
    Optional<Specialist> specialistFound = specialistRepository.findById(appoint.getSpecialistId().getSpecialistId());
    List<Planing> planings = new ArrayList<Planing>();

    if (appoint.getPatientName() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_NAME_IS_REQUIRED);
    } else if (appoint.getPatientEmail() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_EMAIL_IS_REQUIRED);
    } else if (appoint.getPatientPhone() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_PHONE_IS_REQUIRED);
    } else if (appoint.getOriginalAppointmentHour() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED);
    } else if (appoint.getAppointmentDate() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.APPOINTMENT_DATE_IS_REQUIRED);
    } else if (appoint.getAppointmentHour() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.APPOINTMENT_HOUR_IS_REQUIRED);
    } else if (appoint.getPatientMessage() == null) {
      throw new ClinicException(Constants.ITEM_IS_REQUIRED, Constants.PATIENT_MESSAGE_IS_REQUIRED);
    } else if (!Utils.matchWithPattern(appoint.getOriginalAppointmentHour())) {
      throw new ClinicException(Constants.INVALID_INPUT, Constants.HOUR_PATTERN_NOT_MATCHES);
    }

    if (appointments.isEmpty()) {
      if (appoint.getAppointmentId() != null) {
        appoint.setLastUpdateOn(new Date());
      } else {
        if (specialistService.getActiveSpecialist().size() != 0) {
          if (appoint.getSpecialistId() != null && specialistFound.isPresent() &&
              specialistFound.get().getStatus() == Constants.STATE_ACTIVATED) {
            Integer planDay = Utils.getDayoFWeekAsNumber(appoint.getAppointmentDate()) - 1;
            float hour = Utils.fromHourToInt(appoint.getOriginalAppointmentHour());
            float startTime = 0;
            float endTime = 0;
            Boolean inRange = false;
            planings = planingRepository.findBySpecialistSpecialistIdAndPlanDay(specialistFound.get().getSpecialistId(),
                planDay);
            if (planings != null && !planings.isEmpty()) {
              for (Planing planing : planings) {
                startTime = Utils.fromHourToInt(planing.getStartTime());
                endTime = Utils.fromHourToInt(planing.getEndTime());
                inRange = (hour >= startTime && hour <= endTime);
                if (inRange) {
                  throw new ClinicException(Constants.SLOT_TIME_NOT_FREE, Constants.TIME_NOT_FREE);
                }
              }
            }
            appoint.setLastUpdateOn(new Date());
            appoint.setCreatedOn(new Date());
            appoint.setState(EnumStatus.ACCEPTED.getStatus());
          } else if (appoint.getSpecialistId() != null && specialistFound.isPresent()
              && specialistFound.get().getStatus() == Constants.STATE_DEACTIVATED) {
            throw new ClinicException(Constants.ITEM_ALREADY_DEACTIVATED, Constants.SPECIALIST_ALREADY_DEACTIVATED);
          } else if (appoint.getSpecialistId() != null && specialistFound.isPresent()
              && specialistFound.get().getStatus() == Constants.STATE_DELETED) {
            throw new ClinicException(Constants.ITEM_ALREADY_DELETED, Constants.SPECIALIST_ALREADY_DELETED);
          } else if (!specialistFound.isPresent()) {
            throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.SPECIALIST_NOT_FOUND);
          }
        } else {
          throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.NOT_ACTIVE_SPECIALIST);
        }
      }
      return appointRepo.save(appoint);
    } else {
      throw new ClinicException(Constants.SLOT_TIME_NOT_FREE, Constants.TIME_NOT_FREE);
    }
  }

  public List<Appointment> getAllAppointment() {
    List<Appointment> appointments = new ArrayList<Appointment>();
    appointRepo.getAllAppointment(Constants.STATE_ACTIVATED, Constants.STATE_DEACTIVATED).forEach(appointments::add);
    return appointments;
  }

  public List<Appointment> getActiveAppointment() {
    List<Appointment> appointmentList = new ArrayList<Appointment>();
    appointRepo.getActiveAppointment(Constants.STATE_ACTIVATED).forEach(appointmentList::add);
    return appointmentList;
  }

  public Appointment deleteAppointment(Integer appointId) throws ClinicException {
    Optional<Appointment> apptToDelete = appointRepo.findById(appointId);
    if (apptToDelete.isPresent()) {
      apptToDelete.get().setLastUpdateOn(new Date());
      apptToDelete.get().setStatus(Constants.STATE_DELETED);
    } else {
      throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.APPOINTMENT_NOT_FOUND);
    }
    return appointRepo.save(apptToDelete.get());
  }

  public Appointment findAppointmentById(Integer appointId) throws ClinicException {
    Optional<Appointment> apptFound = appointRepo.findById(appointId);
    if (apptFound.isPresent()) {
      return apptFound.get();
    } else {
      throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.APPOINTMENT_NOT_FOUND);
    }
  }

  public List<Appointment> getAllArchivedAppointment() {
    List<Appointment> appointmentList = new ArrayList<Appointment>();
    appointRepo.getAllArchivedappointment(Constants.STATE_ARCHIVE).forEach(appointmentList::add);
    return appointmentList;
  }

  public Appointment findBySpecialistId(Specialist specialistId) {
    return appointRepo.findBySpecialistId(specialistId);
  }

  @Override
  public List<Appointment> getAllTodayAppointment(Date begin_date, Specialist specialistID, Date end_date) {
    return appointRepo.getAllTodayAppointment(begin_date, specialistID, end_date);
  }

  @Override
  public List<Appointment> getAllSupTodayAppointment(Date begin_date, Specialist specialistID) {
    return appointRepo.getAllSupTodayAppointment(begin_date, specialistID);
  }

  @Override
  public CountDoctorDashbord getCountDoctorDashbord(Specialist specialistID) {
    Date date = new Date();
    date.setHours(00);
    date.setMinutes(00);
    date.setSeconds(00);
    Date date1 = new Date();
    date1.setHours(23);
    date1.setMinutes(59);
    date1.setSeconds(59);
    CountDoctorDashbord countDoctorDashbord = new CountDoctorDashbord();
    countDoctorDashbord.setAllPatient(appointRepo.getCountAllAppointment(specialistID));
    countDoctorDashbord.setAllAceptTodayPatient(
        appointRepo.getCountAllAcceptAppointment(String.valueOf(EnumStatus.ACCEPTED), specialistID, date, date1));
    countDoctorDashbord.setAllCancelTodayPatient(
        appointRepo.getCountAllAcceptAppointment(String.valueOf(EnumStatus.CANCELED), specialistID, date, date1));
    countDoctorDashbord.setAllAceptTodayPatientDate(appointRepo.getDateAllAppointment(specialistID));
    countDoctorDashbord.setAllCancelTodayPatientDate(
        appointRepo.getDateAllAcceptAppointment(String.valueOf(EnumStatus.ACCEPTED), specialistID, date, date1));

    return countDoctorDashbord;
  }

  @Override
  public List<Appointment> getAllAppointmentSpecialist(Specialist specialistID) {
    return appointRepo.getAllAppointmentSpecialist(specialistID.getSpecialistId());
  }

  @Override
  public Appointment updateSpecialistState(Integer appointmentId, String State) throws ClinicException {
    Optional<Appointment> appointment = appointRepo.findById(appointmentId);
    if (appointment.isPresent()) {
      appointment.get().setState(State);
      appointRepo.save(appointment.get());
      this.sendConfirmNotifications(appointment.get(), Constants.N_A_UPDATE);
    } else {
      throw new ClinicException(Constants.ITEM_NOT_FOUND, Constants.APPOINTMENT_NOT_FOUND);
    }
    return appointment.get();
  }

  @Override
  public List<Appointment> getAllAppointments() {
    return appointRepo.getAllAppointments();
  }

  @Override
  public List<Appointment> getAllDistinctPatients() {
    return appointRepo.getAllDistinctPatients();
  }

  @Override
  public void sendConfirmNotifications(Appointment appointment, String action) {
    Specialist specialist = appointment.getSpecialistId();
    User specialistUser = specialist.getUserId();
    String pattern = "EEEEE, dd MMMMM yyyy 'à' ";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
    if (action.equalsIgnoreCase(Constants.N_A_CREATE)) {
      // FOR PATIENT
      // SEND MAIL
      if (!appointment.getPatientEmail().trim().isEmpty()) {
        String message = "Merci pour votre confiance!\n\n Votre rendez-vous planifié pour le  "
            + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
            + ";\n" + "avec le médécin: "
            + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " a été bien prit en compte. \n\n " +
            "Un mail et/ou sms de confirmation du médécin vous sera transmit\n\n"
            + "Coordialement la CMCB: votre clinique !";
        this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: Prise de rendez-vous", message,
            appointment.getPatientEmail());
      }
      // FOR Specialist
      // SEND MAIL
      if (!specialistUser.getEmail().trim().isEmpty()) {
        String message = "Vous avez une demande de rendez-vous planifié pour le  "
            + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
            + ";\n" + "avec le patient: "
            + appointment.getPatientName()
            + ".\n\n Veuillez vous connecter à votre espace administrateur et confirmer\n\n"
            + "Cordialement la CMCB: votre clinique !";
        this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: Nouveau rendez-vous planifié", message,
            specialistUser.getEmail());
      }
      // FOR patient
      // SEND SMS
      if (!appointment.getPatientPhone().trim().isEmpty()) {
        String message = "Merci pour votre confiance!\n\n Votre rendez-vous planifié pour le  "
            + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
            + ";\n" + "avec le médécin: "
            + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " a été bien prit en compte. \n\n " +
            "Un mail et ou sms de confirmation du medécin vous sera transmit\n\n"
            + "Coordialement la CMCB: votre clinique !";
        this.smsService.sendSms(appointment.getPatientPhone(), message);
      }
      // FOR Specialist
      // SEND SMS
      if (!specialistUser.getEmail().trim().isEmpty()) {
        String message = "Vous avez une demande de rendez-vous planifié pour le  "
            + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
            + ";\n" + "avec le patient: "
            + appointment.getPatientName() + ".\n\n veuillez confirmer\n\n" + "Coordialement la CMCB: votre clinique !";
        this.smsService.sendSms(specialistUser.getPhone(), message);
      }
    } else if (action.equalsIgnoreCase(Constants.N_A_UPDATE)) {
      if (appointment.getState().equalsIgnoreCase(EnumStatus.ACCEPTED.getStatus())) {
        // FOR PATIENT
        // SEND MAIL
        if (!appointment.getPatientEmail().trim().isEmpty()) {
          String message = "Merci pour votre patience!\n\n Votre rendez-vous planifié pour le  "
              + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
              + ";\n" + "avec le médécin: "
              + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " a été accepté!\n\n"
              + "Cordialement la CMCB: votre clinique !";
          this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: rendez-vous accepté", message,
              appointment.getPatientEmail());
        }
        // FOR patient
        // SEND SMS
        if (!appointment.getPatientPhone().trim().isEmpty()) {
          String message = "Merci pour votre patience!\n\n Votre rendez-vous planifié pour le  "
              + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
              + ";\n" + "avec le médécin: "
              + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " a été accepté!\n\n"
              + "Cordialement la CMCB: votre clinique !";
          this.smsService.sendSms(appointment.getPatientPhone(), message);
        }
      } else if (appointment.getState().equalsIgnoreCase(EnumStatus.CANCELED.getStatus())) {
        // FOR PATIENT
        // SEND MAIL
        if (!appointment.getPatientEmail().trim().isEmpty()) {
          String message = "Désolé, votre rendez-vous planifié pour le "
              + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
              + ";\n" + "avec le médécin: "
              + specialistUser.getFirstName() + " " + specialistUser.getLastName()
              + " a été annulé par votre médécin pour cause d'indisponibilité! " +
              "\n\nVeuillez s'il vous plaît, planifier un rendez-vous avec un autre de nos médécins.\n\n"
              + "Cordialement la CMCB: votre clinique !";
          this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: Annulation de la prise de rendez-vous",
              message, appointment.getPatientEmail());
        }
        // FOR patient
        // SEND SMS
        if (!appointment.getPatientPhone().trim().isEmpty()) {
          String message = "Désolé, votre rendez-vous planifié pour le "
              + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
              + ";\n" + "avec le médécin: "
              + specialistUser.getFirstName() + " " + specialistUser.getLastName()
              + " a été annulé par votre médécin pour cause d'indisponibilité! " +
              "\n\nVeuillez s'il vous plaît, planifier un rendez-vous avec un autre de nos médécins.\n\n"
              + "Cordialement la CMCB: votre clinique !";
          this.smsService.sendSms(appointment.getPatientPhone(), message);
        }
      }
    } else if (action.equalsIgnoreCase(Constants.N_A_CRON)) {
      // FOR PATIENT
      // SEND MAIL
      if (!appointment.getPatientEmail().trim().isEmpty()) {
        String message = "Merci pour votre confiance!\n\n Votre rendez-vous planifié pour le  "
            + simpleDateFormat.format(appointment.getAppointmentHour()) + appointment.getOriginalAppointmentHour()
            + ";\n" + "avec le médécin: "
            + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " est dans 30min. \n\n " +
            "veuillez prendre des dispositions pour être à l'heure. Merci\n";
        this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: Rappel de rendez-vous planifié", message,
            appointment.getPatientEmail());
      }
      // FOR Specialist
      // SEND MAIL
      if (!specialistUser.getEmail().trim().isEmpty()) {
        String message = "Le rendez-vous planifié pour le  " + simpleDateFormat.format(appointment.getAppointmentHour())
            + appointment.getOriginalAppointmentHour() + "\n" + "avec le patient: "
            + appointment.getPatientName() + " est dans 30min";
        this.mail.sendMailAlert("CLINIQUE MÉDICO-CHIRURGICALE le BERCEAU: Rappel de rendez-vous planifié", message,
            specialistUser.getEmail());
      }
      // FOR patient
      // SEND SMS
      if (!appointment.getPatientPhone().trim().isEmpty()) {
        String message = "Merci pour votre confiance!\n\n Votre rendez-vous planifié pour le  "
            + appointment.getAppointmentDate() + appointment.getOriginalAppointmentHour() + ";\n" + "avec le médécin: "
            + specialistUser.getFirstName() + " " + specialistUser.getLastName() + " est dans 30min. \n\n " +
            "veuillez prendre des dispositions pour être à l'heure. Merci\n";
        this.smsService.sendSms(appointment.getPatientPhone(), message);
      }
      // FOR Specialist
      // SEND SMS
      if (!specialistUser.getEmail().trim().isEmpty()) {
        String message = "Le rendez-vous planifié pour le  " + appointment.getAppointmentDate()
            + appointment.getOriginalAppointmentHour() + "\n"
            + "avec le patient: "
            + appointment.getPatientName() + " est dans 30min";
        this.smsService.sendSms(specialistUser.getPhone(), message);
      }
    }
  }
}
