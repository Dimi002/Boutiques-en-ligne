import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { RoleControllerService } from 'src/app/generated/api/roleController.service';
import { Role } from 'src/app/generated/model/role';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { EXCEPTION } from 'src/app/utils/constants';
import { NotificationService } from '../../../../services/notification.service';
import { CreateUpdateRoleModalComponent } from '../components/create-update-role-modal/create-update-role-modal.component';


/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-list-roles',
  templateUrl: './list-roles.component.html',
  styleUrls: ['./list-roles.component.scss']
})
export class ListRolesComponent implements OnInit {
  public roles: Role[] = [];
  public dtTrigger: Subject<any> = new Subject<any>();

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public notificationService: NotificationService,
    public rolesService: RoleControllerService,
    public modalService: BsModalService,
    public navigationService: NavigationService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getRoles();
  }

  public getRoles(reload?: any): void {
    this.isLoading = true;
    this.rolesService.getAllRoleUsingGET().toPromise().then(
      res => {
        if (res) {
          this.isSuccess = true;
          this.roles = res;
        }
      }
    ).catch(
      error => {
        this.isError = true;
        this.notificationService.danger(EXCEPTION.NO_INTERNET_CONNECTION);
      }
    ).finally(
      () => {
        this.isLoading = false;
        if (!reload) this.dtTrigger.next("");
      }
    )
  }

  public create(): void {
    const initialState = { mode: 'CREATE' }
    const bsModalRef: BsModalRef = this.modalService.show(CreateUpdateRoleModalComponent, { initialState, class: 'modal-primary modal-md' });
    bsModalRef?.onHidden?.subscribe(() => {
      const createdSuccesfully = bsModalRef.content.isSuccess;
      const isError = bsModalRef.content.isError;
      if (createdSuccesfully) {
        this.notificationService.success('Role successfully created!');
        this.getRoles('refresh');
      }
      if (isError) {
        this.notificationService.danger('Creation failed, an unknown error occurred');
      }
    })
  }

}
