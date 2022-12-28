import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { PermissionControllerService } from 'src/app/generated/api/permissionController.service';
import { Permission } from 'src/app/generated/model/permission';
import { EXCEPTION } from 'src/app/utils/constants';
import { NotificationService } from '../../../../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-list-permissions',
  templateUrl: './list-permissions.component.html',
  styleUrls: ['./list-permissions.component.scss']
})
export class ListPermissionsComponent implements OnInit {
  public permissions: Permission[] = [];
  public dtTrigger: Subject<any> = new Subject<any>();

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public notificationService: NotificationService,
    public permissionsService: PermissionControllerService
  ) { }

  ngOnInit(): void {
    this.getPermission();
  }

  public getPermission(reload?: any): void {
    this.isLoading = true;
    this.permissionsService.getAllPermissionUsingGET().toPromise().then(
      res => {
        if (res) {
          this.isSuccess = true;
          this.permissions = res;
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

}
