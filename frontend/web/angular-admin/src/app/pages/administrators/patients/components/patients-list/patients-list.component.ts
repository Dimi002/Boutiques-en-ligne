import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Appointment, AppointmentControllerService, SpecialistControllerService } from 'src/app/generated';

@Component({
  selector: 'app-patients-list',
  templateUrl: './patients-list.component.html',
  styleUrls: ['./patients-list.component.scss']
})
export class PatientsListComponent implements OnInit {

  public patientsDtTrigger: Subject<any> = new Subject<any>();
  public patients: Appointment[] = [];

  public isLoading: boolean = false;

  constructor(public specialistService: SpecialistControllerService,
    public appointmentService: AppointmentControllerService
  ) { }

  ngOnInit(): void {
    this.getAllPatients();
  }

  getAllPatients(): void {
    this.isLoading = true;
    this.appointmentService.getAllDistinctPatientsUsingGET().toPromise().then(res => {
      this.patients = res;
    }).finally(() => {
      this.isLoading = false;
      this.patientsDtTrigger.next('');
    });
  }

  public ngOnDestroy(): void {
    this.patientsDtTrigger.unsubscribe();
  }

}
