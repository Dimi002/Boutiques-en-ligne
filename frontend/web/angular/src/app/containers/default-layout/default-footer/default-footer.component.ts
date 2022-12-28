import { Component } from '@angular/core';
import { TranslationsBaseDirective } from 'src/app/base-components/translations-base.component';
import { SettingDTO } from 'src/app/generated';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-default-footer',
  templateUrl: './default-footer.component.html',
  styleUrls: ['./default-footer.component.scss'],
})
export class DefaultFooterComponent extends TranslationsBaseDirective {
  public override translations: any = {
    footer: {}
  }
  public setting?: SettingDTO;

  public constructor(
    override languageService: LanguageService,
    public navService: NavigationService,
    public authService: AuthenticationService) {
    super(languageService);
    this._initialisation();
  }

  public _initialisation() {
    this.setting = this.authService.getSettings()!;
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('footer.PourLesDocteurs').subscribe((res: string) => {
      this.translations.PourLesDocteurs = res;
    });
    this.languageService.get('footer.RendezVous').subscribe((res: string) => {
      this.translations.RendezVous = res;
    });
    this.languageService.get('footer.Connexion').subscribe((res: string) => {
      this.translations.Connexion = res;
    });
    this.languageService.get('footer.Inscription').subscribe((res: string) => {
      this.translations.Inscription = res;
    });
    this.languageService.get('footer.TermesEtConditions').subscribe((res: string) => {
      this.translations.TermesEtConditions = res;
    });
    this.languageService.get('menu.ContactezNous').subscribe((res: string) => {
      this.translations.ContactezNous = res;
    });
    this.languageService.get('footer.TableauDeBordDuDocteur').subscribe((res: string) => {
      this.translations.TableauDeBordDuDocteur = res;
    });
    this.languageService.get('footer.Politique').subscribe((res: string) => {
      this.translations.Politique = res;
    });
    this.languageService.get('footer.PourLesPatients').subscribe((res: string) => {
      this.translations.PourLesPatients = res;
    });
    this.languageService.get('footer.ChercherUnDocteur').subscribe((res: string) => {
      this.translations.ChercherUnDocteur = res;
    });
    this.languageService.get('footer.PrendreUnRDV').subscribe((res: string) => {
      this.translations.PrendreUnRDV = res;
    });
    this.languageService.get('footer.Description').subscribe((res: string) => {
      this.translations.Description = res;
    });
  }
}