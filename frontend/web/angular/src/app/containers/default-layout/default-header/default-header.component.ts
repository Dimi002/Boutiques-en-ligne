import { Component, AfterViewInit, OnInit } from '@angular/core';
import { LanguageService } from 'src/app/services/language.service';
import { NavigationService } from 'src/app/services/navigation.service';
import { DefaultHeaderBaseComponent } from './default-header-base.component';

@Component({
  selector: 'app-default-header',
  templateUrl: './default-header.component.html',
  styleUrls: ['./default-header.component.scss']
})
export class DefaultHeaderComponent extends DefaultHeaderBaseComponent implements OnInit, AfterViewInit {
  public override translations: any = {
    menu: {}
  }
  constructor(
    override languageService: LanguageService,
    override navService: NavigationService
  ) {
    super(languageService, navService);
  }

  public ngAfterViewInit(): void {
    this.initJqueryMenuFn();
  }

  public getMenuName(key?: string): string {
    return key? this.translations.menu[key]: '';
  }

  override getTranslations(): void {
    this.languageService.get('home.pageTitle').subscribe((res: string) => {
      this.translations.pageTitle = res;
    });
    this.languageService.get('menu.Accueil').subscribe((res: string) => {
      this.translations.menu.Accueil = res;
    });
    this.languageService.get('menu.Consultations').subscribe((res: string) => {
      this.translations.menu.Consultations = res;
    });
    this.languageService.get('menu.Departements').subscribe((res: string) => {
      this.translations.menu.Departements = res;
    });
    this.languageService.get('menu.Galerie').subscribe((res: string) => {
      this.translations.menu.Galerie = res;
    });
    this.languageService.get('menu.AProposDeNous').subscribe((res: string) => {
      this.translations.menu.AProposDeNous = res;
    });
    this.languageService.get('menu.ContactezNous').subscribe((res: string) => {
      this.translations.menu.ContactezNous = res;
    });
  }
}