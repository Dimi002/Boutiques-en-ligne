import { Directive, OnInit } from '@angular/core';
import { LanguageService } from 'src/app/services/language.service';

@Directive()
export class TranslationsBaseDirective implements OnInit {

  public currentLang: string = this.languageService.deviceLanguage;

  public translations: any = {};

  public constructor(
    public languageService: LanguageService) {
  }

  public ngOnInit(): void {
    this.getTranslations();
    this.listenForLoginEvents();
  }

  public listenForLoginEvents(): void {
    window.addEventListener('change:language', () => {
      this.getTranslations();
    });
  }

  public getTranslations(): void { }

  public changeLanguage(lang: string): void {
    this.currentLang = lang;
    this.languageService.changeLanguage(lang);
  }
}