import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { ConfirmationModalComponent } from 'src/app/components/confirmation-modal/confirmation-modal.component';
import { Appointment, AppointmentControllerService } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { APPOINTMENT_STATES } from 'src/app/utils/constants';
import { DetailsAppointmentComponent } from '../details-appointment/details-appointment.component';

@Component({
  selector: 'app-display-appointments',
  templateUrl: './display-appointments.component.html',
  styleUrls: ['./display-appointments.component.scss']
})
export class DisplayAppointmentsComponent implements OnInit, OnDestroy {
  public dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;
  @Input() appointments: any[] = [];
  @Input() isLoading: boolean = false;

  @Output() onAppointmentStateChange = new EventEmitter<string>();

  public states: { pending: string, accept: string, cancel: string } = APPOINTMENT_STATES;

  constructor(
    public appointmentService: AppointmentControllerService,
    public modalService: BsModalService,
    public authService: AuthenticationService,
    public dateService: DateParserService
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  public getData(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 3
    };
  }

  public detail(item: any): void {
    const initialState = { appointment: item }
    const bsModalRef: BsModalRef = this.modalService.show(DetailsAppointmentComponent, { initialState, class: 'modal-primary modal-md' });
  }

  public confirm(appointment: Appointment, state: string): void {
    const initialState = { data: { title: this.getModalMessage(appointment, state).title, message: this.getModalMessage(appointment, state).message } };
    const bsModalRef = this.modalService.show(ConfirmationModalComponent, { initialState, class: 'modal-danger modal-sm' });
    bsModalRef?.onHide?.subscribe(() => {
      const agree = bsModalRef?.content?.agree;
      if (agree && appointment.appointmentId) {
        this.isLoading = true;
        this.appointmentService.updateSpecialistStateUsingGET(appointment.appointmentId, state).toPromise().then(res => {
          this.onAppointmentStateChange.emit('change');
        }).finally(() => {
          this.isLoading = false;
        });
      }
    })
  }

  public getModalMessage(appointment: Appointment, state: string): { title: string, message: string } {
    const message: { title: string, message: string } = { title: "", message: "" };
    switch (state) {
      case this.states.accept:
        message.title = "Confirmation";
        message.message = "Confirm your appointment for that date  : <strong>" + this.getDate(appointment?.appointmentDate + '') + ' at ' + this.getHour(appointment?.appointmentHour + '') + ' minutes' + "</strong>";
        return message;
        break;
      case this.states.cancel:
        message.title = "Cancellation";
        message.message = "Do you want to cancel the appointment for this date : <strong>" + this.getDate(appointment?.appointmentDate + '') + ' at ' + this.getHour(appointment?.appointmentHour + '') + ' minutes' + "</strong>";
        return message;
        break;

      default:
        return message;
        break;
    }
  }

  public getHour(data: string | undefined): string {
    if (data)
      return data.substring(data.indexOf('T') + 1, data.indexOf('T') + 6);
    return '';
  }

  public getDate(data: string | undefined): string {
    if (data)
      return data.substring(0, data.indexOf('T'));
    return '';
  }

  public ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }
}
