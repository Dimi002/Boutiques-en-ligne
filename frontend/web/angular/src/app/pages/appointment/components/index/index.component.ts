import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Appointment, Planing, Specialist, SpecialistControllerService, SpecialistSpecialityControllerService, Speciality, SpecialityControllerService } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { DateParserService } from 'src/app/services/date-parser.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { NotificationService } from 'src/app/services/notification.service';
import { SectionDoctorDetailsCardComponent } from '../section-doctor-details-card/section-doctor-details-card.component';

export interface PlaningSlot { day: number, startTime: string, endTime: string };
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent extends TranslationsBaseDirective implements AfterViewInit {
  @ViewChild('doctorCardDetails', { static: true }) doctorCardDetails!: SectionDoctorDetailsCardComponent;
  public override translations: any = {
    appointment: {}
  }
  public data: { pageTitle: string, icon: string } = { pageTitle: 'Prendre rendez-vous avec un', icon: 'fa-arrow-circle-down' };

  public specialists: Specialist[] = []
  public selectedDoctor?: Specialist;

  public specialities: Speciality[] = []
  public selectedSpeciality?: Speciality;

  public weekDaySlotSelected?: { time: string, selected: boolean, order: number, disabled: boolean, date: Date };
  public partialAppointment?: Partial<Appointment>;
  public isLoading: boolean = false;

  constructor(
    public navService: NavigationService,
    public specialityService: SpecialityControllerService,
    public specialistService: SpecialistControllerService,
    public specialistSpecialityService: SpecialistSpecialityControllerService,
    public dateService: DateParserService,
    public authService: AuthenticationService,
    public notifService: NotificationService,
    override languageService: LanguageService,
    public route: ActivatedRoute) {
    super(languageService);
  }

  public ngAfterViewInit(): void {
    this.getRouteParams();
  }

  public getRouteParams(): void {
    const specialistId: string = this.route.snapshot.paramMap.get('specialistId')!;
    if (specialistId) {
      this.getSpecialist(Number(specialistId));
    }
    this.getSpecialities();
  }

  public getSpecialist(specialistId: number): void {
    this.isLoading = true;
    this.specialistService.findSpecialistByIdUsingGET(specialistId).toPromise().then(
      res => {
        if (res) {
          this.selectedDoctor = res;
          this.setAppointmentToPlaning();
          this.doctorCardDetails.scheduleWidget.disabledSlotRanges = this.toPlaningSlots(this.selectedDoctor?.planings!);
          this.doctorCardDetails.scheduleWidget.setDisabledSlotRanges();
        }
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }

  public getSpecialities(): void {
    this.isLoading = true;
    this.specialityService.findAllSpecialitiesMinUsingGET().toPromise().then(
      res => {
        if (res && res.length > 0) {
          this.specialities = res;
          this.getSpecialitySpecialists(res[0].id!);
        }
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }

  public getSpecialitySpecialists(specialityId: number): void {
    this.isLoading = true;
    this.specialistSpecialityService.getAllSpecialitySpecialistsByIdUsingGET(specialityId).toPromise().then(
      res => {

        if (res) {
          this.specialists = res!;
          if (res.length > 0 && !this.selectedDoctor) {
            this.selectedDoctor = res[0];
            this.setAppointmentToPlaning();
            this.doctorCardDetails.scheduleWidget.disabledSlotRanges = this.toPlaningSlots(this.selectedDoctor.planings!);
            this.doctorCardDetails.scheduleWidget.setDisabledSlotRanges();
          } else if (res.length <= 0 && !this.selectedDoctor) {
            this.doctorCardDetails.scheduleWidget.reset();
          }
        }
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }

  public toPlaningSlots(planings: Planing[]): PlaningSlot[] {
    const result: PlaningSlot[] = [];
    planings.forEach(p => {
      result.push({ day: p.planDay!, startTime: p.startTime!, endTime: p.endTime! });
    });
    return result;
  }

  public onSelectSpeciality(speciality: Speciality): void {
    this.selectedSpeciality = speciality;
    this.getSpecialitySpecialists(speciality.id!);
  }

  public onSelectDoctor(doctor: Specialist): void {
    this.selectedDoctor = doctor;
    this.setAppointmentToPlaning();
    this.doctorCardDetails.scheduleWidget.disabledSlotRanges = this.toPlaningSlots(this.selectedDoctor.planings!);
    this.doctorCardDetails.scheduleWidget.setDisabledSlotRanges();
  }

  setAppointmentToPlaning() {
    this.selectedDoctor?.appointmentsList?.forEach((a) => {
      if (a.appointmentDate) {
        const appointDate = new Date(a.appointmentDate ?? '');
        const date = a.appointmentHour as Date;
        var time = date.toString().substring(11, 16);
        time = this.convertFrTimeToEn(time);
        this.selectedDoctor?.planings?.push({
          startTime: time,
          endTime: time,
          specialist: this.selectedDoctor,
          planDay: appointDate.getDay(),
          id: 2
        })
      }
    });
  }

  convertFrTimeToEn(time: string) {
    var prefix = parseInt(time.substring(0, 2));
    if (prefix <= 12) {
      return time + ' AM';
    }
    var suffix = time.substring(2, time.length);
    return '0' + (prefix - 12) + suffix + ' PM';
  }

  public onSchedule(weekDaySlotSelected: { time: string, selected: boolean, order: number, disabled: boolean, date: Date }): void {
    this.weekDaySlotSelected = weekDaySlotSelected;
  }

  public onSubmitAppointment(partialAppointment: any): void {
    this.partialAppointment = JSON.parse(JSON.stringify(partialAppointment));

    if (!this.selectedDoctor) {
      this.notifService.danger('Veuillez sélectionner un docteur');
      return;
    }
    if (!this.weekDaySlotSelected) {
      this.notifService.danger('Veuillez sélectionner une heure de rendez-vous');
      return;
    }
    if (!this.partialAppointment) {
      this.notifService.danger('Veuillez remplir vos informations personnelles');
      return;
    }
    var doctor = { ...this.selectedDoctor };
    delete doctor.appointmentsList;
    delete doctor.planings;
    partialAppointment.specialistId = doctor;
    partialAppointment.appointmentDate = this.dateService.formatYYYYMMDDDate(this.weekDaySlotSelected.date);
    partialAppointment.appointmentHour = this.weekDaySlotSelected.time.substring(0, this.weekDaySlotSelected.time.indexOf(' '));
    const suffix: string = this.weekDaySlotSelected.time.substring(this.weekDaySlotSelected.time.indexOf(' '), this.weekDaySlotSelected.time.length);
    let hours: number = Number((partialAppointment.appointmentHour + '').split(':')[0]);
    const minutes: number = Number((partialAppointment.appointmentHour + '').split(':')[1]);

    partialAppointment.originalAppointmentHour = partialAppointment.appointmentHour + suffix;
    partialAppointment.appointmentHour = this.dateService.formatYYYYMMDDDate(this.weekDaySlotSelected.date);
    partialAppointment.appointmentHour = this.dateService.setMinutesTo00or30(partialAppointment.appointmentHour, hours, minutes, suffix);
    this.authService.storeAppointment(partialAppointment);
    this.navService.goTo('/take-appointment/appointment-booking-verify');

  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.PrendreUnRdvAvec').subscribe((res: string) => {
      this.data.pageTitle = res;
    });
  }
}
