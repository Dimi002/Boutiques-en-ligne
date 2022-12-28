import { Component, OnInit } from '@angular/core';
import { Setting, SettingControllerService } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    public settingService: SettingControllerService,
    public authService: AuthenticationService,
    public notificationService: NotificationService
  ) { }

  public ngOnInit(): void {
    this._initialisation();
  }

  _initialisation() {
    this.settingService.recordsUsingGET3().toPromise().then((res?: Setting) => {
      if (res)
        this.authService.storeSettings(res);
    }).catch(error => {
      if (error && error?.errorText == 'The setting is null')
        this.notificationService.success('Les paramètres sont null')
    })
  }
}
