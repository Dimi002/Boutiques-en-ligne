import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Permission, PermissionControllerService, PermissionsIds, RoleControllerService, RolesPermissionsControllerService, User } from 'src/app/generated';
import { EXCEPTION } from 'src/app/utils/constants';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { NotificationService } from '../../../../../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-permissions-configuration-modal',
  templateUrl: './permissions-configuration-modal.component.html',
  styleUrls: ['./permissions-configuration-modal.component.scss']
})
export class PermissionsConfigurationModalComponent implements OnInit {

  public role?: any;
  public permissions: any[] = [];

  public selectedPermissions: any[] = [];


  public isLoading: boolean = false;
  public isLoadingPermissions: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;
  public currentUser: User = this.authService.getUser();
  public permissionList: string[] = []

  public isAllPermissionsSelected: boolean = false;

  constructor(
    public modalService: BsModalRef,
    public notificationService: NotificationService,
    public rolesService: RoleControllerService,
    public rolesPermissionsService: RolesPermissionsControllerService,
    public permissionsService: PermissionControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getPermissions(true);
  }

  public getPermissions(loading?: boolean): void {
    if (loading) this.isLoadingPermissions = true;
    this.permissionsService.getActivePermissionUsingGET().toPromise().then(
      res => {
        if (res) {
          this.permissions = res;
          this.getPermissionsForRole(this.role?.roleId!);
        }
      }
    ).catch(
      error => {
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    ).finally(
      () => {
        if (loading) this.isLoadingPermissions = false;
      }
    )
  }

  public getPermissionsForRole(roleId: number): void {
    this.rolesPermissionsService.getAllRolePermissionsUsingGET(roleId).toPromise().then(
      (res) => {
        if (res) {
          this.role["rolesPermissionsList"] = res;
          this.setDefaultPermissions();
        }
      }
    ).catch(
      error => {
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    )
  }

  public checkPermission(item: any): void {
    this.checkIfRoleHasAllPermissions();
  }

  public checkIfRoleHasAllPermissions(): void {
    let res: boolean = true;
    this.permissions.forEach((permission: any) => {
      res &&= permission['checked'];
    })
    this.isAllPermissionsSelected = res;
  }

  public setDefaultPermissions(): void {
    this.role["rolesPermissionsList"]?.forEach((rolePermission?: any) => {
      const permission: any = this.permissions?.find(p => (p.permissionName === rolePermission.permissionName));
      if (permission) {
        permission['checked'] = true;
      }
    })
    this.checkIfRoleHasAllPermissions();
  }

  public save(): void {
    if (this.authService.hasAnyPermission(['ASSIGN_PERMISSIONS_TO_ROLE'])) {
      this.isLoading = true;
      this.getSelectedPermissions();
      const selectedPermissionsIds: PermissionsIds = this.getPermissionsListIds(this.selectedPermissions);
      this.rolesPermissionsService.assignPermissionsToRoleUsingPOST(selectedPermissionsIds, this.role?.roleId!).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
            if (this.authService.hasAnyRole([this.role.roleName])) {
              this.selectedPermissions.forEach(permission => {
                this.permissionList.push(permission?.permissionName);
              });
              this.currentUser.permissionList = this.permissionList;
              this.authService.storeUser(this.currentUser);
            }
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.ROLE_ALREADY_DEACTIVATED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DEACTIVATED);
          } else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DELETED);
          } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.ROLE_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.ROLE_NOT_FOUND);
          } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.PERMISSION_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.PERMISSION_NOT_FOUND);
          } else {
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
      this.notificationService.danger('You are not allowed to change the roles and permissions of a user');
    }
  }

  public getPermissionsListIds(permissions: Permission[]): PermissionsIds {
    const permissionsIds: PermissionsIds = {
      permissionsIdsList: []
    };
    permissions.forEach(permission => {
      permissionsIds.permissionsIdsList?.push(Number(permission.permissionId));
    })
    return permissionsIds;
  }

  public getSelectedPermissions(): void {
    this.selectedPermissions = [];
    this.permissions.forEach((permission: any) => {
      if (permission['checked']) {
        this.selectedPermissions.push(permission);
      }
    })
  }

  public toggleSelectAllPermissionsBtn(event: any): void {
    if (event.target.checked) {
      this.permissions.forEach((permission: any) => {
        permission['checked'] = true;
      })
    } else {
      this.permissions.forEach((permission: any) => {
        permission['checked'] = false;
      })
    }
  }

  public close(): void {
    this.modalService.hide();
  }
}
