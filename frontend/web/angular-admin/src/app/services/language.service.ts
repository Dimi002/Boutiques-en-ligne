import { Injectable, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { languages } from '../configs/languages';

/**
 * @author Arléon Zemtsop
 * @email arleonzemtsop@gmail.com
*/
@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  public deviceLanguage: string = '';

  constructor(
    private translateService: TranslateService) { }

  public ngOnInit(): void { }

  public changeLanguage(lang: string): void {
    this.deviceLanguage = lang;
    this.translateService.use(this.deviceLanguage);
    window.dispatchEvent(new CustomEvent('change:language'));
  }

  public get(key: string, options?: any) {
    if (options)
      return this.translateService.get(key, options);
    return this.translateService.get(key);
  }

  public initTranslate(deviceLanguage: string): void {
    // Set the default deviceLanguage for translation strings, and the current deviceLanguage.
    this.translateService.setDefaultLang(languages.FR);
    if (deviceLanguage) {
      if (deviceLanguage.toLowerCase().includes(languages.FR)) {
        this.deviceLanguage = languages.FR;
      } else if (deviceLanguage.toLowerCase().includes(languages.EN)) {
        this.deviceLanguage = languages.EN;
      } else {
        this.deviceLanguage = languages.FR;
      }
      this.translateService.use(this.deviceLanguage);
    } else {
      // Set your deviceLanguage here
      this.deviceLanguage = languages.FR;
    }
  }

  public getDeviceLanguage(): void {
    if (window.Intl && typeof window.Intl === 'object') {
      this.initTranslate(navigator.language);
    } else {
      this.initTranslate(languages.FR);
    }
  }
}