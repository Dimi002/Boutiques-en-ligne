import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Appointment, AppointmentControllerService, SpecialistControllerService } from 'src/app/generated';

@Component({
  selector: 'app-appointments-list',
  templateUrl: './appointments-list.component.html',
  styleUrls: ['./appointments-list.component.scss']
})
export class AppointmentsListComponent implements OnInit {
  public appointments: Appointment[] = [];
  public apptDtTrigger: Subject<any> = new Subject<any>();
  public isLoading: boolean = false;

  constructor(public specialistService: SpecialistControllerService,
    public appointmentService: AppointmentControllerService
  ) { }

  ngOnInit(): void {
    this.getAllAppointments();
  }

  getAllAppointments(): void {
    this.isLoading = true;
    this.appointmentService.getAllAppointmentsUsingGET().toPromise().then(res => {
      this.appointments = res;
    }).finally(() => {
      this.isLoading = false;
      this.apptDtTrigger.next('');
    });
  }

  public ngOnDestroy(): void {
    this.apptDtTrigger.unsubscribe();
  }

}
