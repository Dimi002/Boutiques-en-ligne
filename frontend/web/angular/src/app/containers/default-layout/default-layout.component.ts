import { Component } from '@angular/core';
import { SettingControllerService } from 'src/app/generated';

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
})
export class DefaultLayoutComponent {
  constructor(
    public settingService: SettingControllerService,
  ) { }
}
