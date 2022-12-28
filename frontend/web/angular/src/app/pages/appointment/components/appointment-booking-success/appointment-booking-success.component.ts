import { Component, OnInit } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-appointment-booking-success',
  templateUrl: './appointment-booking-success.component.html',
  styleUrls: ['./appointment-booking-success.component.scss']
})
export class AppointmentBookingSuccessComponent extends TranslationsBaseDirective implements OnInit {
  public override translations: any = {
    appointment: {}
  }
  public appointment: any = this.authService.getAppointment();

  public data: { title: string, description: string } = { title: 'Confirmez le rendez-vous dans notre clinique avec le docteur ', description: '' };

  constructor(override languageService: LanguageService,
    public navService: NavigationService,
    private authService: AuthenticationService) {
    super(languageService);
    this.getTranslations();
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.RdvAccepte').subscribe((res: string) => {
      this.translations.RdvAccepte = res;
    });
    this.languageService.get('appointment.RdvAvec').subscribe((res: string) => {
      this.translations.RdvAvec = res;
    });
    this.languageService.get('appointment.Le').subscribe((res: string) => {
      this.translations.Le = res;
    });
    this.languageService.get('appointment.PageAccueil').subscribe((res: string) => {
      this.translations.PageAccueil = res;
    });
    this.languageService.get('appointement.TitreConfirmationDeRDV').subscribe((res: string) => {
      this.data.title = res;
    });
  }

  public getHour(hour: string | undefined): string {
    return hour ? hour.substring(hour.indexOf('T') + 1) : '';
  }

}