import { Component, OnInit } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { AppointmentControllerService } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { EXCEPTION } from 'src/app/utils/constants';
import { Appointment } from 'src/app/generated';

@Component({
  selector: 'app-appointment-booking-verify',
  templateUrl: './appointment-booking-verify.component.html',
  styleUrls: ['./appointment-booking-verify.component.scss']
})
export class AppointmentBookingVerifyComponent extends TranslationsBaseDirective implements OnInit {
  public override translations: any = {
    appointment: {}
  }
  public appointment: Appointment
    = this.authService.getAppointment();

  public data: { title: string, description: string } = { title: 'Confirmez le rendez-vous dans notre clinique avec le docteur ', description: '' };
  public isLoading: boolean = false;
  public appointmentHour: string = "";

  constructor(override languageService: LanguageService,
    public navService: NavigationService,
    public notifService: NotificationService,
    private dataService: AppointmentControllerService,
    private authService: AuthenticationService,
    public dateService: DateParserService) {
    super(languageService)
  }

  public override ngOnInit(): void {
    console.log(this.appointment);

  }
  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.Confirmation').subscribe((res: string) => {
      this.translations.Confirmation = res;
    });
    this.languageService.get('appointment.HeureProgrammee').subscribe((res: string) => {
      this.translations.HeureProgrammee = res;
    });
    this.languageService.get('appointment.Rappel').subscribe((res: string) => {
      this.translations.Rappel = res;
    });
    this.languageService.get('appointment.Mail').subscribe((res: string) => {
      this.translations.Mail = res;
    });
    this.languageService.get('appointment.Message').subscribe((res: string) => {
      this.translations.Message = res;
    });
    this.languageService.get('appointment.ConfirmezLeRDV').subscribe((res: string) => {
      this.translations.ConfirmezLeRDV = res;
    });
    this.languageService.get('appointement.TitreConfirmationDeRDV').subscribe((res: string) => {
      this.data.title = res;
    });
  }

  public getHour(hour: string | undefined): string {
    this.appointmentHour = hour ? hour.substring(hour.indexOf('T') + 1) : '';
    return this.appointmentHour;
  }

  public saveChanges(): void {
    this.isLoading = true;
    if (this.appointment != undefined) {
      this.appointment.originalAppointmentHour = this.dateService.parseAppointmentHour(this.getHour(this.appointment?.appointmentHour + ""));
      this.dataService.createAppointmentUsingPOST(this.appointment).toPromise().then(res => {
        this.notifService.success(EXCEPTION.APPOINTMENT_SAVE_SUCCES);
        this.navService.goTo('/take-appointment/appointment-booking-success');
      }).catch(
        error => {
          if (error.stringErrorCode == 505 && error.errorText == EXCEPTION.TIME_NOT_FREE) {
            this.notifService.danger(EXCEPTION.TIME_NOT_FREE)
          }
          else if (error.stringErrorCode == 503 && error.errorText == EXCEPTION.SPECIALIST_ALREADY_DEACTIVATED) {
            this.notifService.danger(EXCEPTION.SPECIALIST_ALREADY_DEACTIVATED)
          }
          else if (error.stringErrorCode == 502 && error.errorText == EXCEPTION.SPECIALIST_ALREADY_DELETED) {
            this.notifService.danger(EXCEPTION.SPECIALIST_ALREADY_DELETED)
          }
          else if (error.stringErrorCode == 403 && error.errorText == EXCEPTION.NOT_ACTIVE_SPECIALIST) {
            this.notifService.danger(EXCEPTION.NOT_ACTIVE_SPECIALIST)
          }
          else if (error.stringErrorCode == 505 && error.errorText == EXCEPTION.TIME_NOT_FREE) {
            this.notifService.danger(EXCEPTION.TIME_NOT_FREE)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.PATIENT_NAME_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.PATIENT_NAME_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.PATIENT_EMAIL_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.PATIENT_EMAIL_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.PATIENT_PHONE_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.PATIENT_PHONE_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.ORIGINAL_APPOINTMENT_HOUR_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.APPOINTMENT_DATE_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.APPOINTMENT_DATE_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.APPOINTMENT_HOUR_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.APPOINTMENT_HOUR_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 504 && error.errorText == EXCEPTION.PATIENT_MESSAGE_IS_REQUIRED) {
            this.notifService.danger(EXCEPTION.PATIENT_MESSAGE_IS_REQUIRED)
          }
          else if (error.stringErrorCode == 506 && error.errorText == EXCEPTION.HOUR_PATTERN_NOT_MATCHES) {
            this.notifService.danger(EXCEPTION.HOUR_PATTERN_NOT_MATCHES)
          }
          else {
            this.notifService.danger(EXCEPTION.NO_INTERNET_CONNECTION)
          }
        }
      ).finally(() => {
        this.isLoading = false;
      });
    }
  }
}
