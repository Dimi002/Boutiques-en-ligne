package com.dimsoft.clinicStackProd.scheduler;

import com.dimsoft.clinicStackProd.beans.Appointment;
import com.dimsoft.clinicStackProd.beans.Specialist;
import com.dimsoft.clinicStackProd.exceptions.InvalidInputException;
import com.dimsoft.clinicStackProd.repository.AppointmentRepository;
import com.dimsoft.clinicStackProd.service.AppointmentService;
import com.dimsoft.clinicStackProd.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class AppointmentScheduler {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentService appointmentService;

    //The job will be executed every 1hour
    @Scheduled(cron = "0 0/30 * * * * ?")
    //@Scheduled(cron = "* * * * * *")
    public void notifyClients() {
        Date min = new Date();
        min.setHours(00);
        min.setMinutes(00);
        min.setSeconds(00);
        Date max = new Date();
        max.setHours(23);
        max.setMinutes(59);
        max.setSeconds(59);
        List<Appointment> appointments = appointmentRepository.getAllTodayAppointment(min, max);
        for (Appointment app : appointments) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LocalDate todayDay = LocalDate.parse(formatter.format(new Date()), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate appDate = LocalDate.parse(
                    formatter.format(app.getAppointmentHour()),
                    DateTimeFormatter.ISO_LOCAL_DATE);
            long minuteBeforeAppointment = ChronoUnit.MINUTES.between(appDate, todayDay);
            if (minuteBeforeAppointment <= 30) {
                this.appointmentService.sendConfirmNotifications(app, Constants.N_A_CRON);
            }
        }
    }
}
