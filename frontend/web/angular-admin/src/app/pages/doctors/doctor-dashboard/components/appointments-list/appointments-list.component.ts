import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { Appointment, AppointmentControllerService, User } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-appointments-list',
  templateUrl: './appointments-list.component.html',
  styleUrls: ['./appointments-list.component.scss']
})
export class AppointmentsListComponent implements OnInit, OnDestroy {
  public allApptDtTrigger: Subject<any> = new Subject<any>();
  public todayApptDtTrigger: Subject<any> = new Subject<any>();
  public upcomingApptDtTrigger: Subject<any> = new Subject<any>();

  public activeTab: number = 1;
  public allAppts: Appointment[] = [];
  public todayAppts: Appointment[] = [];
  public upcommingAppts: Appointment[] = [];

  public isLoadingAllAppts: boolean = false;
  public isLoadingTodayAppts: boolean = false;
  public isLoadingUpcommingAppts: boolean = false;

  public currentUser: User = this.authService.getUser();

  constructor(
    public appointmentService: AppointmentControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getAllAppts();
    this.getTodayAppts();
    this.getUpcomingAppts();
  }

  getAllAppts(event?: any, loading?: boolean): void {
    this.isLoadingAllAppts = true;
    const id: number = Number(this.currentUser?.specialist?.specialistId);
    this.appointmentService.getAllAppointmentSpecialistUsingGET(id).toPromise().then(res => {
      this.allAppts = res;
      if (!event) {
        this.allApptDtTrigger.next('');
      }
    }).finally(() => {
      this.isLoadingAllAppts = false;
    });
  }

  getTodayAppts(event?: any, loading?: boolean): void {
    this.isLoadingTodayAppts = true;
    const id: number = Number(this.currentUser?.specialist?.specialistId);
    this.appointmentService.getAllTodayAppointmentUsingGET(id).toPromise().then(res => {
      this.todayAppts = res;
      if (!event) {
        this.todayApptDtTrigger.next('');
      }
    }).finally(() => {
      this.isLoadingTodayAppts = false
    });
  }

  getUpcomingAppts(event?: any, loading?: boolean): void {
    this.isLoadingUpcommingAppts = true;
    const id: number = Number(this.currentUser?.specialist?.specialistId);
    this.appointmentService.getAllSupTodayAppointmentUsingGET(id).toPromise().then(res => {
      this.upcommingAppts = res;
      if (!event) {
        this.upcomingApptDtTrigger.next('');
      }
    }).finally(() => {
      this.isLoadingUpcommingAppts = false;
    });
  }

  public refresh(event?: any): void {
    this.getAllAppts(event);
    this.getTodayAppts(event);
    this.getUpcomingAppts(event);
  }

  public ngOnDestroy(): void {
    this.allApptDtTrigger.unsubscribe();
    this.todayApptDtTrigger.unsubscribe();
    this.upcomingApptDtTrigger.unsubscribe();
  }
}
