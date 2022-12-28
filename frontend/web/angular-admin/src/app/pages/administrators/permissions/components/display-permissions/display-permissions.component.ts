import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DateParserService } from '../../../../../services/date-parser.service';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { CreateUpdatePermissionModalComponent } from '../../components/create-update-permission-modal/create-update-permission-modal.component';
import { NavigationService } from '../../../../../services/navigation.service';
import { NotificationService } from '../../../../../services/notification.service';
import { AuthenticationService } from '../../../../../services/authentication.service';
import { EXCEPTION, STATE } from '../../../../../utils/constants';
import { State } from '../../../../../additional-models/State';
import { PermissionControllerService } from 'src/app/generated/api/permissionController.service';
import { Permission } from 'src/app/generated/model/permission';
import { ConfirmationModalComponent } from 'src/app/components/confirmation-modal/confirmation-modal.component';
import { ADTSettings } from 'angular-datatables/src/models/settings';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-display-permissions',
  templateUrl: './display-permissions.component.html',
  styleUrls: ['./display-permissions.component.scss']
})
export class DisplayPermissionsComponent implements OnInit, OnDestroy {

  @Input() actionsDisabled?: boolean;
  @Input() permissions?: Permission[] = [];
  @Input() isLoading?: boolean;
  @Input() dtTrigger?: any;


  @Output() createOrDeleteOrUpdateItemEvent = new EventEmitter<string>();

  public currentDate: string = '';

  public isError: boolean = false;
  public isSuccess: boolean = false;
  public state: State = STATE;
  public dtOptions: DataTables.Settings = { pagingType: 'full_numbers' };

  constructor(
    public dateParserService: DateParserService,
    public modalService: BsModalService,
    public navigationService: NavigationService,
    public notificationService: NotificationService,
    public permissionService: PermissionControllerService,
    public authService: AuthenticationService
  ) { }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  ngOnInit(): void {
    this.currentDate = this.dateParserService.parseToLocalFr(new Date());
  }

  public desactivate(item: Permission): void {
    item.status = STATE.STATE_DEACTIVATED;
    this.permissionService.updatePermissionUsingPOST(item).toPromise().then(
      res => {
        if (res) {
          this.notificationService.success(EXCEPTION.PERMISSION_DESACTIVED_SUCCES);
        }
      }
    ).catch(
      error => {
        item.status = STATE.STATE_ACTIVATED;
        this.notificationService.danger(EXCEPTION.UNKNOWN_ERROR_STATUS);
      }
    )
  }

  public activate(item: Permission): void {
    item.status = STATE.STATE_ACTIVATED;
    this.permissionService.updatePermissionUsingPOST(item).toPromise().then(
      res => {
        if (res) {
          this.notificationService.success(EXCEPTION.PERMISSION_ACTIVED_SUCCES);
        }
      }
    ).catch(
      error => {
        item.status = STATE.STATE_DEACTIVATED;
        this.notificationService.danger(EXCEPTION.UNKNOWN_ERROR_STATUS);
      }
    )
  }

  public delete(item: Permission): void {
    const initialState = { data: { title: 'Delete permission', message: `Are you sure ? the ${item.permissionName} item will be deleted` } };
    const bsModalRef = this.modalService.show(ConfirmationModalComponent, { initialState, class: 'modal-danger modal-sm' });
    bsModalRef?.onHide?.subscribe(() => {
      const agree = bsModalRef?.content?.agree;
      if (agree) {
        this.permissionService.deletePermissionUsingGET(item.permissionId!).toPromise().then(
          res => {
            this.notificationService.success(EXCEPTION.DELETE_PERMISSION_SUCCES);
            this.createOrDeleteOrUpdateItemEvent.emit('delete');
          }
        ).catch(
          error => {
            if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.PERMISSION_ALREADY_DELETED) {
              this.notificationService.danger(EXCEPTION.PERMISSION_ALREADY_DELETED)
            }
            else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.PERMISSION_NOT_FOUND) {
              this.notificationService.danger(EXCEPTION.PERMISSION_NOT_FOUND)
            }
            else {
              this.isError = true;
              this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
            }
          }
        )
      }
    })
  }

  public create(): void {
    const initialState = { mode: 'CREATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdatePermissionModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success(EXCEPTION.PERMISSION_SUCCEFULLY_CREATE);
        this.createOrDeleteOrUpdateItemEvent.emit('create');
      }
      if (isError) {
        this.notificationService.danger(EXCEPTION.UNKNOWN_ERROR_PERMISSION_CREATED);
      }
    })
  }

  public update(item: Permission): void {
    const initialState = { mode: 'UPDATE', permission: item }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdatePermissionModalComponent, { initialState, class: 'modal-secondary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const updatedSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (updatedSuccesfully) {
        this.notificationService.success(EXCEPTION.PERMISSION_SUCCEFULLY_UPDATE);
        this.createOrDeleteOrUpdateItemEvent.emit('update');
      }
      if (isError) {
        this.notificationService.danger(EXCEPTION.PERMISSION_SUCCEFULLY_UPDATE);
      }
    })
  }

  public displayDate(date: Date | undefined): string {
    if (date)
      return this.dateParserService.parseToLocalFr(new Date(date));
    return '';
  }
}
