package com.ibrasoft.storeStackProd.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibrasoft.storeStackProd.beans.Appointment;
import com.ibrasoft.storeStackProd.beans.Specialist;
import com.ibrasoft.storeStackProd.exceptions.ClinicException;
import com.ibrasoft.storeStackProd.models.CountDoctorDashbord;

@Service
public interface AppointmentService {
	public Appointment createOrUpdateAppointment(Appointment appoint)
			throws ClinicException;

	public List<Appointment> getAllAppointment();

	public List<Appointment> getActiveAppointment();

	public Appointment deleteAppointment(Integer appointId) throws ClinicException;

	public Appointment findAppointmentById(Integer appointId) throws ClinicException;

	public List<Appointment> getAllArchivedAppointment();

	public Appointment findBySpecialistId(Specialist specialistId);

	public CountDoctorDashbord getCountDoctorDashbord(Specialist specialistID);

	public List<Appointment> getAllTodayAppointment(Date begin_date, Specialist specialistID, Date end_date);

	public List<Appointment> getAllSupTodayAppointment(Date begin_date, Specialist specialistID);

	public List<Appointment> getAllAppointmentSpecialist(Specialist specialistID);

	public Appointment updateSpecialistState(Integer appointmentid, String State) throws ClinicException;

	public List<Appointment> getAllAppointments();

	public List<Appointment> getAllDistinctPatients();

	public void sendConfirmNotifications(Appointment appointment, String action);
}
