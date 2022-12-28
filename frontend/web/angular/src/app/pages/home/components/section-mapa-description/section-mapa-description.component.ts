import { AfterViewInit, Component } from '@angular/core';
import { NavigationService } from 'src/app/services/navigation.service';
import * as AOS from 'aos';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-section-mapa-description',
  templateUrl: './section-mapa-description.component.html',
  styleUrls: ['./section-mapa-description.component.scss']
})
export class SectionMapaDescriptionComponent extends TranslationsBaseDirective implements AfterViewInit {
  public override translations: any = {
    mapa: {}
  }
  public data: { title: string, description: string } = { title: 'What is MAPA ?', description: 'La MAPA permet un dépistage et un meilleur traitement de l\'hypertension artérielle qui est la maladie cardio-vasculaire la plus fréquente et souvent la plus grave et par complication:' };

  constructor(override languageService: LanguageService,
    public navService: NavigationService) {
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
    this.languageService.get('mapa.Cerebrale').subscribe((res: string) => {
      this.translations.Cerebrale = res;
    });
    this.languageService.get('mapa.Insuffisance').subscribe((res: string) => {
      this.translations.Insuffisance = res;
    });
    this.languageService.get('mapa.Cardiaque').subscribe((res: string) => {
      this.translations.Cardiaque = res;
    });
    this.languageService.get('appointment.PrendreUnRDV').subscribe((res: string) => {
      this.translations.PrendreUnRDV = res;
    });
    this.languageService.get('mapa.MapaTitre').subscribe((res: string) => {
      this.data.title = res;
    });
    this.languageService.get('mapa.MapaDescription').subscribe((res: string) => {
      this.data.description = res;
    });
  }
}