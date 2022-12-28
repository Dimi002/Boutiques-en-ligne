import { Component, Input, AfterViewInit } from '@angular/core';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { Specialist } from 'src/app/generated';
import { DateParserService } from 'src/app/services/date-parser.service';
import { ImageService } from 'src/app/services/image.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-doctor-widget',
  templateUrl: './doctor-widget.component.html',
  styleUrls: ['./doctor-widget.component.scss']
})
export class DoctorWidgetComponent extends TranslationsBaseDirective implements AfterViewInit {
  @Input() data?: Specialist;
  public override translations: any = {
    doctorWidget: {}
  }
  constructor(
    public imageService: ImageService,
    public dateService: DateParserService,
    override languageService: LanguageService,
    public navService: NavigationService) {
    super(languageService);
  }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
  }

  public getSpecialities(specialities: string[] | undefined): string {
    if (specialities) {
      if (specialities.length <= 3)
        return specialities.join(', ');
      return specialities.slice(0, 3).join(', ') + '...'
    }
    return '';
  }

  public getGender(gender: string | undefined): string {
    if (gender)
      return gender === 'M' ? 'Man' : 'Woman';
    return '';
  }

  public getDisponibility(): string {
    return this.dateService.parseToLocalFr(new Date());
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('doctorWidget.Reserver').subscribe((res: string) => {
      this.translations.Reserver = res;
    });
    this.languageService.get('doctorWidget.AnneeExperience').subscribe((res: string) => {
      this.translations.AnneeExperience = res;
    });
    this.languageService.get('doctorWidget.DisponibleLe').subscribe((res: string) => {
      this.translations.DisponibleLe = res;
    });
  }
}
