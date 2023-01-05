import { Component, OnInit } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { LoginData } from 'src/app/additional-models/LoginData';
import { EmailResetPasswordModalComponent } from 'src/app/components/email-reset-password-modal/email-reset-password-modal.component';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {
  public passwordType: string = 'password';
  public loginData: LoginData = new LoginData();
  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public navigationService: NavigationService,
    public authService: AuthenticationService,
    public notificationService: NotificationService,
    public modalService: BsModalService
  ) { }

  public ngOnInit(): void {
  }

  public login(): boolean {
    if (!this.loginData.login) {
      this.notificationService.danger('Please enter your username or email address');
      return false;
    }
    if (!this.loginData.password) {
      this.notificationService.danger('Please enter your password');
      return false;
    }
    this.isLoading = true;
    this.authService.login(this.loginData).toPromise().then((res: any) => {
      this.isLoading = false;
      this.authService.localLogin(res.user, res.token, res.tokenExpiresAt);
      if (this.authService.hasAnyRole(['ADMIN']) && !this.authService.hasAnyRole(['SPECIALIST'])) {
        this.navigationService.goTo('home/admin-dashboard');
        this.notificationService.success('You are successfully connected');
      }
      if (this.authService.hasAnyRole(['ADMIN']) && this.authService.hasAnyRole(['SPECIALIST'])) {
        this.navigationService.goTo('home/boutique-dashboard');
        this.notificationService.success('You are successfully connected');
      }
      if (this.authService.hasAnyRole(['SPECIALIST']) && !this.authService.hasAnyRole(['ADMIN'])) {
        this.navigationService.goTo('home/boutique-dashboard');
        this.notificationService.success('You are successfully connected');
      }
    }).catch(error => {
      this.notificationService.danger('Incorrect login or password or check your internet connection');
    }).finally(() => {
      this.isLoading = false;
    });
    return false;
  }

  public sendEmailResetPassord(): void {
    const bsModalRef: BsModalRef = this.modalService.show(EmailResetPasswordModalComponent, { class: 'modal-purple modal-md' });
    bsModalRef.onHidden?.subscribe(() => {
      const sendSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (sendSuccesfully) {
        this.notificationService.success('The password reset email was successfully sent to the email address you entered.');
      }
      if (isError) {
        this.notificationService.danger('Failed to send email, an unknown error occurred, please enter a valid email address or check your internet connection');
      }
    })
  }

  public toggleViewPasswordBtn(): void {
    this.passwordType = this.passwordType === 'password' ? 'text' : 'password';
  }

}
