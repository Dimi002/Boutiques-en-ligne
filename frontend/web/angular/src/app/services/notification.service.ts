import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';


@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor(private toastr: ToastrService) {}

  public success(message: string): void {
    this.toastr.success(message, 'Message', { closeButton: true, positionClass: 'toast-bottom-right' });
  }

  public warning(message: string): void {
    this.toastr.warning(message, 'Message', { closeButton: true, positionClass: 'toast-bottom-right' })
  }

  public danger(message: string): void {
    this.toastr.error(message, 'Message', { closeButton: true, positionClass: 'toast-bottom-right' })
  }

  public info(message: string): void {
    this.toastr.info(message, 'Message', { closeButton: true, positionClass: 'toast-bottom-right' })
  }
}
