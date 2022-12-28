import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { NotificationService } from '../../../../../services/notification.service';
import { RoleControllerService } from 'src/app/generated/api/roleController.service';
import { Role } from 'src/app/generated/model/role';
import { EXCEPTION } from 'src/app/utils/constants';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-create-update-role-modal',
  templateUrl: './create-update-role-modal.component.html',
  styleUrls: ['./create-update-role-modal.component.scss']
})
export class CreateUpdateRoleModalComponent implements OnInit {

  public mode?: string;
  public form: FormGroup = this.fb.group({
    roleName: ['', Validators.required],
    roleDesc: ['', Validators.required]
  });

  public role: Role = {};

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public modalService: BsModalRef,
    public rolesService: RoleControllerService,
    private fb: FormBuilder,
    public authService: AuthenticationService,
    public notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  get f() {
    return this.form.controls;
  }

  public initForm(): void {
    this.form = this.fb.group({
      roleId: [],
      roleName: ['', Validators.required],
      roleDesc: ['', Validators.required]
    });

    if (this.mode === "UPDATE") {
      this.form.patchValue({
        roleId: this.role?.roleId,
        roleName: this.role?.roleName,
        roleDesc: this.role?.roleDesc,
      });
    }
  }

  public create(): void {
    if (this.authService.hasAnyPermission(['CREATE_ROLE'])) {
      const item: Role = this.form.value;
      this.role = item;
      this.isLoading = true;
      this.rolesService.createRoleUsingPOST(this.role).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.ROLE_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.ROLE_NAME_ALREADY_EXIST);
          }
          else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DELETED);
          }
          else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.ROLE_ALREADY_DEACTIVATED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DEACTIVATED);
          }
          else {
            this.isError = true;
          }
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      )
    } else {
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_ROLE_CREATED);
    }
  }

  public update(): void {
    if (this.authService.hasAnyPermission(['UPDATE_ROLE_INFOS'])) {
      const item: Role = this.form.value;
      this.role.roleName = item.roleName;
      this.role.roleDesc = item.roleDesc;
      this.isLoading = true;
      this.rolesService.updateRoleUsingPOST(this.role!).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.ROLE_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.ROLE_NAME_ALREADY_EXIST);
          } else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_WAS_DELETED) {
            this.notificationService.danger(EXCEPTION.ROLE_WAS_DELETED)
          } else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.ROLE_WAS_DESACTIVED) {
            this.notificationService.danger(EXCEPTION.ROLE_WAS_DESACTIVED)
          }
          else {
            this.isError = true;
          }
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      )
    } else {
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_CHANGE_ROLE);
    }
  }

  public close(): void {
    this.modalService.hide();
  }
}
