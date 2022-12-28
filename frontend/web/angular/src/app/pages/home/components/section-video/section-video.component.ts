import { AfterViewInit, Component } from '@angular/core';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-video',
  templateUrl: './section-video.component.html',
  styleUrls: ['./section-video.component.scss', './complement.scss']
})
export class SectionVideoComponent extends TranslationsBaseDirective implements AfterViewInit {
  public override translations: any = {
    video: {}
  }
  public data: { title: string, description: string } = { title: 'What We Do!', description: 'Notre mission première est d’accueillir et de  prodiguer des soins aux patients;  De veiller à leur confort et leur bien-être en vue de maintenir et d’améliorer leur état de santé.' };

  constructor(override languageService: LanguageService) {
    super(languageService);
  }

  ngAfterViewInit(): void {
    AOS.init({ disable: 'mobile' });
    AOS.refresh();
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('video.Staff').subscribe((res: string) => {
      this.translations.Staff = res;
    });
    this.languageService.get('video.Objectif').subscribe((res: string) => {
      this.translations.Objectif = res;
    });
    this.languageService.get('video.Titre').subscribe((res: string) => {
      this.data.title = res;
    });

    this.languageService.get('video.Description').subscribe((res: string) => {
      this.data.description = res;
    });
  }
}
