import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FormBuilder, Validators, ValidatorFn, AbstractControl, FormGroup } from '@angular/forms';
import { NotificationService } from '../../../../../services/notification.service';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { ActivatedRoute } from '@angular/router';
import { EXCEPTION, ROLES } from 'src/app/utils/constants';
import { User, UserControllerService } from 'src/app/generated';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-create-update-user-modal',
  templateUrl: './create-update-user-modal.component.html',
  styleUrls: ['./create-update-user-modal.component.scss']
})
export class CreateUpdateUserModalComponent implements OnInit {

  public mode: string = '';
  public title: string = '';
  public form: FormGroup = this.fb.group({});

  public userToCreate: User = {};
  public userToUpdate: User = {};
  public currentUser: User = this.authService.getUser();
  public id: number = 0;

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;
  public admin: boolean = false;
  public specialist: boolean = false;



  constructor(
    public modalService: BsModalRef,
    private fb: FormBuilder,
    public userService: UserControllerService,
    public notificationService: NotificationService,
    public authService: AuthenticationService,
    public navigationService: NavigationService,
    public dateParserService: DateParserService,
    private activatedroute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this._init();
    this.initForm();
  }

  public _init(): void {
    this.activatedroute.paramMap.subscribe(params => {
      this.id = Number(params.get('id'));
    });
    if (this.id && this.id != 0) {
      this.mode = 'UPDATE';
      this.title = "Edit user information";
    } else {
      this.mode = 'CREATE';
      this.title = "Register a new user";
    }
  }

  get f() {
    return this.form.controls;
  }

  public initForm(): void {
    this.form = this.fb.group({
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['',
        [
          Validators.required,
          this.recheckIfPasswordMatch()
        ]
      ],
      confirmPassword: ['',
        [
          Validators.required,
          this.checkIfPasswordMatch()
        ]
      ],
      birthDate: [''],
      comment: ['']
    });
    if (this.mode === "UPDATE" && this.id && this.id != 0) {
      this.userService.findUserByIdUsingGET(this.id).toPromise().then(user => {
        this.userToUpdate = user;
        if (this.userToUpdate.roleList?.includes(ROLES.SPECIALIST)) {
          this.specialist = true;
        }
        if (this.userToUpdate.roleList?.includes(ROLES.ADMIN_ROLE)) {
          this.admin = true;
        }
        let date: Date = new Date(this.userToUpdate?.birthDate + "");
        this.form.patchValue({
          id: this.userToUpdate.id,
          lastName: this.userToUpdate?.lastName,
          username: this.userToUpdate?.username,
          firstName: this.userToUpdate?.firstName,
          phone: this.userToUpdate?.phone?.slice(3),
          email: this.userToUpdate?.email,
          birthDate: this.dateParserService.formatYYYYMMDDDate(date),
          comment: this.userToUpdate?.comment
        });
      }).catch(error => {
        this.notificationService.danger("the selected user was not found");
      });
    }
  }

  public create(): void {
    if (this.authService.hasAnyPermission(["CREATE_ADMIN_OR_SPECIALIST"])) {
      const item: User = this.form.value;
      this.userToCreate.lastName = item.lastName;
      this.userToCreate.username = item.username;
      this.userToCreate.firstName = item.firstName;
      this.userToCreate.phone = item.phone;
      this.userToCreate.email = item.email;
      this.userToCreate.password = item.password;
      this.userToCreate.birthDate = new Date(item.birthDate + "");
      this.userToCreate.comment = item.comment;
      this.userToCreate.clearPassword = item.password;
      this.isLoading = true;
      if (!this.admin && !this.specialist) {
        this.notificationService.danger('Please assign at least one role to this user');
        this.isLoading = false;
      } else if (item.phone?.length != 9) {
        this.notificationService.danger('The phone number must have 9 digits');
        this.isLoading = false;
      } else if (this.containsAnyLetters(item.phone)) {
        this.notificationService.danger('The telephone number must not contain letters');
        this.isLoading = false;
      } else if (this.userToCreate.comment?.length! > 500) {
        this.notificationService.danger('le commentaire doit avoir au plus 500 caractères');
        this.isLoading = false;
      } else {
        this.userToCreate.phone = "237" + item.phone;
        this.userService.createAdminOrSpecialistUsingPOST(this.userToCreate, this.admin, this.specialist).toPromise().then(
          res => {
            if (res) {
              this.isLoading = false;
              this.form.reset();
              this.navigationService.goTo('/home/admin-users/');
              this.notificationService.success('User successfully created!');
            }
          }
        ).catch(
          error => {
            if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.EMAIL_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.EMAIL_ALREADY_EXIST);
            } else if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.USERNAME_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.USERNAME_ALREADY_EXIST);
            } else if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST) {
              this.notificationService.danger(EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST);
            }
            this.isLoading = false;
          }
        ).finally(
          () => {
            this.isLoading = false;
          }
        )
      }
    } else {
      this.notificationService.danger("You are not authorized to do this action");
    }
  }

  public update(): void {
    const item: User = this.form.value;
    this.userToUpdate.id = this.id;
    this.userToUpdate.lastName = item.lastName;
    this.userToUpdate.username = item.username;
    this.userToUpdate.firstName = item.firstName;
    this.userToUpdate.email = item.email;
    this.userToUpdate.birthDate = new Date(item.birthDate + '');
    this.userToUpdate.comment = item.comment;
    this.isLoading = true;

    if (item.phone?.length != 9) {
      this.notificationService.danger('The phone number must have 9 digits');
      this.isLoading = false;
    } else {
      this.userToUpdate.phone = '237' + item.phone;

      this.userService.updateAdminOrSpecialistByAdminUsingPOST(this.userToUpdate).toPromise().then(
        res => {
          if (res) {
            this.isLoading = false;
            this.form.reset();
            this.navigationService.goTo('/home/admin-users/');
            this.notificationService.success('User successfully modified!');
          }
        }
      ).catch(
        error => {
          if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.EMAIL_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.EMAIL_ALREADY_EXIST);
          } else if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.USERNAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.USERNAME_ALREADY_EXIST);
          } else if (error?.stringErrorCode == 501 && error?.errorText == EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.USERNAME_AND_EMAIL_ALREADY_EXIST);
          } else if (error.stringErrorCode == 500 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
          } else if (error.errorText == EXCEPTION.USER_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.USER_ALREADY_DELETED);
          } else if (error.errorText == EXCEPTION.USER_ALREADY_DEACTIVATED) {
            this.notificationService.danger(EXCEPTION.USER_ALREADY_DEACTIVATED);
          } else {
            this.isError = true;
            this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
          }
          this.isLoading = false;
        }
      ).finally(
        () => {
          this.isLoading = false;
        }
      )
    }
  }

  public checkIfPasswordMatch(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const confirmPassword = control.value;
      let password = '';
      if (this.f['password'] !== undefined) {
        password = this.f['password'].value;
      }
      return password !== confirmPassword ? { passwordMismatch: { password: password, confirmPassword: confirmPassword } } : null;
    };
  }

  public recheckIfPasswordMatch(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (this.f['confirmPassword'] !== undefined) {
        this.f['confirmPassword'].updateValueAndValidity();
      }
      return null;
    };
  }

  public toggleViewPasswordBtn(): void {
    this.togglePasswordOptions.passwordType = this.togglePasswordOptions.passwordType === 'password' ? 'text' : 'password';
  }

  public toggleViewConfirmPasswordBtn(): void {
    this.togglePasswordOptions.confirmPasswordType = this.togglePasswordOptions.confirmPasswordType === 'password' ? 'text' : 'password';
  }

  public togglePasswordOptions: any = {
    passwordType: 'password',
    confirmPasswordType: 'password'
  }

  public errorMessages = {
    confirmPassword: [
      { type: 'passwordMismatch', message: 'The password entered does not match' }
    ]
  }

  public containsAnyLetters(str: string): boolean {
    return /[a-zA-Z]/.test(str);
  }
}