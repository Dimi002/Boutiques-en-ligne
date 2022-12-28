import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Setting, Specialist } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { ScheduleWidgetComponent } from '../schedule-widget/schedule-widget.component';

@Component({
  selector: 'app-section-doctor-details-card',
  templateUrl: './section-doctor-details-card.component.html',
  styleUrls: ['./section-doctor-details-card.component.scss', './complement.scss']
})
export class SectionDoctorDetailsCardComponent extends TranslationsBaseDirective {
  @ViewChild('scheduleWidget', { static: true }) scheduleWidget!: ScheduleWidgetComponent;
  @Input() data?: Specialist;
  @Output() schedule: EventEmitter<any> = new EventEmitter<any>;
  @Output() submitAppointment: EventEmitter<{ speciality: string, doctor: string, apptDate: string, apptStartTime: string, apptEndTime: string, patientFullName: string, patientPhone: string, patientEmail: string, apptPurpose: string }> = new EventEmitter<{ speciality: string, doctor: string, apptDate: string, apptStartTime: string, apptEndTime: string, patientFullName: string, patientPhone: string, patientEmail: string, apptPurpose: string }>;

  public settings?: Setting = this.authService.getSettings(); 
  public clinicOpeningAngClosingHours: { start: number, end: number } = { start: this.settings?.planingStartAt ?? 8, end: this.settings?.planingEndAt ?? 17 };
  public override translations: any = {
    appointment: {}
  }

  constructor(override languageService: LanguageService,
    public imageService: ImageService,
    public authService: AuthenticationService) {
    super(languageService);
  }

  public save(partialAppointment: any): void {
    this.submitAppointment.emit(partialAppointment);
  }

  public onScheduleTiming(selectedWeekDaysSlots: any): void {
    this.schedule.emit(selectedWeekDaysSlots);
  }

  public getSpecialities(specialities: string[] | undefined): string {
    if (specialities) {
      if (specialities.length <= 3)
        return specialities.join(', ');
      return specialities.slice(0, 3).join(', ') + '...'
    }
    return '';
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.ChoisirPlage').subscribe((res: string) => {
      this.translations.ChoisirPlage = res;
    });
    this.languageService.get('appointment.RemplissageInfo').subscribe((res: string) => {
      this.translations.RemplissageInfo = res;
    });
  }
}
