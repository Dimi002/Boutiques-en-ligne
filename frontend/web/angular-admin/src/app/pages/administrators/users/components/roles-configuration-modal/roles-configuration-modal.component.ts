import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Role, RoleControllerService, RolesIds, User, UserControllerService, UsersRolesControllerService } from 'src/app/generated';
import { EXCEPTION } from 'src/app/utils/constants';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { NotificationService } from '../../../../../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-roles-configuration-modal',
  templateUrl: './roles-configuration-modal.component.html',
  styleUrls: ['./roles-configuration-modal.component.scss']
})
export class RolesConfigurationModalComponent implements OnInit {

  public user!: User;
  public roles: any[] = [];

  public selectedRoles: any[] = [];
  public roleList: string[] = [];

  public isLoading: boolean = false;
  public isLoadingRoles: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;
  public currentUser: User = this.authService.getUser();


  public isAllRolesSelected: boolean = false;

  constructor(
    public modalService: BsModalRef,
    public notificationService: NotificationService,
    public usersService: UserControllerService,
    public rolesService: RoleControllerService,
    public usersRolesService: UsersRolesControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getRoles(true);
  }

  public getRoles(loading?: boolean): void {
    if (loading) this.isLoadingRoles = true;
    this.rolesService.getActivesRolesUsingGET().toPromise().then(
      res => {
        if (res) {
          this.roles = res;
          this.getRolesForUser(Number(this.user.id));
        }
      }
    ).catch(
      error => {
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    ).finally(
      () => {
        if (loading) this.isLoadingRoles = false;
      }
    )
  }

  public getRolesForUser(userId: number): void {
    this.usersRolesService.getAllUserRoleUsingGET(userId).toPromise().then(
      res => {
        if (res) {
          this.user["roleList"] = res;
          this.setDefaultRoles();
        }
      }
    ).catch(
      error => {
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    )
  }

  public checkRole(item: any): void {
    this.checkIfUserHasAllRoles();
  }

  public checkIfUserHasAllRoles(): void {
    let res: boolean = true;
    this.roles.forEach((role: any) => {
      res &&= role['checked'];
    })
    this.isAllRolesSelected = res;
  }

  public setDefaultRoles(): void {
    if (this.user.roleList) {
      this.user["roleList"].forEach((userRole: any) => {
        const role: any = this.roles.find(p => (p.roleName === userRole.roleName));
        if (role) {
          role['checked'] = true;
        }
      })
      this.checkIfUserHasAllRoles();
    }
  }

  public save(): void {
    if (this.authService.hasAnyPermission(['ASSIGN_ROLES_TO_USER'])) {
      this.isLoading = true;
      this.getSelectedRoles();
      const selectedRolesIds: RolesIds = this.getRolesListIds(this.selectedRoles);
      this.usersRolesService.assignRoleToUserUsingPOST(selectedRolesIds, Number(this.user.id)).toPromise().then(
        res => {
          if (res) {
            this.isSuccess = true;
            this.selectedRoles.forEach(role => {
              this.roleList.push(role?.roleName);
            })
            if (this.user.id == this.currentUser.id) {
              this.currentUser.roleList = this.roleList;
              this.authService.storeUser(this.currentUser);
            }
            this.dispachRoleUserChangeEvent();
          }
        }
      ).catch(
        error => {
          if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.ROLE_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.ROLE_NOT_FOUND);
          } else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.USER_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.USER_ALREADY_DELETED);
          } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
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
      this.notificationService.danger('You are not allowed to change a user\'s roles and permissions');
    }
  }

  public getRolesListIds(roles: Role[]): RolesIds {
    const rolesIds: RolesIds = {
      rolesIdsList: []
    };
    roles.forEach(role => {
      if (rolesIds.rolesIdsList)
        rolesIds.rolesIdsList.push(Number(role.roleId));
    })
    return rolesIds;
  }

  public getSelectedRoles(): void {
    this.selectedRoles = [];
    this.roles.forEach((role: any) => {
      if (role['checked']) {
        this.selectedRoles.push(role);
      }
    })
  }

  public toggleSelectAllRolesBtn(event: any): void {
    if (event.target.checked) {
      this.roles.forEach((role: any) => {
        role['checked'] = true;
      })
    } else {
      this.roles.forEach((role: any) => {
        role['checked'] = false;
      })
    }
  }

  public close(): void {
    this.modalService.hide();
  }

  public dispachRoleUserChangeEvent(): void {
    window.dispatchEvent(new CustomEvent('role-user:change'));
  }
}
