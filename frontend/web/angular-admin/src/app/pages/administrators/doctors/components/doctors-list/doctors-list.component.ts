import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { AppointmentControllerService, Specialist, SpecialistControllerService } from 'src/app/generated';

@Component({
  selector: 'app-doctors-list',
  templateUrl: './doctors-list.component.html',
  styleUrls: ['./doctors-list.component.scss']
})
export class DoctorsListComponent implements OnInit {
  public specialist: Specialist[] = [];
  public doctorsDtTrigger: Subject<any> = new Subject<any>();

  public isLoading: boolean = false;

  constructor(public specialistService: SpecialistControllerService,
    public appointmentService: AppointmentControllerService
  ) { }

  ngOnInit(): void {
    this.getAllSpecialist();
  }

  getAllSpecialist(): void {
    this.isLoading = true;
    this.specialistService.getAllSpecialistUsingGET().toPromise().then(res => {
      this.specialist = res;
    }).finally(() => {
      this.doctorsDtTrigger.next('');
      this.isLoading = false;
    });
  }

  public ngOnDestroy(): void {
    this.doctorsDtTrigger.unsubscribe();
  }
}
