import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { NotificationService } from '../../../../../services/notification.service';
import { Permission } from 'src/app/generated/model/permission';
import { PermissionControllerService } from 'src/app/generated/api/permissionController.service';
import { EXCEPTION } from 'src/app/utils/constants';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-create-update-permission-modal',
  templateUrl: './create-update-permission-modal.component.html',
  styleUrls: ['./create-update-permission-modal.component.scss']
})
export class CreateUpdatePermissionModalComponent implements OnInit {

  public mode?: string;
  public form: FormGroup = this.fb.group({
    // permissionName: ['', Validators.required],
    permissionDesc: ['', Validators.required]
  });

  public permission: Permission = {};

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public modalService: BsModalRef,
    public permissionService: PermissionControllerService,
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
      permissionId: [],
      // permissionName: ['', Validators.required],
      permissionDesc: ['', Validators.required]
    });

    if (this.mode === "UPDATE") {
      this.form.patchValue({
        permissionId: this.permission.permissionId,
        // permissionName: this.permission.permissionName,
        permissionDesc: this.permission.permissionDesc,
      });
    }
  }

  public create(): void {
    if (this.authService.hasAnyPermission(['CREATE_PERMISSION'])) {
      const item: Permission = this.form.value;
      this.permission = item;
      this.isLoading = true;
      this.permissionService.createPermissionUsingPOST(this.permission).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.PERMISSION_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.PERMISSION_NAME_ALREADY_EXIST)
          }
          this.isError = true;
        }
      ).finally(
        () => {
          this.isLoading = false;
          this.close();
        }
      )
    } else {
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_PERMISSION_CREATED);
    }
  }

  public update(): void {
    if (this.authService.hasAnyPermission(['UPDATE_PERMISSION_INFOS'])) {
      const item: Permission = this.form.value;
      this.permission.permissionDesc = item.permissionDesc;
      this.isLoading = true;
      this.permissionService.updatePermissionUsingPOST(this.permission).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 501 && error.errorText == EXCEPTION.PERMISSION_NAME_ALREADY_EXIST) {
            this.notificationService.danger(EXCEPTION.PERMISSION_NAME_ALREADY_EXIST);
          } else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.PERMISSION_WAS_DELETED) {
            this.notificationService.danger(EXCEPTION.PERMISSION_WAS_DELETED);
          } else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.PERMISSION_WAS_DESACTIVED) {
            this.notificationService.danger(EXCEPTION.PERMISSION_WAS_DESACTIVED);
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
      this.notificationService.danger(EXCEPTION.NO_AUTORISATION_CHANGE_PERMISSION);
    }
  }

  public close(): void {
    this.modalService.hide();
  }
}
