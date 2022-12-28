import { Component, OnInit } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { Appointment } from 'src/app/generated';
import { APPOINTMENT_STATES } from 'src/app/utils/constants';

@Component({
  selector: 'app-details-appointment',
  templateUrl: './details-appointment.component.html',
  styleUrls: ['./details-appointment.component.scss']
})
export class DetailsAppointmentComponent implements OnInit {

  public appointment?: any;

  public states: { pending: string, accept: string, cancel: string } = APPOINTMENT_STATES;

  constructor(public modalService: BsModalService) { }

  public ngOnInit(): void {
  }

  public close(): void {
    this.modalService.hide();
  }

  public getHour(data: string | undefined): string {
    if (data)
      return data.substring(data.indexOf('T') + 1, data.indexOf('T') + 6);
    return '';
  }

}
