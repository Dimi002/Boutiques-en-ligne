import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ProfileTotalBaseComponent } from './profile-total-base.component';
import { AppointmentControllerService } from '../../../../../generated/api/appointmentController.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/generated';


@Component({
  selector: 'app-profile-total',
  templateUrl: './profile-total.component.html',
  styleUrls: ['./profile-total.component.scss']
})
export class ProfileTotalComponent extends ProfileTotalBaseComponent implements OnInit, AfterViewInit {

  public totalobjet: any;
  public currentUser: User = this.authService.getUser();

  constructor(
    public appointmentService: AppointmentControllerService,
    public authService: AuthenticationService
  ) {
    super();
  }

  getAll(): void {
    const id: number = Number(this.currentUser?.specialist?.specialistId);
    this.appointmentService.getCountCountDoctorDashbordUsingGET(id).toPromise().then(res => {
      this.totalobjet = res;
    }).finally(() => {

    });
  }

  ngOnInit(): void {
    this.getAll();
  }

  public ngAfterViewInit(): void {
    setTimeout(() => {
      this.initJQuerryFn();
    }, 300);
  }

}
