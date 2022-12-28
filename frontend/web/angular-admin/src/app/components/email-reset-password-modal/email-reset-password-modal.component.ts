import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { NotificationService } from 'src/app/services/notification.service';
import { UserControllerService } from '../../generated/index';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
export interface IChangePassword {
  oldPassword: string;
  password: string;
  confirmPassword: string;
}

@Component({
  selector: 'app-email-reset-password-modal',
  templateUrl: './email-reset-password-modal.component.html',
  styleUrls: ['./email-reset-password-modal.component.scss']
})
export class EmailResetPasswordModalComponent implements OnInit {

  public email: string = '';

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  public error: any = {};

  constructor(
    public modalService: BsModalRef,
    public userService: UserControllerService,
    public notificationService: NotificationService
  ) { }

  ngOnInit(): void {
  }

  public save(): void {
    this.isLoading = true;
    if (this.email.includes('@')) {
      this.userService.rememberUserPasswordUsingGET(this.email).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          this.isError = true;
          this.error = error.error;
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      );
    } else {
      this.notificationService.danger("Please enter a valid email address");
    }
  }

  public close(): void {
    this.modalService.hide();
  }
}
