import { AfterViewInit, Component } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import * as AOS from 'aos';
import { websiteAddresses } from 'src/app/utils/constants';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { SettingDTO } from 'src/app/generated';

@Component({
  selector: 'app-section-contact-cards',
  templateUrl: './section-contact-cards.component.html',
  styleUrls: ['./section-contact-cards.component.scss']
})
export class SectionContactCardsComponent extends TranslationsBaseDirective implements AfterViewInit {
  public override translations: any = {
    appointment: {}
  }
  public websiteAddresses: { addresses: string[], phones: string[], emails: string[] } = websiteAddresses;
  public setting?: SettingDTO;

  constructor(
    override languageService: LanguageService, 
    public navService: NavigationService,
    public authService: AuthenticationService) {
    super(languageService);
    this._initialisation();
  }

  _initialisation() {
    this.setting = this.authService.getSettings()!;
    if (this.setting) {
      this.websiteAddresses.addresses[0] = this.setting.adresse!;
      this.websiteAddresses.emails[0] = this.setting.email!;
      this.websiteAddresses.emails[1] = this.setting.email2!;
      this.websiteAddresses.phones[0] = this.setting.tel!;
      this.websiteAddresses.phones[1] = this.setting.tel2!;
    }
  }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.HeuresDeTravail').subscribe((res: string) => {
      this.translations.HeuresDeTravail = res;
    });
  }

  public getHour(hour: number | undefined): string {
    if (hour) {
      return hour < 10? '0' + hour: hour + '';
    }
    return '';
  }
}
