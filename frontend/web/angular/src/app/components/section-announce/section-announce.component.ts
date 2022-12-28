import { AfterViewInit, Component, OnInit } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-section-announce',
  templateUrl: './section-announce.component.html',
  styleUrls: ['./section-announce.component.scss']
})
export class SectionAnnounceComponent extends TranslationsBaseDirective {
  public override translations: any = {
    sectionAnounce: {}
  }
  public data: { title: string, description: string } = { title: 'Now Available !', description: '' };

  constructor(override languageService: LanguageService,
    public navService: NavigationService) {
    super(languageService);
    this.getTranslations();
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('sectionAnounce.Hotler').subscribe((res: string) => {
      this.translations.Hotler = res;
    });
    this.languageService.get('sectionAnounce.HotlerRyth').subscribe((res: string) => {
      this.translations.HotlerRyth = res;
    });
    this.languageService.get('speciality.SavoirPlus').subscribe((res: string) => {
      this.translations.SavoirPlus = res;
    });
    this.languageService.get('sectionAnounce.Titre').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
