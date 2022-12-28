import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { Specialist, SpecialityMin } from 'src/app/generated';
import { DateParserService } from 'src/app/services/date-parser.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-quick-appointment-form',
  templateUrl: './quick-appointment-form.component.html',
  styleUrls: ['./quick-appointment-form.component.scss']
})
export class QuickAppointmentFormComponent extends TranslationsBaseDirective {
  public override translations: any = {
    appointment: {}
  }

  public form: FormGroup = this.formBuilder.group({});

  @Output() specialityChange: EventEmitter<number> = new EventEmitter<number>();
  @Output() specialistChange: EventEmitter<number> = new EventEmitter<number>();
  @Input() specialities: SpecialityMin[] = [];
  @Input() doctors: Specialist[] = [];

  public startTime: string = '08:00';
  public endTime: string = '17:00';

  constructor(
    private formBuilder: FormBuilder, override languageService: LanguageService,
    public navService: NavigationService,
    private dateService: DateParserService,
    private authService: AuthenticationService,
    private notifService: NotificationService
  ) {
    super(languageService);
    this.initForm();
  }

  public initForm(): void {
    this.form = this.formBuilder.group({
      speciality: new FormControl('select-speciality', Validators.required),
      specialist: new FormControl('select-doctor', Validators.required),
      appointmentDate: new FormControl(this.dateService.formatYYYYMMDDDate(new Date()), Validators.required),
      appointmentHour: new FormControl('', Validators.required),
      patientName: new FormControl('', Validators.required),
      patientPhone: new FormControl('', Validators.required),
      patientEmail: new FormControl('', [Validators.email, Validators.required]),
      patientMessage: new FormControl('', [Validators.required, Validators.required])
    });
  }

  public saveChanges(): void {
    const appointment: any = JSON.parse(JSON.stringify(this.form.value));
    if (!appointment.specialist || appointment.specialist === 'select-doctor') {
      this.notifService.danger(this.translations.ErreurPasDeDocteurSelectionne);
      return;
    }
    let hours: number = Number((appointment.appointmentHour + '').split(':')[0]);
    const minutes: number = Number((appointment.appointmentHour + '').split(':')[1]);
    appointment.originalAppointmentHour = this.dateService.parseAppointmentHour(appointment.appointmentHour + '');
    appointment.appointmentHour = this.form.value.appointmentDate + '';
    appointment.appointmentHour = this.dateService.setMinutesTo00or30(appointment.appointmentHour, hours, minutes);
    appointment.specialistId = this.doctors.find(s => s.specialistId === Number(this.form.value.specialist));
    this.authService.storeAppointment(appointment);
    const date = this.dateService.formatDDMMYYYYDate(this.form.value.appointmentDate);
    const currentDate = this.dateService.formatDDMMYYYYDate(new Date());
    var nowString = new Date().toLocaleTimeString().slice(0, -3);
    var customerHourString = this.form.value.appointmentHour;
    const now = parseInt(nowString.slice(0, 2).replace(':', ''));
    const customerHour = parseInt(customerHourString.slice(0, 2));
    if (this.dateService.compareDates(date, currentDate) == "greater" || this.dateService.compareDates(date, currentDate) == "equal") {
      if (customerHour > now && this.dateService.compareDates(date, currentDate) == "equal") {
        this.navService.goTo('/take-appointment/appointment-booking-verify');
      } else if (this.dateService.compareDates(date, currentDate) == "greater") {
        this.navService.goTo('/take-appointment/appointment-booking-verify');
      } else if (this.dateService.compareDates(date, currentDate) == "equal" && customerHour < now) {
        this.notifService.danger("l'heure saisie doit être supérieure ou égale à l'heure actuelle");
      }
    } else {
      this.notifService.danger("la date saisie doit être supérieure ou égale à la date du jour");
    }
  }

  public onSpecialityChange(event: any): void {
    this.specialityChange.emit(event.target.value);
  }

  public onSpecialistChange(event: any): void {
    this.specialistChange.emit(event.target.value);
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.SelectionDeSpecialite').subscribe((res: string) => {
      this.translations.SelectionDeSpecialite = res;
    });
    this.languageService.get('appointment.SelectionDeMedecin').subscribe((res: string) => {
      this.translations.SelectionDeMedecin = res;
    });
    this.languageService.get('appointment.Heure').subscribe((res: string) => {
      this.translations.Heure = res;
    });
    this.languageService.get('appointment.HeureOuverTureFermeture').subscribe((res: string) => {
      this.translations.HeureOuverTureFermeture = res;
    });
    this.languageService.get('appointment.NomComplet').subscribe((res: string) => {
      this.translations.NomComplet = res;
    });
    this.languageService.get('appointment.Telephone').subscribe((res: string) => {
      this.translations.Telephone = res;
    });
    this.languageService.get('appointment.Email').subscribe((res: string) => {
      this.translations.Email = res;
    });
    this.languageService.get('appointment.BesoinsDuPatient').subscribe((res: string) => {
      this.translations.BesoinsDuPatient = res;
    });
    this.languageService.get('appointment.PrendreUnRDV').subscribe((res: string) => {
      this.translations.PrendreUnRDV = res;
    });
    this.languageService.get('appointment.ErreurPasDeDocteurSelectionne').subscribe((res: string) => {
      this.translations.ErreurPasDeDocteurSelectionne = res;
    });
  }
}
