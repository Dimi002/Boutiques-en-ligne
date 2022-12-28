import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { Contact, ContactControllerService } from 'src/app/generated';
import { AppointmentControllerService } from 'src/app/generated/api/appointmentController.service';
import { SpecialistControllerService } from 'src/app/generated/api/specialistController.service';
import { Appointment } from 'src/app/generated/model/appointment';
import { Specialist } from 'src/app/generated/model/specialist';
import { NotificationService } from 'src/app/services/notification.service';
import { DashboardTotalComponent } from '../dashboard-total/dashboard-total.component';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit, OnDestroy {
  @ViewChild('dashboardTotal', { static: true }) dashboardTotal!: DashboardTotalComponent;
  public apptDtTrigger: Subject<any> = new Subject<any>();
  public doctorsDtTrigger: Subject<any> = new Subject<any>();
  public patientsDtTrigger: Subject<any> = new Subject<any>();
  public contactsDtTrigger: Subject<any> = new Subject<any>();

  public specialist: Specialist[] = [];
  public patients: Appointment[] = [];
  public appointments: Appointment[] = [];
  public contacts: Contact[] = [];

  public isLoadingSpecialists: boolean = false;
  public isLoadingPatients: boolean = false;
  public isLoadingAppts: boolean = false;
  public isLoadingContactsMessages: boolean = false;

  constructor(public specialistService: SpecialistControllerService,
    public appointmentService: AppointmentControllerService,
    public contactService: ContactControllerService,
    public notificationService: NotificationService,
  ) { }

  public ngOnInit(): void {
    this.getAllSpecialists();
    this.getAllPatients();
    this.getAllAppointments();
    this.getAllContacts();
  }

  getAllSpecialists(refresh?: string): void {
    this.isLoadingSpecialists = true;
    this.specialistService.getAllSpecialistUsingGET().toPromise().then(res => {
      this.specialist = res;
      if (this.dashboardTotal) {
        this.dashboardTotal.setDoctorsTotal(res.length);
      }
    }).finally(() => {
      this.isLoadingSpecialists = false;
      if (!refresh)
        this.doctorsDtTrigger.next('');
    });
  }

  getAllPatients(refresh?: string): void {
    this.isLoadingPatients = true;
    this.appointmentService.getAllDistinctPatientsUsingGET().toPromise().then(res => {
      this.patients = res;
      if (this.dashboardTotal) {
        this.dashboardTotal.setPatientsTotal(res.length);
      }
    }).finally(() => {
      this.isLoadingPatients = false;
      if (!refresh)
        this.patientsDtTrigger.next('');
    });
  }

  getAllAppointments(refresh?: string): void {
    this.isLoadingAppts = true;
    this.appointmentService.getAllAppointmentsUsingGET().toPromise().then(res => {
      this.appointments = res;
      if (this.dashboardTotal) {
        this.dashboardTotal.setAppointmentsTotal(res.length);
      }
    }).finally(() => {
      this.isLoadingAppts = false;
      if (!refresh)
        this.apptDtTrigger.next('');
    });
  }

  getAllContacts(refresh?: string): void {
    this.isLoadingContactsMessages = true;
    this.contactService.recordsUsingGET().toPromise().then(res => {
      this.contacts = res!;
      if (this.dashboardTotal && res) {
        this.dashboardTotal.setMessagesTotal(this.contacts.length);
      }
    }).catch(error => {
      if(error && error?.errorText == 'The contacts list is null')
      this.notificationService.danger("The contacts list is null");
    }).finally(() => {
      this.isLoadingContactsMessages = false;
      if (!refresh)
        this.contactsDtTrigger.next('');
    });
  }

  public ngOnDestroy(): void {
    this.apptDtTrigger.unsubscribe();
    this.doctorsDtTrigger.unsubscribe();
    this.patientsDtTrigger.unsubscribe();
    this.contactsDtTrigger.unsubscribe();
  }

}
