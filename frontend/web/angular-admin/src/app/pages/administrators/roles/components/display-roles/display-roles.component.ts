import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DateParserService } from '../../../../../services/date-parser.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { CreateUpdateRoleModalComponent } from '../../components/create-update-role-modal/create-update-role-modal.component';
import { NavigationService } from '../../../../../services/navigation.service';
import { NotificationService } from '../../../../../services/notification.service';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { PermissionsConfigurationModalComponent } from '../permissions-configuration-modal/permissions-configuration-modal.component';
import { EXCEPTION, STATE } from '../../../../../utils/constants';
import { State } from '../../../../../additional-models/State';
import { Role } from 'src/app/generated/model/role';
import { RoleControllerService } from 'src/app/generated/api/roleController.service';
import { ConfirmationModalComponent } from 'src/app/components/confirmation-modal/confirmation-modal.component';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-display-role',
  templateUrl: './display-roles.component.html',
  styleUrls: ['./display-roles.component.scss']
})
export class DisplayRolesComponent implements OnInit, OnDestroy {

  @Input() actionsDisabled?: boolean;
  @Input() roles: Role[] = [];
  @Input() isLoading?: boolean;
  @Input() dtOptions: DataTables.Settings = {};
  @Input() dtTrigger?: any;

  @Output() createOrDeleteOrUpdateItemEvent = new EventEmitter<string>();

  public currentDate: string = '';

  public isError: boolean = false;
  public isSuccess: boolean = false;
  public state: State = STATE;


  constructor(
    public dateParserService: DateParserService,
    public modalService: BsModalService,
    public navigationService: NavigationService,
    public notificationService: NotificationService,
    public RoleService: RoleControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  ngOnInit(): void {
    this.getData();
    this.currentDate = this.dateParserService.parseToLocalFr(new Date());
  }

  public desactivate(item: Role): void {
    item.status = STATE.STATE_DEACTIVATED;
    this.RoleService.updateRoleUsingPOST(item).toPromise().then(
      res => {
        if (res) {
          this.notificationService.success('Role successfully deactivated!');
        }
      }
    ).catch(
      error => {
        item.status = STATE.STATE_ACTIVATED;
        this.notificationService.danger('Unknown error: Unable to change the status');
      }
    )
  }

  public activate(item: Role): void {
    item.status = STATE.STATE_ACTIVATED;
    this.RoleService.updateRoleUsingPOST(item).toPromise().then(
      res => {
        if (res) {
          this.notificationService.success('Role successfully activated!');
        }
      }
    ).catch(
      error => {
        item.status = STATE.STATE_DEACTIVATED;
        this.notificationService.danger('Unknown error: Unable to change the status');
      }
    )
  }

  public delete(item: Role): void {
    const initialState = { data: { title: 'Delete role', message: `Are you sure ? the ${item.roleName} item will be deleted` } };
    const bsModalRef = this.modalService.show(ConfirmationModalComponent, { initialState, class: 'modal-danger modal-sm' });
    bsModalRef?.onHide?.subscribe(() => {
      const agree = bsModalRef?.content?.agree;
      if (agree) {
        this.RoleService.deleteRoleUsingGET(item.roleId!).toPromise().then(
          () => {
            this.notificationService.success('Successfully delete!');
            this.createOrDeleteOrUpdateItemEvent.emit('delete');
          }
        ).catch(
          error => {
            if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.ROLE_NOT_FOUND) {
              this.notificationService.danger(EXCEPTION.ROLE_NOT_FOUND);
              this.createOrDeleteOrUpdateItemEvent.emit('refresh');
            }
            else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.ROLE_ALREADY_DELETED) {
              this.notificationService.danger(EXCEPTION.ROLE_ALREADY_DELETED);
              this.createOrDeleteOrUpdateItemEvent.emit('refresh');
            }
            else {
              this.isError = true;
              this.notificationService.danger('Une erreur inconue c\'est produite veuillez vérifier votre connexion à internet');
            }
          }
        )
      }
    })
  }

  public create(): void {
    const initialState = { mode: 'CREATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateRoleModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success('Role successfully created!');
        this.createOrDeleteOrUpdateItemEvent.emit('create');
      }
      if (isError) {
        this.notificationService.danger('Creation failed, an unknown error occurred');
      }
    })
  }

  public update(item: Role): void {
    const initialState = { mode: 'UPDATE', role: item }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateRoleModalComponent, { initialState, class: 'modal-secondary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const updatedSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (updatedSuccesfully) {
        this.notificationService.success('The information has been successfully modified!');
        this.createOrDeleteOrUpdateItemEvent.emit('update');
      }
      if (isError) {
        this.notificationService.danger('Update failed, an unknown error occurred');
      }
    })
  }

  public modifyPermissions(item: Role): void {
    const initialState = { role: item }
    const bsModalRef: BsModalRef = this.modalService.show(PermissionsConfigurationModalComponent, { initialState, class: 'modal-warning modal-md' });
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
}
