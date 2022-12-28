import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DateParserService } from '../../../../../services/date-parser.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { NavigationService } from '../../../../../services/navigation.service';
import { NotificationService } from '../../../../../services/notification.service';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { RolesConfigurationModalComponent } from '../roles-configuration-modal/roles-configuration-modal.component';
import { EXCEPTION, STATE } from '../../../../../utils/constants';
import { State } from '../../../../../additional-models/State';
import { ConfirmationModalComponent } from 'src/app/components/confirmation-modal/confirmation-modal.component';
import { UserControllerService } from 'src/app/generated/api/userController.service';
import { User } from 'src/app/generated/model/user';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-display-users',
  templateUrl: './display-users.component.html',
  styleUrls: ['./display-users.component.scss']
})
export class DisplayUsersComponent implements OnInit, OnDestroy {

  @Input() initialUsers: User[] = [];
  @Input() users: User[] = [];
  @Input() isLoading?: boolean = false;
  @Input() dtTrigger?: any;


  @Output() createOrDeleteOrUpdateItemEvent = new EventEmitter<string>();

  public currentUser: User = this.authService.getUser();
  public currentDate: string = '';
  public roleList?: string[] = this.authService.getUser().roleList;

  public isError: boolean = false;
  public isSuccess: boolean = false;
  public state: State = STATE;
  public dtOptions: DataTables.Settings = {};

  constructor(
    public dateParserService: DateParserService,
    public modalService: BsModalService,
    public navigationService: NavigationService,
    public notificationService: NotificationService,
    public userService: UserControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  ngOnInit(): void {
    this.listenForLoginEvents();
    this.getData();
    this.currentDate = this.dateParserService.parseToLocalFr(new Date());
  }

  public desactivate(item: User): void {
    if (this.authService.hasAnyPermission(["UPDATE_USER_STATUS"])) {
      item.status = STATE.STATE_DEACTIVATED;
      this.userService.updateUserStatusUsingPOST(item).toPromise().then(
        res => {
          if (res) {
            this.notificationService.success('User successfully deactivated!');
          }
        }
      ).catch(
        error => {
          item.status = STATE.STATE_ACTIVATED;
          if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.USER_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.USER_ALREADY_DELETED);
            this.createOrDeleteOrUpdateItemEvent.emit('refresh');
          } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
            this.createOrDeleteOrUpdateItemEvent.emit('refresh');
          } else {
            this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
          }
        }
      )
    } else {
      this.notificationService.danger("You are not authorized to perform this action");
    }
  }

  public activate(item: User): void {
    if (this.authService.hasAnyPermission(["UPDATE_USER_STATUS"])) {
      item.status = STATE.STATE_ACTIVATED;
      this.userService.updateUserStatusUsingPOST(item).toPromise().then(
        res => {
          if (res) {
            this.notificationService.success('User successfully deactivated!');
          }
        }
      ).catch(
        error => {
          item.status = STATE.STATE_DEACTIVATED;
          if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.USER_ALREADY_DELETED) {
            this.notificationService.danger(EXCEPTION.USER_ALREADY_DELETED);
            this.createOrDeleteOrUpdateItemEvent.emit('refresh');
          } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
            this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
            this.createOrDeleteOrUpdateItemEvent.emit('refresh');
          } else {
            this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
          }
        }
      )
    } else {
      this.notificationService.danger("You are not authorized to perform this action");
    }
  }

  public delete(item: User): void {
    if (this.authService.hasAnyPermission(["DELETE_USER"])) {
      const initialState = { data: { title: 'Delete user', message: `Are you sure ? the ${item.username} item will be deleted` } };
      const bsModalRef = this.modalService.show(ConfirmationModalComponent, { initialState, class: 'modal-danger modal-sm' });
      bsModalRef?.onHide?.subscribe(() => {
        const agree = bsModalRef?.content?.agree;
        if (agree) {
          this.userService.deleteUserUsingGET1(item.id!).toPromise().then(
            res => {
              this.notificationService.success('Supprimer avec succès!');
              this.createOrDeleteOrUpdateItemEvent.emit('refresh');
            }
          ).catch(
            error => {
              if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.USER_ALREADY_DELETED) {
                this.notificationService.danger(EXCEPTION.USER_ALREADY_DELETED);
                this.createOrDeleteOrUpdateItemEvent.emit('refresh');
              } else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.USER_NOT_FOUND) {
                this.notificationService.danger(EXCEPTION.USER_NOT_FOUND);
                this.createOrDeleteOrUpdateItemEvent.emit('refresh');
              } else {
                this.isError = true;
                this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
              }
            }
          )
        }
      })
    } else {
      this.notificationService.danger("You are not authorized to perform this action");
    }

  }

  public update(user: User): void {
    if (this.authService.hasAnyPermission(["UPDATE_ADMIN_OR_SPECIALIST"])) {
      const route: string = '/home/admin-users/update/' + user.id;
      this.navigationService.goTo(route);
    } else {
      this.notificationService.danger("You are not authorized to perform this action");
    }
  }

  public modifyRoles(item: User): void {
    if (this.authService.hasAnyPermission(['ASSIGN_ROLES_TO_USER'])) {
      const userClone: User = JSON.parse(JSON.stringify(item));
      const initialState = { user: userClone };
      const bsModalRef: BsModalRef = this.modalService.show(RolesConfigurationModalComponent, { initialState, class: 'modal-warning modal-md' });
      bsModalRef?.onHidden?.subscribe(() => {
        const updatedSuccesfully = bsModalRef.content.isSuccess;
        const isError = bsModalRef.content.isError;
        if (updatedSuccesfully) {
          this.notificationService.success('The information has been successfully modified!');
        }
        if (isError) {
          this.notificationService.danger('Update failed, an unknown error occurred');
        }
      })
    } else {
      this.notificationService.danger('You are not allowed to change a user roles and permissions');
    }
  }

  public displayDate(date: Date | undefined): string {
    if (date)
      return this.dateParserService.parseToLocalFr(new Date(date));
    return '';
  }

  public getData(): void {
    this.dtOptions = {
      pagingType: 'full_numbers'
    };
  }

  public listenForLoginEvents(): void {
    window.addEventListener('role-user:change', () => {
      this.createOrDeleteOrUpdateItemEvent.emit('refresh');
    });
  }
}
