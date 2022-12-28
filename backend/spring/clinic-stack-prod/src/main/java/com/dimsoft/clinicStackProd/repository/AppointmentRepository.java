package com.dimsoft.clinicStackProd.repository;

import java.util.Date;
import java.util.List;

import com.dimsoft.clinicStackProd.beans.Appointment;
import com.dimsoft.clinicStackProd.beans.Specialist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppointmentRepository
                extends JpaRepository<Appointment, Integer> {
        Appointment findByAppointmentDate(Date appointmentDate);

        Appointment findByPatientEmail(String patientEmail);

        List<Appointment> findBySpecialistIdSpecialistId(Integer specialistId);

        @Query("select a from Appointment a WHERE specialistId = :specialistID AND appointmentDate = :appointmentDates AND appointmentHour = :appointmentHours AND state = :states")
        List<Appointment> findSpecialityByIdAndAppointmentDate(@Param("specialistID") Specialist specialistID,
                        @Param("appointmentDates") Date appointmentDates,
                        @Param("states") String states,
                        @Param("appointmentHours") Date appointmentHours);

        @Query(value = "select * from appointment group by patient_phone", nativeQuery = true)
        List<Appointment> getAllDistinctPatients();

        @Query(value = "select * from appointment ORDER BY appointment_date DESC ", nativeQuery = true)
        List<Appointment> getAllAppointments();

        @Query("select COUNT (DISTINCT patient_phone) from Appointment a WHERE specialistId = :specialistID ")
        int getCountAllAppointment(@Param("specialistID") Specialist specialistID);

        @Query("select a from Appointment a WHERE a.specialistId.specialistId = :specialistID ORDER BY a.appointmentDate DESC ")
        List<Appointment> getAllAppointmentSpecialist(@Param("specialistID") Integer specialistID);

        @Query(value = "select MIN(created_on) from appointment a WHERE specialist_id = :specialistID ", nativeQuery = true)
        Date getDateAllAppointment(@Param("specialistID") Specialist specialistID);

        @Query("SELECT COUNT (*) from Appointment a WHERE a.state = :status AND a.specialistId = :specialistID AND a.appointmentDate BETWEEN :begin_date AND :end_date ")
        int getCountAllAcceptAppointment(@Param("status") String status,
                        @Param("specialistID") Specialist specialistID,
                        @Param("begin_date") Date begin_date,
                        @Param("end_date") Date end_date);

        @Query(value = "SELECT MIN(created_on) from appointment a WHERE a.state = :status AND specialist_id = :specialistID AND appointment_date BETWEEN :begin_date AND :end_date ", nativeQuery = true)
        Date getDateAllAcceptAppointment(@Param("status") String status,
                        @Param("specialistID") Specialist specialistID,
                        @Param("begin_date") Date begin_date,
                        @Param("end_date") Date end_date);

        @Query("SELECT a from Appointment a WHERE a.specialistId = :specialistID AND a.appointmentDate BETWEEN :begin_date AND :end_date ORDER BY a.appointmentDate DESC")
        List<Appointment> getAllTodayAppointment(@Param("begin_date") Date begin_date,
                        @Param("specialistID") Specialist specialistID,
                        @Param("end_date") Date end_date);

        @Query("SELECT a from Appointment a WHERE a.appointmentDate BETWEEN :begin_date AND :end_date ORDER BY a.appointmentDate DESC")
        List<Appointment> getAllTodayAppointment(@Param("begin_date") Date begin_date,
                        @Param("end_date") Date end_date);

        @Query("SELECT a from Appointment a WHERE a.specialistId = :specialistID AND a.appointmentDate >= :begin_date ORDER BY a.appointmentDate DESC")
        List<Appointment> getAllSupTodayAppointment(@Param("begin_date") Date begin_date,
                        @Param("specialistID") Specialist specialistID);

        @Query("UPDATE Appointment a SET a.status = :status, a.lastUpdateOn = :lastUpdateOn WHERE a.id = :appointmentId")
        Appointment deleteAppointment(@Param("status") short status,
                        @Param("lastUpdateOn") Date lastUpdateOn,
                        @Param("appointmentId") int appointmentId);

        @Query("SELECT a FROM Appointment a WHERE a.status = :activateStatus OR a.status = :deActivateStatus")
        List<Appointment> getAllAppointment(@Param("activateStatus") short appointmentStatus,
                        @Param("deActivateStatus") short appointmentDeActivateStatus);

        @Query("SELECT a FROM Appointment a WHERE a.status = :archiveStatus")
        List<Appointment> getAllArchivedappointment(@Param("archiveStatus") short archiveStatus);

        @Query("SELECT a FROM Appointment a WHERE a.status = :activateStatus")
        List<Appointment> getActiveAppointment(@Param("activateStatus") short activateStatus);

        Appointment findBySpecialistId(Specialist specialistId);

        @Transactional
        @Modifying
        @Query("UPDATE Appointment a SET a.status = :status  WHERE a.appointmentId = :appointmentId")
        void updateAppointmentStatus(@Param("appointmentId") Integer appointmentId, @Param("status") short status);
}
