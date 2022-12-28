import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { User, UserControllerService } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { EXCEPTION } from 'src/app/utils/constants';
import { NotificationService } from '../../../../services/notification.service';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
 */
@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss']
})
export class ListUsersComponent implements OnInit {
  public users: User[] = [];
  public dtTrigger: Subject<any> = new Subject<any>();

  public isLoading: boolean = false;
  public isError: boolean = false;
  public isSuccess: boolean = false;

  constructor(
    public notificationService: NotificationService,
    public userService: UserControllerService,
    public navigationService: NavigationService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getUsers();
  }

  public getUsers(reload?: any, loading?: boolean): void {
    this.isLoading = true;
    this.userService.getAllUserUsingGET().toPromise().then(
      res => {
        if (res) {
          this.isSuccess = true;
          this.users = res;
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
    this.navigationService.goTo('/home/admin-users/create');
  }

}
