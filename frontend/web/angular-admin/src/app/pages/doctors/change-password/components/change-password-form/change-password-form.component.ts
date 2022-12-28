import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { UserControllerService } from 'src/app/generated/api/userController.service';
import { User } from 'src/app/generated/model/user';
import { UserPasswordChangeModel } from 'src/app/generated/model/userPasswordChangeModel';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.scss']
})
export class ChangePasswordFormComponent implements OnInit {
  public form: FormGroup = this.formBuilder.group({});
  public currentUser: User = this.authService.getUser();
  public UserPasswordChangeModel: UserPasswordChangeModel = {}

  public isLoading: boolean = false;

  public togglePasswordOptions: any = {
    oldPasswordType: 'password',
    passwordType: 'password',
    confirmPasswordType: 'password'
  }

  public errorMessages = {
    confirmPassword: [
      { type: 'passwordMismatch', message: 'The password entered does not match' }
    ]
  }

  public error: any = {};

  constructor(
    private formBuilder: FormBuilder,
    public authService: AuthenticationService,
    public userControllerService: UserControllerService,
    public notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  get f(): any {
    return this.form.controls;
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', [Validators.required, this.recheckIfPasswordMatch()]),
      confirmPassword: new FormControl('', [Validators.required, this.checkIfPasswordMatch()])
    });
  }

  public checkIfPasswordMatch(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const confirmPassword = control.value;
      let password = '';
      if (this.f.newPassword !== undefined) {
        password = this.f.newPassword.value;
      }
      return password !== confirmPassword ? { passwordMismatch: { password: password, confirmPassword: confirmPassword } } : null;
    };
  }

  public recheckIfPasswordMatch(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (this.f.confirmPassword !== undefined) {
        this.f.confirmPassword.updateValueAndValidity();
      }
      return null;
    };
  }

  public toggleViewOldPasswordBtn(): void {
    this.togglePasswordOptions.oldPasswordType = this.togglePasswordOptions.oldPasswordType === 'password' ? 'text' : 'password';
  }

  public toggleViewPasswordBtn(): void {
    this.togglePasswordOptions.passwordType = this.togglePasswordOptions.passwordType === 'password' ? 'text' : 'password';
  }

  public toggleViewConfirmPasswordBtn(): void {
    this.togglePasswordOptions.confirmPasswordType = this.togglePasswordOptions.confirmPasswordType === 'password' ? 'text' : 'password';
  }

  public saveChanges(): void {
    this.isLoading = true;
    this.UserPasswordChangeModel.newPassword = this.form.value?.newPassword;
    this.UserPasswordChangeModel.oldPassword = this.form.value?.oldPassword;
    this.UserPasswordChangeModel.userId = this.currentUser?.id;

    this.userControllerService.updateUserPasswordUsingPOST(this.UserPasswordChangeModel).subscribe(
      res => {
        this.notificationService.success("Your password has been successfully reset");
      },
      error => {
        if (error.stringErrorCode == 506 && error.errorText == EXCEPTION.OLD_PASSWORD_NOT_MATCH) {
          this.notificationService.danger(EXCEPTION.OLD_PASSWORD_NOT_MATCH);
          this.isLoading = false
        } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
          this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
          this.isLoading = false
        } else {
          this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
        }
      },
      () => {
        this.isLoading = false
      }
    )
  }

}
