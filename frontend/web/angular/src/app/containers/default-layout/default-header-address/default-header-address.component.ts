import { Component, OnInit } from '@angular/core';
import { SettingDTO } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { websiteAddresses } from 'src/app/utils/constants';

@Component({
  selector: 'app-default-header-address',
  templateUrl: './default-header-address.component.html',
  styleUrls: ['./default-header-address.component.scss']
})
export class DefaultHeaderAddressComponent implements OnInit {
  public websiteAddresses: { addresses: string[], phones: string[], emails: string[] } = websiteAddresses;
  public setting?: SettingDTO;

  constructor(public authService: AuthenticationService) { }

  ngOnInit(): void {
    this._initialisation();
  }

  public _initialisation() {
    this.setting = this.authService.getSettings()!;
  }
}
