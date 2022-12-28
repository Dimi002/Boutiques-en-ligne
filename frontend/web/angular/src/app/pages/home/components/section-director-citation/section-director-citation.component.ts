import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { SlickCarouselComponent } from 'ngx-slick-carousel';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-director-citation',
  templateUrl: './section-director-citation.component.html',
  styleUrls: ['./section-director-citation.component.scss']
})
export class SectionDirectorCitationComponent extends TranslationsBaseDirective implements AfterViewInit {
  public override translations: any = {
    sectionDirecteur: {}
  }
  @ViewChild('slickComponent', { static: true }) slides!: SlickCarouselComponent;

  public data: { title: string, description: string } = { title: 'Clinic Director Word', description: '' };

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
    this.languageService.get('sectionDirecteur.MotDuDirecteur').subscribe((res: string) => {
      this.translations.MotDuDirecteur = res;
    });
    this.languageService.get('sectionDirecteur.TitreSectionDirecteur').subscribe((res: string) => {
      this.data.title = res;
    });
  }
}
