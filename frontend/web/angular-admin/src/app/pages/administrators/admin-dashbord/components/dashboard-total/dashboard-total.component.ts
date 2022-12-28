import { Component, Input, OnInit } from '@angular/core';
import { Appointment } from 'src/app/generated/model/appointment';

@Component({
  selector: 'app-dashboard-total',
  templateUrl: './dashboard-total.component.html',
  styleUrls: ['./dashboard-total.component.scss']
})
export class DashboardTotalComponent implements OnInit {
  @Input() appointments: Appointment[] = [];
  public items: { dashWidgetIconClass: string, dashWidgetIcon: string, dashWidgetCount: number, dashWidgetLabel: string, dashWidgetProgressClass: string }[] = [
    { dashWidgetIconClass: 'text-warning border-warning', dashWidgetIcon: 'fe fe-mail', dashWidgetCount: 0, dashWidgetLabel: 'Messages', dashWidgetProgressClass: 'bg-warning' },
    { dashWidgetIconClass: 'text-primary border-primary', dashWidgetIcon: 'fa-user-md fas', dashWidgetCount: 0, dashWidgetLabel: 'Doctors', dashWidgetProgressClass: 'bg-primary' },
    { dashWidgetIconClass: 'text-success', dashWidgetIcon: 'fa-user-injured fas', dashWidgetCount: 0, dashWidgetLabel: 'Patients', dashWidgetProgressClass: 'bg-success' },
    { dashWidgetIconClass: 'text-danger border-danger', dashWidgetIcon: 'fa-calendar-check fas', dashWidgetCount: 0, dashWidgetLabel: 'Appointments', dashWidgetProgressClass: 'bg-danger' }
  ];
  public lastMonthTotal: number = 500;

  constructor() { }

  ngOnInit(): void {
  }

  setAppointmentsTotal(total: number): void {
    this.items[3].dashWidgetCount = total;
  }

  setPatientsTotal(total: number): void {
    this.items[2].dashWidgetCount = total;
  }

  setDoctorsTotal(total: number): void {
    this.items[1].dashWidgetCount = total;
  }

  setMessagesTotal(total: number): void {
    this.items[0].dashWidgetCount = total;
  }
}
