import { AfterViewInit, Component } from '@angular/core';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Specialist, SpecialistControllerService, SpecialistSpecialityControllerService, SpecialityControllerService, SpecialityMin } from 'src/app/generated';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-quick-appointment',
  templateUrl: './section-quick-appointment.component.html',
  styleUrls: ['./section-quick-appointment.component.scss']
})
export class SectionQuickAppointmentComponent extends TranslationsBaseDirective implements AfterViewInit {

  public override translations: any = {
    appointment: {}
  }
  public specialities: SpecialityMin[] = [];

  public doctors: Specialist[] = [];
  public doctorsImages: Specialist[] = [];

  public data: { title: string, description: string } = { title: 'Take a quick Appointment', description: '' };
  public isLoading: boolean = false;

  constructor(
    public specialityService: SpecialityControllerService,
    public specialistService: SpecialistControllerService,
    public specialistSpecialityService: SpecialistSpecialityControllerService,
    override languageService: LanguageService) { 
      super(languageService);
    }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
    this.getSpecialities();
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
        this.doctors = res!;
        this.doctorsImages = res!;
      }
    ).finally(() => {
      this.isLoading = false;
    })
  }

  public filterSpecialist(doctorId: number): void {
    this.doctorsImages = this.doctors.filter(d => (doctorId == d.specialistId));
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('appointment.Titre').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
